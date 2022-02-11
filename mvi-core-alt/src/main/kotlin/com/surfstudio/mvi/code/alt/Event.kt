package com.surfstudio.mvi.code.alt

import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.isActive

/** Событие. */
interface Event

// То, что мы наследуем хаб от CoreComponent -- спорно,
// т.к. добавляет грязное api, которое не должно быть доступно пользователю
/** Неизменяемая шина событий, существует как root точка для организации остального наследования. */
interface EventHub<E : Event> : CoreComponent

/** Изменяемая шина событий, на котороую можно отправить событие, используется внутри бизнес-логики и из UI слоя. */
interface MutableEventHub<E : Event> : EventHub<E> {
    fun send(event: E)
}

/** Неизменяемая шина событий, события которой можно слушать и реагировать на них, используется внутри бизнес-логики. */
interface ListenableEventHub<E : Event> : EventHub<E> {
    fun addListener(listener: Listener<E>)
    fun removeListener(listener: Listener<E>)

    fun interface Listener<E : Event> {
        fun onEvent(event: E)
    }
}

/** Удобное объединение интерфейсов, часто хочется видеть и использовать их вместе. */
interface MutableListenableEventHub<E : Event> : MutableEventHub<E>, ListenableEventHub<E>

/** Позволяет реализовать паттер "Наблюдатель" для любой библиотеки потоков данных. */
interface StreamedEventHub<E : Event, STREAM> : EventHub<E> {
    fun getStream(): STREAM
}


/**
 * Декораторы ниже скрывают реализацию декорированием,
 * чтобы разработчик не смог скастить объект к другому типу и обхитрить спроектированную систему.
 *
 * Реальная рреализация только одна.
 */
class MutableEventHubDecorator<E : Event>(
    private val impl: MutableEventHub<E>
) : MutableEventHub<E> by impl

class ListenableEventHubDecorator<E : Event>(
    private val impl: ListenableEventHub<E>
) : ListenableEventHub<E> by impl

class EventHubImpl<E : Event> : MutableListenableEventHub<E> {

    private val listeners: MutableSet<ListenableEventHub.Listener<E>> = mutableSetOf()

    override fun start() = Unit

    override fun shutdown() {
        listeners.clear()
    }

    override fun send(event: E) {
        listeners.forEach { listener -> listener.onEvent(event) }
    }

    override fun addListener(listener: ListenableEventHub.Listener<E>) {
        listeners += listener
    }

    override fun removeListener(listener: ListenableEventHub.Listener<E>) {
        listeners -= listener
    }

    fun asMutable(): MutableEventHub<E> {
        return MutableEventHubDecorator(this)
    }

    fun asListenable(): ListenableEventHub<E> {
        return ListenableEventHubDecorator(this)
    }
}


// region RX extension
interface StreamedObservableEventHub<E : Event> : StreamedEventHub<E, Observable<E>>

class StreamedObservableEventHubDecorator<E : Event>(
    private val impl: ListenableEventHub<E>
) : StreamedObservableEventHub<E> {

    override fun start() = Unit
    override fun shutdown() = Unit

    override fun getStream(): Observable<E> {
        return Observable.create { emitter ->
            val listener = ListenableEventHub.Listener<E> { event ->
                if (!emitter.isDisposed) {
                    emitter.onNext(event)
                }
            }
            impl.addListener(listener)
            emitter.setCancellable { impl.removeListener(listener) }
        }
    }
}

fun <E : Event> ListenableEventHub<E>.asStreamedObservable(): StreamedObservableEventHub<E> {
    return StreamedObservableEventHubDecorator(this)
}

fun <E : Event> ListenableEventHub<E>.getStateObservableStream(): Observable<E> {
    return asStreamedObservable().getStream()
}
// endregion


// region Coroutines extension
interface StreamedFlowEventHub<E : Event> : StreamedEventHub<E, Flow<E>>

class StreamedFlowEventHubDecorator<E : Event>(
    private val impl: ListenableEventHub<E>
) : StreamedFlowEventHub<E> {

    override fun start() = Unit
    override fun shutdown() = Unit

    override fun getStream(): Flow<E> {
        return callbackFlow {
            val listener = ListenableEventHub.Listener<E> { event ->
                if (isActive) {
                    trySendBlocking(event)
                }
            }
            impl.addListener(listener)
            invokeOnClose { impl.removeListener(listener) }
        }
    }
}

fun <E : Event> ListenableEventHub<E>.asStreamedFlow(): StreamedFlowEventHub<E> {
    return StreamedFlowEventHubDecorator(this)
}

fun <E : Event> ListenableEventHub<E>.getStateFlowStream(): Flow<E> {
    return asStreamedFlow().getStream()
}
//endregion
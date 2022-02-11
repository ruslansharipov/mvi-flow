package com.surfstudio.mvi.code.alt

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/** Состояние. */
interface State

// То, что мы наследуем хаб от CoreComponent -- спорно,
// т.к. добавляет грязное api, которое не должно быть доступно пользователю
/** Неизменяемый хранитель состояния. */
interface StateHub<S : State> : CoreComponent {
    fun getState(): S
}

/** Изменяемый хранитель состояния, который используется для изменения состояния из бизнес-логики. */
interface MutableStateHub<S : State> : StateHub<S> {
    fun setState(state: S)
}

/** Неизменяемый хранитель состояния, которого можно слушать, чтобы реагировать на смену состояний, например из UI. */
interface ListenableStateHub<S : State> : StateHub<S> {
    fun addListener(listener: Listener<S>)
    fun removeListener(listener: Listener<S>)

    fun interface Listener<S : State> {
        fun onStateChanged(state: S)
    }
}

/** Удобное объединение интерфейсов, часто хочется видеть и использовать их вместе. */
interface MutableListenableStateHub<S : State> : MutableStateHub<S>, ListenableStateHub<S>

/** Позволяет реализовать паттер "Наблюдатель" для любой библиотеки потоков данных. */
interface StreamedStateHub<S : State, STREAM> : StateHub<S> {
    fun getStateStream(): STREAM
}


/**
 * Декораторы ниже скрывают реализацию декорированием,
 * чтобы разработчик не смог скастить объект к другому типу и обхитрить спроектированную систему.
 *
 * Реальная реализация только одна.
 */
class StateHubDecorator<S : State>(
    private val impl: StateHub<S>
) : StateHub<S> by impl

class MutableStateHubDecorator<S : State>(
    private val impl: MutableStateHub<S>
) : MutableStateHub<S> by impl

class ListenableStateHubDecorator<S : State>(
    private val impl: ListenableStateHub<S>
) : ListenableStateHub<S> by impl

class StateHubImpl<S : State>(
    private var state: S
) : MutableListenableStateHub<S> {

    private val listeners: MutableSet<ListenableStateHub.Listener<S>> = mutableSetOf()

    override fun start() = Unit

    override fun shutdown() {
        listeners.clear()
    }

    override fun getState(): S {
        return state
    }

    override fun setState(state: S) {
        this.state = state
        listeners.forEach { listener -> listener.onStateChanged(state) }
    }

    override fun addListener(listener: ListenableStateHub.Listener<S>) {
        listeners += listener
    }

    override fun removeListener(listener: ListenableStateHub.Listener<S>) {
        listeners -= listener
    }

    fun asImmutable(): StateHub<S> {
        return StateHubDecorator(this)
    }

    fun asMutable(): MutableStateHub<S> {
        return MutableStateHubDecorator(this)
    }

    fun asListenable(): ListenableStateHub<S> {
        return ListenableStateHubDecorator(this)
    }
}

// region RX extension
/**
 * Неизменяемый владелец состояния.
 *
 * Реализует паттерн "Наблюдатель" с использованием RxJava3 Observable.
 */
interface StreamedObservableStateHub<S : State> : StreamedStateHub<S, Observable<S>>

class StreamedObservableStateHubDecorator<S : State>(
    private val impl: ListenableStateHub<S>
) : StreamedObservableStateHub<S> {

    private val stream: BehaviorSubject<S> = BehaviorSubject.createDefault(impl.getState())

    override fun start() = Unit
    override fun shutdown() = Unit

    override fun getState(): S {
        return impl.getState()
    }

    override fun getStateStream(): Observable<S> {
        val listener = ListenableStateHub.Listener<S> { state -> stream.onNext(state) }
        return stream
            .hide()
            .doOnSubscribe { impl.addListener(listener) }
            .doOnComplete { impl.removeListener(listener) }
    }
}

fun <S : State> ListenableStateHub<S>.asStreamedObservable(): StreamedObservableStateHub<S> {
    return StreamedObservableStateHubDecorator(this)
}

fun <S : State> ListenableStateHub<S>.getStateObservableStream(): Observable<S> {
    return asStreamedObservable().getStateStream()
}
// endregion

// region Coroutines extension
/**
 * Неизменяемый владелец состояния.
 *
 * Реализует паттерн "Наблюдатель" с использованием Coroutines Flow.
 */
interface StreamedFlowStateHub<S : State> : StreamedStateHub<S, Flow<S>>

class StreamedFlowStateHubImpl<S : State>(
    private val impl: ListenableStateHub<S>
) : StreamedFlowStateHub<S> {

    private val stream: MutableStateFlow<S> = MutableStateFlow(impl.getState())

    override fun start() = Unit
    override fun shutdown() = Unit

    override fun getState(): S {
        return impl.getState()
    }

    override fun getStateStream(): Flow<S> {
        return stream.asStateFlow()
    }
}


fun <S : State> ListenableStateHub<S>.getStateFlowStream(): Flow<S> {
    return StreamedFlowStateHubImpl(this).getStateStream()
}
// endregion


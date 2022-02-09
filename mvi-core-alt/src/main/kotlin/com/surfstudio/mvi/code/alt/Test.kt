package com.surfstudio.mvi.code.alt

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

data class StateTest(
    val title: String,
    val content: String,
    val isSaved: Boolean
) : State

sealed class EventTest : Event {
    data class TitleChanged(val newTitle: String) : EventTest()
    data class ContentChanged(val newContent: String) : EventTest()
    object Save : EventTest()
}

class BusinessLogicComponent(
    private val stateHub: MutableStateHub<StateTest>,
    private val eventHub: MutableListenableEventHub<EventTest>
) : CoreComponent {

    private var loadDataDisposable: Disposable = Disposable.empty()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun start() {
        loadDataDisposable = loadData()
        eventHub.asStreamedObservable().getStream()
            .filter { it is EventTest.TitleChanged }
            .map { it as EventTest.TitleChanged }
            .subscribe(::updateTitle)
            .also(compositeDisposable::add)
    }

    override fun shutdown() {
        loadDataDisposable.dispose()
    }

    private fun loadData(): Disposable = TODO()

    private fun updateTitle(event: EventTest.TitleChanged) {
        stateHub.getState().copy(title = event.newTitle).also(stateHub::setState)
    }
}

class AndroidViewTest(
    private val stateHub: ListenableStateHub<StateTest>,
    private val eventHub: MutableEventHub<EventTest>
) : CoreComponent {

    override fun start() {
        stateHub.getStateFlowStream().onEach(::renderState)//.launchIn(viewLifecycleScope) or
        stateHub.getStateObservableStream().subscribe(::renderState)//.also(compositeDisposable::add)

        titleChanged { eventHub.send(EventTest.TitleChanged("changed")) }
        contentChanged { eventHub.send(EventTest.ContentChanged("changed")) }
        saveClicked { eventHub.send(EventTest.Save) }
    }

    override fun shutdown() {
        // dispose
    }

    private fun renderState(state: StateTest): Nothing = TODO()
    private fun titleChanged(callback: () -> Unit) = callback()
    private fun contentChanged(callback: () -> Unit) = callback()
    private fun saveClicked(callback: () -> Unit) = callback()
}

fun main() {
    val state = StateTest(title = "empty", content = "empty", isSaved = false)
    val stateHub = StateHubImpl(state)
    val eventHub = EventHubImpl<EventTest>()
    val businessLogic = BusinessLogicComponent(stateHub, eventHub)

    val requiredTitle = "hei bruh"
    val core = CoreImpl()
    core.register(stateHub)
    core.register(eventHub)
    core.register(businessLogic)
    core.start()

    eventHub.send(EventTest.TitleChanged(requiredTitle))
    runBlocking { delay(500L) }
    assert(stateHub.getState().title == requiredTitle) { "mvi not working!" }

    core.shutdown()
}


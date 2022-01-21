package ru.surfstudio.mvi.flow.app.request

sealed class RequestState {
    object None: RequestState()
    object Loading: RequestState()
    object Success: RequestState()
    object Error: RequestState()
}
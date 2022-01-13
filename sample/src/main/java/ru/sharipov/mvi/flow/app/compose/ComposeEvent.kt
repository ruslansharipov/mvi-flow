package ru.sharipov.mvi.flow.app.compose

import ru.sharipov.mvi.core.event.Event

sealed class ComposeEvent: Event {
    object NextBtnClick : ComposeEvent()
    object BackBtnClick : ComposeEvent()
    object CloseBtnClick : ComposeEvent()
}
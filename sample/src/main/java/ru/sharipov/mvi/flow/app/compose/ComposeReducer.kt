package ru.sharipov.mvi.flow.app.compose

import ru.sharipov.mvi.core.reducer.Reducer

data class ComposeState(
    val stage: ComposeScreenStage = ComposeScreenStage.INITIAL
)

class ComposeReducer: Reducer<ComposeEvent, ComposeState> {

    override fun reduce(
        state: ComposeState,
        event: ComposeEvent
    ): ComposeState {
        return when(event) {
            ComposeEvent.NextBtnClick -> state.copy(
                stage = ComposeScreenStage.getByCode((state.stage.code + 1) % 3)
            )
            ComposeEvent.BackBtnClick -> TODO()
            ComposeEvent.CloseBtnClick -> TODO()
        }
    }
}
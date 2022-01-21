package ru.surfstudio.mvi.flow.app.handler

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.surfstudio.mvi.flow.app.R
import ru.surfstudio.mvi.flow.app.simple.SimpleState
import ru.surfstudio.mvi.mappers.handler.MviErrorHandlerAndroidView

class HandlerActivity : AppCompatActivity(),
    MviErrorHandlerAndroidView<HandlerState, HandlerEvent> {

    override val viewModel by viewModels<HandlerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handler)

        observeState { state: HandlerState ->
            //todo
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        emit(HandlerEvent.OnBackPressed)
    }
}
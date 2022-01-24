package ru.surfstudio.mvi.flow.app.handler

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.surfstudio.mvi.flow.app.R
import ru.surfstudio.mvi.flow.app.handler.mapper.LoadStateType
import ru.surfstudio.mvi.mappers.handler.MviErrorHandlerAndroidView

class HandlerActivity : AppCompatActivity(),
    MviErrorHandlerAndroidView<HandlerState, HandlerEvent> {

    override val viewModel by viewModels<HandlerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handler)
        val contentTv = findViewById<TextView>(R.id.handler_content_tv)

        observeState { state: HandlerState ->
            contentTv.text = when (state.loadState) {
                LoadStateType.Empty -> "Empty state"
                LoadStateType.Error -> "Error"
                LoadStateType.Main -> "Main loading"
                LoadStateType.NoInternet -> "No internet"
                LoadStateType.None -> "Get content"
                LoadStateType.SwipeRefreshLoading -> "Swipe refresh"
                LoadStateType.TransparentLoading -> "Transparent loading"
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        emit(HandlerEvent.OnBackPressed)
    }
}
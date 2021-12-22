package ru.sharipov.mvi.flow.app.simple

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import ru.sharipov.mvi.flow.app.R
import ru.sharipov.mvi.flow.app.request.RequestState
import ru.sharipov.mvi.flow.lifecycle.MviAndroidView

class SimpleActivity : AppCompatActivity(), MviAndroidView<SimpleState, SimpleEvent> {

    override val viewModel by viewModels<SimpleViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val counterTv = findViewById<TextView>(R.id.counter_tv)
        val incrementBtn = findViewById<Button>(R.id.increment_btn)
        val decrementBtn = findViewById<Button>(R.id.decrement_btn)

        val clickMeBtn = findViewById<Button>(R.id.click_me_btn)

        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        val loadingBtn = findViewById<Button>(R.id.loading_btn)

        loadingBtn.setOnClickListener { emit(SimpleEvent.StartLoadingClick) }

        incrementBtn.setOnClickListener { emit(SimpleEvent.IncrementClick) }
        decrementBtn.setOnClickListener { emit(SimpleEvent.DecrementClick) }

        clickMeBtn.setOnClickListener { emit(SimpleEvent.SimpleClick) }

        observeState { state: SimpleState ->
            counterTv?.text = state.counter.toString()
            clickMeBtn?.text = state.title

            progressBar.isVisible = state.request == RequestState.Loading
            loadingBtn.isClickable = state.request == RequestState.None
            loadingBtn.text = when(state.request) {
                RequestState.Error -> "Ошибка =("
                RequestState.Loading -> "Загрузка..."
                RequestState.None -> "Старт загрузки"
                RequestState.Success -> "Успех!"
            }
        }
    }
}
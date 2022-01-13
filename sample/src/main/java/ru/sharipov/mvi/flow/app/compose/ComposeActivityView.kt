package ru.sharipov.mvi.flow.app.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ru.sharipov.mvi.flow.app.compose.ui.theme.MviflowTheme
import ru.sharipov.mvi.flow.lifecycle.MviAndroidView

class ComposeActivityView : ComponentActivity(), MviAndroidView<ComposeState, ComposeEvent> {

    override val viewModel: ComposeViewModel by viewModels<ComposeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeState { screenState: ComposeState ->
            setContent {
                MviflowTheme {
                    ComposeScreen(screenState) {
                        ComposeEvent.NextBtnClick.emit()
                    }
                }
            }
        }
    }
}

@Composable
fun ComposeScreen(screenState: ComposeState, onClickEvent: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.weight(1F)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = screenState.stage.name,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize()
            )
        }
        Button(
            onClick = onClickEvent,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Следующий этап")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MviflowTheme {
        ComposeScreen(ComposeState(), {})
    }
}
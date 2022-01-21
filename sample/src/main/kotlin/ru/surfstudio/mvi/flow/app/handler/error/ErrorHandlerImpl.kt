package ru.surfstudio.mvi.flow.app.handler.error

import android.util.Log
import ru.surfstudio.mvi.mappers.handler.ErrorHandler

/** Sample error handler. The showing of snackbar could be added here for each error in real app */
class ErrorHandlerImpl : ErrorHandler {

    override fun handleError(e: Throwable) {
        Log.e("Error", "Sample", e)
    }
}
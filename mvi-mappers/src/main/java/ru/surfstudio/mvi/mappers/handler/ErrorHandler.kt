package ru.surfstudio.mvi.mappers.handler

/** Base error handler which is using in mappers for Reducer */
interface ErrorHandler {
    fun handleError(e: Throwable)
}
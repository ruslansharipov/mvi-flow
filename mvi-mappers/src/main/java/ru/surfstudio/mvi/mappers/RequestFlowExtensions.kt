package ru.surfstudio.mvi.mappers

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

/**
 * Расширение для Flow, переводящее асинхронный запрос загрузки данных к Flow<[Request]>.
 *
 * При добавлении к цепочке flow, необходимо применять именно к тому элементу, который будет эмитить значения.
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun <T> Flow<T>.asRequest(): Flow<Request<T>> = this
    .map { Request.Success(it) as Request<T> }
    .onStart { emit(Request.Loading()) }
    .catch { emit(Request.Error(it)) }
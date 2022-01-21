package ru.surfstudio.mvi.core.event

typealias EventFactory<T, E> = (T) -> E
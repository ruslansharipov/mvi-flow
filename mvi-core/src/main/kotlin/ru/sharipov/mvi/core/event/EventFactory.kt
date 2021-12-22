package ru.sharipov.mvi.core.event

typealias EventFactory<T, E> = (T) -> E
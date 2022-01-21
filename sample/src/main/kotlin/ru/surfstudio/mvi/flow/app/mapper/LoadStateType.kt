package ru.surfstudio.mvi.flow.app.mapper

import ru.surfstudio.mvi.mappers.Loading

sealed class LoadStateType : Loading {

    /**
     * Отсутствие загрузки в плейсхолдере, плейсхолдер скрыт
     */
    object None : LoadStateType() {
        override val isLoading: Boolean = false
    }

    /**
     * Стейт активного PTR
     */
    object SwipeRefreshLoading : LoadStateType() {
        override val isLoading: Boolean = true
    }

    /**
     * Контейнер с ошибкой и возможностью перезагрузить данные.
     */
    object Error : LoadStateType() {
        override val isLoading: Boolean = false
    }

    /**
     * Контейнер с ошибкой об отсутствии интернета и возможностью перезагрузить данные.
     */
    object NoInternet : LoadStateType() {
        override val isLoading: Boolean = false
    }

    /**
     * Контейнер с сообщением о том, что мы получили пустой список данных
     */
    object Empty : LoadStateType() {
        override val isLoading: Boolean = false
    }

    /**
     * Полноэкранный лоадер, отображается поверх контента
     */
    object Main : LoadStateType() {
        override val isLoading: Boolean = true
    }

    /**
     * Прозрачный лоадер, за которым видно контент.
     */
    object TransparentLoading : LoadStateType() {
        override val isLoading: Boolean = true
    }
}
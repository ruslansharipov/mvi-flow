package com.surfstudio.mvi.code.alt

/** Компонент системы, который управляет жизненным циклом. */
interface CoreComponent {
    fun start()
    fun shutdown()
}

/** Гибкое связующее ядро системы. */
interface Core {
    fun register(component: CoreComponent)
    fun start()
    fun shutdown()
}

/** Стандартная реализация ядра системы. */
class CoreImpl : Core {

    private val components: MutableSet<CoreComponent> = mutableSetOf()
    private var isStarted: Boolean = false

    override fun register(component: CoreComponent) {
        components += component
        if (isStarted) {
            component.start()
        }
    }

    override fun start() {
        isStarted = true
        components.forEach { component -> component.start() }
    }

    override fun shutdown() {
        components.forEach { component -> component.shutdown() }
    }
}
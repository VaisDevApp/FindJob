package ru.vais.core.di


object BaseComponentHolder {
    private var baseComponent: BaseComponent? = null

    fun init(component: BaseComponent) {
        baseComponent = component
    }

    fun get(): BaseComponent {
        return baseComponent ?: throw IllegalStateException("You must call init()")
    }
}
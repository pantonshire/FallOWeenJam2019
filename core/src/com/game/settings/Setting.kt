package com.game.settings

abstract class Setting<T>(val name: String, val defaultValue: T, val onSet: (newValue: T) -> Unit) {

    var currentValue: T = defaultValue
        private set

    abstract fun isValid(value: T): Boolean

    protected abstract fun parseString(string: String): T

    fun set(value: T) {
        if (isValid(value)) {
            currentValue = value
            onSet(value)
        } else {
            println("Value $value out of range for setting $name")
        }
    }

    fun fromString(string: String) {
        set(parseString(string))
    }

    fun reset() {
        currentValue = defaultValue
    }

}
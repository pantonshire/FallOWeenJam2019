package com.game.settings

import java.lang.NumberFormatException

class FloatSetting(
        name: String,
        defaultValue: Float,
        private val range: ClosedFloatingPointRange<Float>,
        onSet: (newValue: Float) -> Unit = {}
): Setting<Float>(name, defaultValue, onSet) {

    override fun isValid(value: Float) =
            value in range

    override fun parseString(string: String) = try {
        string.toFloat()
    } catch (exception: NumberFormatException) {
        println("Error loading floating-point setting $name from string: \"${string}\"")
        defaultValue
    }

}
package com.game.settings

import java.lang.NumberFormatException

class IntegerSetting(name: String, defaultValue: Int, private val range: IntRange, onSet: (newValue: Int) -> Unit = {}):
        Setting<Int>(name, defaultValue, onSet) {

    override fun isValid(value: Int) =
            value in range

    override fun parseString(string: String) = try {
        string.toInt()
    } catch (exception: NumberFormatException) {
        println("Error loading integer setting $name from string: \"${string}\"")
        defaultValue
    }

}
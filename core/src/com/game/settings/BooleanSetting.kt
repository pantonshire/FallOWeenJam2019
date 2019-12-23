package com.game.settings

import java.lang.NumberFormatException

class BooleanSetting(name: String, defaultValue: Boolean, onSet: (newValue: Boolean) -> Unit = {}):
        Setting<Boolean>(name, defaultValue, onSet) {

    override fun isValid(value: Boolean) =
            true

    override fun parseString(string: String) = try {
        string.toBoolean()
    } catch (exception: NumberFormatException) {
        println("Error loading boolean setting $name from string: \"${string}\"")
        defaultValue
    }

}
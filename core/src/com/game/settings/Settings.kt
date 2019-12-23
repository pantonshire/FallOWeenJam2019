package com.game.settings

import com.game.graphics.CursorManager

object Settings {

    private val booleanSettings: Map<String, BooleanSetting> = arrayOf(
            BooleanSetting("ui-movement", true),
            BooleanSetting("particles", true),
            BooleanSetting("cursor-hidden", true) {
                value -> if (value) { CursorManager.hideCursor() } else { CursorManager.revealCursor() }
            }
    ).map { it.name to it }.toMap()

    private val integerSettings: Map<String, IntegerSetting> = arrayOf<IntegerSetting>(

    ).map { it.name to it }.toMap()

    private val floatSettings: Map<String, FloatSetting> = arrayOf(
            FloatSetting("audio-vol-all", 1f, 0f..2f),
            FloatSetting("audio-vol-ui", 1f, 0f..2f),
            FloatSetting("audio-vol-game", 1f, 0f..2f),
            FloatSetting("audio-pan", 0f, -1f..1f)
    ).map { it.name to it }.toMap()


    fun initialise() {
        booleanSettings.values.forEach { it.onSet(it.defaultValue) }
        integerSettings.values.forEach { it.onSet(it.defaultValue) }
        floatSettings.values.forEach { it.onSet(it.defaultValue) }
    }


    fun setBool(name: String, value: Boolean) {
        booleanSettings[name]?.set(value)
    }

    fun getBool(name: String): Boolean =
            booleanSettings[name]?.currentValue ?: error("Unknown boolean setting $name")

    fun setInt(name: String, value: Int) {
        integerSettings[name]?.set(value)
    }

    fun getInt(name: String): Int =
            integerSettings[name]?.currentValue ?: error("Unknown integer setting $name")

    fun setFloat(name: String, value: Float) {
        floatSettings[name]?.set(value)
    }

    fun getFloat(name: String): Float =
            floatSettings[name]?.currentValue ?: error("Unknown float setting $name")

}
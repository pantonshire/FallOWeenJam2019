/*
 * Copyright (C) 2019 Thomas Panton
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

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
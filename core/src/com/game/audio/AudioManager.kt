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

package com.game.audio

import com.badlogic.gdx.audio.Sound
import com.game.maths.Maths
import com.game.settings.Settings

object AudioManager {

    private var enabled = false

    fun setAudioEnabled(enabled: Boolean) {
        this.enabled = enabled
    }

    fun playSound(sound: Sound, category: SoundCategory, volume: Float = 1f, pitch: Float = 1f) {
        if (enabled) {
            val masterVolume = Settings.getFloat("audio-vol-all")
            val categoryVolume = Settings.getFloat(category.settingName)
            val pan = Settings.getFloat("audio-pan")
            sound.play(volume * masterVolume * categoryVolume, pitch, pan)
        }
    }

}
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
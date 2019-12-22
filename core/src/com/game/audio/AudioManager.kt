package com.game.audio

import com.badlogic.gdx.audio.Sound
import com.game.maths.Maths

object AudioManager {

    private const val MIN_VOLUME = 0f
    private const val MAX_VOLUME = 2f

    private val volumes = HashMap<SoundCategory, Float>()
    private var masterVolume = 1f
    private var pan = 0f
    private var enabled = false

    fun setAudioEnabled(enabled: Boolean) {
        this.enabled = enabled
    }

    fun playSound(sound: Sound, category: SoundCategory, volume: Float = 1f, pitch: Float = 1f) {
        if (enabled) {
            sound.play(volume * masterVolume * (volumes[category] ?: 1f), pitch, pan)
        }
    }

    fun adjustVolumeFor(category: SoundCategory, volume: Float) {
        volumes[category] = Maths.clamp(volume, 0f, 2f)
    }

    fun adjustMasterVolume(volume: Float) {
        masterVolume = Maths.clamp(volume, 0f, 2f)
    }

    fun volumeFromPercentage(percentage: Float) =
            percentage * MAX_VOLUME

    private fun clampVolume(volume: Float) =
            Maths.clamp(volume, MIN_VOLUME, MAX_VOLUME)

}
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

package com.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Cursor
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.game.audio.AudioManager
import com.game.gamestate.GameStateManager
import com.game.gamestate.LaunchState
import com.game.resources.AssetManagerWrapper

class Main: ApplicationAdapter() {

    private var active = true

    companion object {
        val gsm = GameStateManager()

        fun exitGame() {
            AudioManager.setAudioEnabled(false)
            Gdx.app.exit()
        }
    }

    override fun create() {
        AssetManagerWrapper.INSTANCE.initialise()

        AssetManagerWrapper.INSTANCE.loadFont("editundo.ttf", 16)
        AssetManagerWrapper.INSTANCE.loadTexture("debug.png")
        AssetManagerWrapper.INSTANCE.loadTexture("blackBox.png")
        AssetManagerWrapper.INSTANCE.loadTexture("whiteBox.png")
        AssetManagerWrapper.INSTANCE.loadTexture("particle.png")
        AssetManagerWrapper.INSTANCE.loadSound("select.wav")
        AssetManagerWrapper.INSTANCE.loadSound("impact.wav")
        AssetManagerWrapper.INSTANCE.loadSound("explosion.wav")
        AssetManagerWrapper.INSTANCE.loadSound("jump.wav")
        AssetManagerWrapper.INSTANCE.loadSound("death.wav")
        AssetManagerWrapper.INSTANCE.loadSound("tick.wav")
        AssetManagerWrapper.INSTANCE.loadSound("pass.wav")

        gsm.queueCollapseTo(LaunchState())
    }

    override fun render() {
        if (active) {
            Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
            gsm.update(Gdx.graphics.deltaTime)
            AssetManagerWrapper.INSTANCE.waitLoadAssets() //replaced update()
            gsm.render()
        }
    }

    override fun dispose() {
        AssetManagerWrapper.INSTANCE.unload("editundo.ttf")
        AssetManagerWrapper.INSTANCE.unload("debug.png")
        AssetManagerWrapper.INSTANCE.unload("blackBox.png")
        AssetManagerWrapper.INSTANCE.unload("whiteBox.png")
        AssetManagerWrapper.INSTANCE.unload("particle.png")
        AssetManagerWrapper.INSTANCE.unload("select.wav")
        AssetManagerWrapper.INSTANCE.unload("impact.wav")
        AssetManagerWrapper.INSTANCE.unload("explosion.wav")
        AssetManagerWrapper.INSTANCE.unload("jump.wav")
        AssetManagerWrapper.INSTANCE.unload("death.wav")
        AssetManagerWrapper.INSTANCE.unload("tick.wav")
        AssetManagerWrapper.INSTANCE.unload("pass.wav")
        gsm.onExit()
    }

    override fun resize(width: Int, height: Int) {
        gsm.resize(width, height)
    }

    override fun pause() {
        active = false
    }

    override fun resume() {
        active = true
    }

}
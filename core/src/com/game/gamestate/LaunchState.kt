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

package com.game.gamestate

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.game.Main
import com.game.audio.AudioManager
import com.game.graphics.Canvas
import com.game.resources.AssetManagerWrapper
import com.game.settings.Settings


class LaunchState: GameState() {

    override fun onEnter() {
        AssetManagerWrapper.INSTANCE.waitLoadAssets()
        AudioManager.setAudioEnabled(true)
        Gdx.graphics.setWindowedMode(1280, 720)
        Settings.initialise()
    }

    override fun onExit() {

    }

    override fun update(delta: Float) {
        Main.gsm.queueCollapseTo(MainMenu())
    }

    override fun draw(canvas: Canvas) {

    }

}
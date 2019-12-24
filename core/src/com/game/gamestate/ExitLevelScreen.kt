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
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.game.Main
import com.game.gameplay.Level
import com.game.gameplay.LevelFactory
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper
import com.game.ui.Label
import com.game.ui.TextOption

class ExitLevelScreen: OptionsMenuScreen() {

    init {
        val font = AssetManagerWrapper.INSTANCE.getFont("editundo.ttf")

        addElement(Label(this, Vec(0f, 80f), font, "ARE YOU SURE?", 3f, Color.WHITE, true, true))
        addElement(Label(this, Vec(0f, 40f), font, "YOUR PROGRESS WILL BE LOST.", 2f, Color.WHITE, true, true))

        addOption(TextOption(this, Vec(0f, -40f), font, "NO", 2f, true, true) {
            Main.gsm.queuePop()
        })

        addOption(TextOption(this, Vec(0f, -80f), font, "YES", 2f, true, true) {
            Main.gsm.queueCollapseTo(LevelSelect())
        })
    }

    override fun update(delta: Float) {
        super.update(delta)

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            Main.gsm.queuePop()
        }
    }

    override fun onEnter() {

    }

    override fun onExit() {

    }

}
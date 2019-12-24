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

import com.badlogic.gdx.graphics.Color
import com.game.Main
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper
import com.game.ui.Flabel
import com.game.ui.Label
import com.game.ui.TextOption

class MainMenu: OptionsMenuScreen() {

    init {
        val font = AssetManagerWrapper.INSTANCE.getFont("editundo.ttf")

        addElement(Flabel(this, Vec(0f, 80f), font, "TINY ESCAPES", 4f, Color.WHITE, true, true, 120, 4f))
        addElement(Label(this, Vec(0f, 20f), font, "BY TOM PANTON", 2f, Color.WHITE, true, true))

        addOption(TextOption(this, Vec(0f, -60f), font, "PLAY", 2f, true, true) {
            Main.gsm.queueCollapseTo(LevelSelect())
        })

        addOption(TextOption(this, Vec(0f, -100f), font, "QUIT", 2f, true, true) {
           Main.exitGame()
        })
    }

    override fun onEnter() {

    }

    override fun onExit() {

    }

}
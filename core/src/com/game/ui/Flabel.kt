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

package com.game.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.game.gamestate.MenuScreen
import com.game.graphics.Canvas
import com.game.maths.Vec
import com.game.settings.Settings
import com.game.time.Stopwatch

open class Flabel(
        menu: MenuScreen,
        pos: Vec,
        font: BitmapFont,
        text: String,
        scale: Float,
        colour: Color,
        centreX: Boolean,
        centreY: Boolean,
        private val timePeriodTicks: Int,
        private val amplitude: Float,
        onSelect: () -> Unit = {}
): Label(menu, pos, font, text, scale, colour, centreX, centreY, onSelect) {

    private val stopwatch = Stopwatch()

    override fun draw(canvas: Canvas, origin: Vec) {
        stopwatch.tick()

        val offset = if (Settings.getBool("ui-movement")) {
            Vec(0f, amplitude * stopwatch.angle(timePeriodTicks).sin())
        } else {
            Vec.NULL
        }

        super.draw(canvas, origin + offset)
    }

}
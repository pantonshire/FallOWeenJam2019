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
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.game.gamestate.MenuScreen
import com.game.graphics.Canvas
import com.game.maths.Vec

open class Label(
        menu: MenuScreen,
        pos: Vec,
        private val font: BitmapFont,
        private val text: String,
        private val scale: Float,
        private val colour: Color,
        private val centreX: Boolean,
        private val centreY: Boolean,
        onSelect: () -> Unit = {}
): MenuElement(menu, pos, onSelect) {

    val textSize: Vec

    init {
        font.data.setScale(scale, scale)
        val layout = GlyphLayout(font, text)
        textSize = Vec(layout.width, layout.height)
    }

    override fun draw(canvas: Canvas, origin: Vec) {
        canvas.drawText(text, origin + pos, font, colour, centreX, centreY, scale)
    }

    override fun selectionBoxPosition() = Vec(
            if (centreX) { pos.x } else { pos.x + (textSize.x / 2f) },
            if (centreY) { pos.y } else { pos.y + (textSize.y / 2f) }
    )

    override fun selectionBoxSize() = Vec(
            textSize.x + 64f,
            textSize.y + 16f
    )

}
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

import com.game.graphics.Canvas
import com.game.maths.Vec

abstract class GameState {

    private val canvas = Canvas()

    abstract fun update(delta: Float)

    abstract fun draw(canvas: Canvas)

    abstract fun onEnter()

    abstract fun onExit()

    fun render() {
        this.canvas.beginBatch()
        this.draw(this.canvas)
        this.canvas.endBatch()
    }

    fun resize(width: Int, height: Int) {
        this.canvas.updateViewport(width, height)
    }

    fun toScreenPos(worldPos: Vec) =
            this.canvas.project(worldPos)

    fun toWorldPos(screenPos: Vec) =
            this.canvas.unproject(screenPos)

}
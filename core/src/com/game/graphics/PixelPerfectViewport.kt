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

package com.game.graphics

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.game.maths.Vec
import kotlin.math.floor

class PixelPerfectViewport(worldSize: Vec, camera: Camera): ScalingViewport(Scaling.fill, worldSize.x, worldSize.y, camera) {

    override fun update(screenWidth: Int, screenHeight: Int, centerCamera: Boolean) {
        val scaled = scaling.apply(worldWidth, worldHeight, screenWidth.toFloat(), screenHeight.toFloat())
        val viewportWidth = (worldWidth * floor(scaled.x / worldWidth)).toInt()
        val viewportHeight = (worldHeight * floor(scaled.y / worldHeight)).toInt()
        setScreenBounds((screenWidth - viewportWidth) / 2, (screenHeight - viewportHeight) / 2, viewportWidth, viewportHeight)
        apply(centerCamera)
    }

}
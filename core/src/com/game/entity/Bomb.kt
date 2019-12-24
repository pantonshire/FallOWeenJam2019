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

package com.game.entity

import com.game.gamestate.World
import com.game.graphics.Canvas
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper

class Bomb(world: World, position: Vec): Entity(world, Vec(10f, 18f), position) {

    private val animationPath = "bomb"
    private val animation = AssetManagerWrapper.INSTANCE.fetchAnimation(animationPath)

    private var animationTime = 0f

    override fun entityUpdateLate(delta: Float) {
        animationTime += delta
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRegionCentred(animation["bomb"].getKeyFrame(animationTime), position)
    }

    override fun onSpawn() {

    }

    override fun onRemoved() {
        AssetManagerWrapper.INSTANCE.unload(animationPath)
    }

}
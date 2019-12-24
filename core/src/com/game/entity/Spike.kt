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

class Spike(world: World, position: Vec, val inverted: Boolean = false): Entity(world, Vec(12f, 8f), position) {

    private val texturePath = if (inverted) { "invertedSpike.png" } else { "spike.png" }

    init {
        AssetManagerWrapper.INSTANCE.loadTexture(texturePath)
    }

    override fun entityUpdateLate(delta: Float) {
        if (intersects(world.player) && world.player.velocity.y * if (inverted) { -1f } else { 1f } <= 0) {
            world.player.kill()
        }
    }

    override fun draw(canvas: Canvas) {
        canvas.drawTextureCentred(AssetManagerWrapper.INSTANCE.getTexture(texturePath), position)
//        canvas.drawTexture(AssetManagerWrapper.INSTANCE.getTexture("debug.png"), position)
//        canvas.drawTexture(AssetManagerWrapper.INSTANCE.getTexture("debug.png"), position - extents.xComponent())
//        canvas.drawTexture(AssetManagerWrapper.INSTANCE.getTexture("debug.png"), position + extents.xComponent())
    }

    override fun onSpawn() {

    }

    override fun onRemoved() {
        AssetManagerWrapper.INSTANCE.unload(texturePath)
    }

}
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

class Door(world: World, position: Vec): Entity(world, Vec(8f, 10f), position) {

    private val doorTexturePath = "door.png"
    private val exitTexturePath = "exit.png"

    init {
        AssetManagerWrapper.INSTANCE.loadTexture(doorTexturePath)
        AssetManagerWrapper.INSTANCE.loadTexture(exitTexturePath)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawTextureCentred(AssetManagerWrapper.INSTANCE.getTexture(doorTexturePath), position)
        canvas.drawTextureCentred(AssetManagerWrapper.INSTANCE.getTexture(exitTexturePath), position + Vec(0f, 20f))
    }

    override fun onSpawn() {

    }

    override fun onRemoved() {
        AssetManagerWrapper.INSTANCE.unload(doorTexturePath)
        AssetManagerWrapper.INSTANCE.unload(exitTexturePath)
    }

}
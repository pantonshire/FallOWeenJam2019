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
import com.game.maths.Angle
import com.game.maths.Vec
import com.game.random.Dice
import com.game.resources.AssetManagerWrapper
import com.game.settings.Settings

class PlayableDoor(world: World, modifiers: Array<String>, position: Vec): Playable(world, Vec(12f, 24f), modifiers, position) {

    private val doorTexturePath = "door.png"
    private val exitTexturePath = "exit.png"

    var wasOnGround = true

    init {
        AssetManagerWrapper.INSTANCE.loadTexture(doorTexturePath)
        AssetManagerWrapper.INSTANCE.loadTexture(exitTexturePath)
        walkSpeed = 1.5f
        jumpSpeed = 3.5f
        wallJumpHSpeed = 1.5f
        wallJumpVSpeed = 3.5f
    }

    override fun entityUpdateLate(delta: Float) {
        super.entityUpdateLate(delta)

        if (Settings.getBool("particles")) {
            if (velocity.x != 0f && onGround && framesAlive % 10 == 0) {
                world.queueSpawn(Particle(world, Vec(position.x, position.y - 11), Dice.FAIR.rollF(0.4f..0.8f), if (velocity > 0f) {
                    Angle.HALF - Angle(Dice.FAIR.rollF(0f..0.2f))
                } else {
                    Angle(Dice.FAIR.rollF(0f..0.2f))
                }))
            }

            if ((onGround || justJumped) && !wasOnGround) {
                for (i in 0..4) {
                    world.queueSpawn(Particle(world, Vec(position.x, position.y - 11), Dice.FAIR.rollF(0.4f..1f), Angle.HALF - Angle(Dice.FAIR.rollF(0f..0.5f))))
                    world.queueSpawn(Particle(world, Vec(position.x, position.y - 11), Dice.FAIR.rollF(0.4f..1f), Angle(Dice.FAIR.rollF(0f..0.5f))))
                }
            }
        }

        wasOnGround = onGround
    }

    override fun draw(canvas: Canvas) {
        canvas.drawTextureCentred(AssetManagerWrapper.INSTANCE.getTexture(doorTexturePath), position)
        canvas.drawTextureCentred(AssetManagerWrapper.INSTANCE.getTexture(exitTexturePath), position + Vec(0f, 20f))
    }

    override fun onRemoved() {
        AssetManagerWrapper.INSTANCE.unload(doorTexturePath)
        AssetManagerWrapper.INSTANCE.unload(exitTexturePath)
    }

    override fun onSpawn() {

    }

}
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

class SpikeBlock(world: World, position: Vec, val maxWait: Int = 80, initialWait: Int = 0): PhysicsEntity(world, Vec(20f, 20f), 0f, position) {

    private val animationPath = "spikeBlock"
    private val animation = AssetManagerWrapper.INSTANCE.fetchAnimation(animationPath)

    private val speed = 10f

    private var waitTime = initialWait
    private var travellingDown = true

    private var currentAnimation = "neutral"
    private var animationTime = 0f

    init {
        slam()
    }

    override fun entityUpdateLate(delta: Float) {
        animationTime += delta

        if (intersects(world.player)) {
            world.player.kill()
        }

        if (velocity.y == 0f) {
            waitTime --
            if (waitTime <= 0) {
                travellingDown = !travellingDown
                slam()
            } else {
                currentAnimation = "neutral"
            }
        }
    }

    fun slam() {
        waitTime = maxWait
        velocity = Vec(velocity.x, if (travellingDown) { -speed } else { speed })
        currentAnimation = "move_nr"
        animationTime = 0f
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRegionCentred(animation[currentAnimation].getKeyFrame(animationTime), position)
//        canvas.drawTexture(AssetManagerWrapper.INSTANCE.getTexture("debug.png"), position)
//        canvas.drawTexture(AssetManagerWrapper.INSTANCE.getTexture("debug.png"), position - extents.xComponent())
//        canvas.drawTexture(AssetManagerWrapper.INSTANCE.getTexture("debug.png"), position + extents.xComponent())
    }

    override fun onSpawn() {

    }

    override fun onRemoved() {
        AssetManagerWrapper.INSTANCE.unload(animationPath)
    }

}
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

import com.game.audio.AudioManager
import com.game.audio.SoundCategory
import com.game.gamestate.World
import com.game.graphics.Canvas
import com.game.maths.Vec
import com.game.random.Dice
import com.game.resources.AssetManagerWrapper

class Spring(
        world: World,
        position: Vec,
        private val ySpeed: Float = 7.5f,
        private val inverted: Boolean = false,
        private val flip: Boolean = false
): Entity(world, Vec(24f, 14f), position) {

    private val direction = if (inverted) { -1f } else { 1f }

    private val animationPath = "spring"
    private val animation = AssetManagerWrapper.INSTANCE.fetchAnimation(animationPath)

    private var currentAnimation = "neutral"
    private var animationTime = 0f

    override fun entityUpdateLate(delta: Float) {
        animationTime += delta

        if (intersects(world.player) && world.player.velocity.y * direction < 0f && !world.player.onGround && (!flip || world.player.gravity * direction > 0f)) {
            AudioManager.playSound(AssetManagerWrapper.INSTANCE.getSound("jump.wav"),
                    SoundCategory.GAMEPLAY, 0.4f, Dice.FAIR.rollF(0.7f..1.3f))

            if (flip) {
                world.player.forceFlipGravity()
            } else {
                world.player.forceJump(ySpeed * direction)
            }

            currentAnimation = "bounce_nr"
            animationTime = 0f
        }

        if (currentAnimation == "bounce_nr" && animation[currentAnimation].isAnimationFinished(animationTime)) {
            currentAnimation = "neutral"
        }
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRegionCentred(animation[currentAnimation].getKeyFrame(animationTime), position, yScale = direction)
    }

    override fun onSpawn() {

    }

    override fun onRemoved() {
        AssetManagerWrapper.INSTANCE.unload(animationPath)
    }

}
package com.game.entity

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
            AssetManagerWrapper.INSTANCE.getSound("jump.wav").play(0.4f, Dice.FAIR.rollF(0.7f..1.3f), 0f)

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
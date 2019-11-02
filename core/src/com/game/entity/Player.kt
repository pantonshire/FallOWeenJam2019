package com.game.entity

import com.game.gamestate.World
import com.game.graphics.Canvas
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper
import kotlin.math.sign

class Player(world: World, modifiers: Array<String>, position: Vec): Playable(world, Vec(10f, 19f), modifiers, position) {

    val animationPath = "player"
    val animation = AssetManagerWrapper.INSTANCE.fetchAnimation(animationPath)

    var currentAnimation = "idle"
    var animationTime = 0f

    override fun entityUpdateLate(delta: Float) {
        super.entityUpdateLate(delta)

        val lastAnimation = currentAnimation
        currentAnimation = action

        if (currentAnimation != lastAnimation) {
            animationTime = 0f
        } else {
            animationTime += delta
        }
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRegionCentred(animation[currentAnimation].getKeyFrame(animationTime), position, xScale = if(facingRight) { 1f } else { -1f }, yScale = sign(gravity))
    }

    override fun onRemoved() {
        AssetManagerWrapper.INSTANCE.unload(animationPath)
    }

    override fun onSpawn() {

    }

}
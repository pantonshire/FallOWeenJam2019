package com.game.entity

import com.badlogic.gdx.graphics.Color
import com.game.gamestate.World
import com.game.graphics.Canvas
import com.game.level.Modifiers
import com.game.maths.Maths
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sign

class Player(world: World, modifiers: Array<String>, position: Vec): Playable(world, Vec(10f, 19f), modifiers, position) {

    val animationPath = "player"
    val animation = AssetManagerWrapper.INSTANCE.fetchAnimation(animationPath)

    var currentAnimation = "idle"
    var animationTime = 0f

    override fun entityUpdateLate(delta: Float) {
        if (onGround && currentAnimation == "jump_nr") {
            currentAnimation = "idle"
            animationTime = 0f
        }

        super.entityUpdateLate(delta)

        val lastAnimation = currentAnimation
        currentAnimation = animationFromAction(action)

        if (currentAnimation != lastAnimation) {
            animationTime = 0f
        } else {
            animationTime += delta
        }
    }

    override fun draw(canvas: Canvas) {
        if (modifier(Modifiers.FADE)) {
            canvas.colour = Color(1f, 1f, 1f, Maths.clamp(cos(framesAlive / 30f) + 0.75f, 0f, 1f))
            canvas.drawRegionCentred(animation[currentAnimation].getKeyFrame(animationTime), position, xScale = if(facingRight) { 1f } else { -1f }, yScale = sign(gravity))
            canvas.colour = Color.WHITE
        } else {
            canvas.drawRegionCentred(animation[currentAnimation].getKeyFrame(animationTime), position, xScale = if(facingRight) { 1f } else { -1f }, yScale = sign(gravity))
        }

//        canvas.drawTexture(AssetManagerWrapper.INSTANCE.getTexture("debug.png"), position)
//        canvas.drawTexture(AssetManagerWrapper.INSTANCE.getTexture("debug.png"), position - extents.xComponent())
//        canvas.drawTexture(AssetManagerWrapper.INSTANCE.getTexture("debug.png"), position + extents.xComponent())
    }

    override fun onRemoved() {
        AssetManagerWrapper.INSTANCE.unload(animationPath)
    }

    override fun onSpawn() {

    }

    private fun animationFromAction(action: String) = when(action) {
        "jump"  -> "jump_nr"
        else    -> action
    }

}
package com.game.entity

import com.game.gamestate.World
import com.game.graphics.Canvas
import com.game.maths.Vec
import com.game.random.Dice
import com.game.resources.AssetManagerWrapper

class Spring(world: World, position: Vec, val ySpeed: Float = 7.5f): Entity(world, Vec(24f, 14f), position) {

    private val texturePath = "spring.png"

    init {
        AssetManagerWrapper.INSTANCE.loadTexture(texturePath)
    }

    override fun entityUpdateLate(delta: Float) {
        if (intersects(world.player) && world.player.velocity.y < 0f && !world.player.onGround) {
            AssetManagerWrapper.INSTANCE.getSound("jump.wav").play(0.4f, Dice.FAIR.rollF(0.7f..1.3f), 0f)
            world.player.forceJump(ySpeed)
        }
    }

    override fun draw(canvas: Canvas) {
        canvas.drawTextureCentred(AssetManagerWrapper.INSTANCE.getTexture(texturePath), position)
    }

    override fun onSpawn() {

    }

    override fun onRemoved() {
        AssetManagerWrapper.INSTANCE.unload(texturePath)
    }

}
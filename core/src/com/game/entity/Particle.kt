package com.game.entity

import com.badlogic.gdx.graphics.Color
import com.game.gamestate.World
import com.game.graphics.Canvas
import com.game.maths.Angle
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper

class Particle(world: World, position: Vec, speed: Float, angle: Angle, val lifetime: Int = 30): Entity(world, Vec(2f, 2f), position) {

    private val texturePath = "particle.png"

    private var ticksLeft = lifetime

    init {
        AssetManagerWrapper.INSTANCE.loadTexture(texturePath)
        velocity = Vec(speed, angle)
    }

    override fun entityUpdateLate(delta: Float) {
        ticksLeft --
        if (ticksLeft <= 0) {
            retire()
        }
    }

    override fun draw(canvas: Canvas) {
        canvas.colour = Color(1f, 1f, 1f, ticksLeft.toFloat() / lifetime.toFloat())
        canvas.drawTextureCentred(AssetManagerWrapper.INSTANCE.getTexture(texturePath), position)
        canvas.colour = Color.WHITE
    }

    override fun onSpawn() {

    }

    override fun onRemoved() {
        AssetManagerWrapper.INSTANCE.unload(texturePath)
    }

}
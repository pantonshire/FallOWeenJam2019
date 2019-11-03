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
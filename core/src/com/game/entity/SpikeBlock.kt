package com.game.entity

import com.game.gamestate.World
import com.game.graphics.Canvas
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper

class SpikeBlock(world: World, position: Vec): PhysicsEntity(world, Vec(20f, 20f), 0f, position) {

    private val texturePath = "spikeBlock.png"

    private val speed = 10f
    private val maxWait = 80

    private var waitTime = 0
    private var travellingDown = true

    init {
        AssetManagerWrapper.INSTANCE.loadTexture(texturePath)
        slam()
    }

    override fun entityUpdateLate(delta: Float) {
        if (intersects(world.player)) {
            world.player.kill()
        }

        if (velocity.y == 0f) {
            waitTime --
            if (waitTime <= 0) {
                travellingDown = !travellingDown
                slam()
            }
        }
    }

    fun slam() {
        waitTime = maxWait
        velocity = Vec(velocity.x, if (travellingDown) { -speed } else { speed })
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
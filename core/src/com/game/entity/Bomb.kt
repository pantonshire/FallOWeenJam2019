package com.game.entity

import com.game.gamestate.World
import com.game.graphics.Canvas
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper

class Bomb(world: World, position: Vec): Entity(world, Vec(10f, 18f), position) {

    val animationPath = "bomb"
    val animation = AssetManagerWrapper.INSTANCE.fetchAnimation(animationPath)

    var animationTime = 0f

    override fun entityUpdateLate(delta: Float) {
        animationTime += delta
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRegionCentred(animation["bomb"].getKeyFrame(animationTime), position)
    }

    override fun onSpawn() {

    }

    override fun onRemoved() {
        AssetManagerWrapper.INSTANCE.unload(animationPath)
    }

}
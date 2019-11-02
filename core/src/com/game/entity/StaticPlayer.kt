package com.game.entity

import com.game.gamestate.World
import com.game.graphics.Canvas
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper

class StaticPlayer(world: World, position: Vec): Entity(world, Vec(8f, 10f), position) {

    private val texturePath = "idlePlayer.png"

    init {
        AssetManagerWrapper.INSTANCE.loadTexture(texturePath)
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
package com.game.entity

import com.game.gamestate.World
import com.game.graphics.Canvas
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper

class Door(world: World, position: Vec): Entity(world, Vec(8f, 10f), position) {

    private val doorTexturePath = "door.png"
    private val exitTexturePath = "exit.png"

    init {
        AssetManagerWrapper.INSTANCE.loadTexture(doorTexturePath)
        AssetManagerWrapper.INSTANCE.loadTexture(exitTexturePath)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawTextureCentred(AssetManagerWrapper.INSTANCE.getTexture(doorTexturePath), position)
        canvas.drawTextureCentred(AssetManagerWrapper.INSTANCE.getTexture(exitTexturePath), position + Vec(0f, 20f))
    }

    override fun onSpawn() {

    }

    override fun onRemoved() {
        AssetManagerWrapper.INSTANCE.unload(doorTexturePath)
        AssetManagerWrapper.INSTANCE.unload(exitTexturePath)
    }

}
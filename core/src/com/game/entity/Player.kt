package com.game.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.game.gamestate.World
import com.game.graphics.Canvas
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper
import java.awt.Point
import kotlin.math.sign

class Player(world: World, position: Vec): Entity(world, Vec(32f, 32f), position) {

    val texturePath = "player.png"
    val walkSpeed = 3f

    init {
        AssetManagerWrapper.INSTANCE.loadTexture(texturePath)
    }

    override fun entityUpdateLate(delta: Float) {
        val inLeft = Gdx.input.isKeyPressed(Input.Keys.A)
        val inRight = Gdx.input.isKeyPressed(Input.Keys.D)

        if (inLeft != inRight) {
            velocity = Vec(if (inLeft) { -walkSpeed } else { walkSpeed }, velocity.y)
        } else {
            velocity = Vec(0f, velocity.y)
        }
    }

    override fun draw(canvas: Canvas) {
        canvas.drawTextureCentred(AssetManagerWrapper.INSTANCE.getTexture(texturePath), position)
    }

    override fun onRemoved() {
        AssetManagerWrapper.INSTANCE.unloadTexture(texturePath)
    }

    override fun onSpawn() {
        velocity = Vec(0f, -1f)
    }

    override fun handleCollisions(nextH: Vec, nextV: Vec, translation: Vec): Vec {
        var finalTranslation = translation

        if (translation.x != 0f) {
            val xSign = sign(translation.x)
            val column = world.map.toMapX(nextH.x + (extents.x * xSign))
            for (row in getOccupyingTilesV(nextH)) {
                if (world.map.isSolid(Point(column, row))) {
                    finalTranslation = Vec(0f, finalTranslation.y)
                    val correctedX = column * world.map.tileSize - (xSign * (extents.x + 0.00390625f)) - (world.map.tileSize * (xSign - 1) / 2f)
                    position = Vec(correctedX, position.y)
                    break
                }
            }
        }

        if (translation.y != 0f) {
            val ySign = sign(translation.y)
            val row = world.map.toMapY(nextV.y + (extents.y * ySign))
            for (column in getOccupyingTilesH(nextV)) {
                if (world.map.isSolid(Point(column, row))) {
                    finalTranslation = Vec(finalTranslation.x, 0f)
                    val correctedY = row * world.map.tileSize - (ySign * (extents.y + 0.00390625f)) - (world.map.tileSize * (ySign - 1) / 2f)
                    position = Vec(position.x, correctedY)
                    break
                }
            }
        }

        return finalTranslation
    }

}
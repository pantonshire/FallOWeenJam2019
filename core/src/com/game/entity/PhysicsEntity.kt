package com.game.entity

import com.game.gamestate.World
import com.game.maths.Vec
import java.awt.Point
import kotlin.math.sign

abstract class PhysicsEntity(world: World, size: Vec, var gravity: Float, initialPosition: Vec): Entity(world, size, initialPosition) {

    var onGround = false

    override fun entityUpdateEarly(delta: Float) {
        if (velocity.y != 0f) {
            onGround = false
        }
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
            /* Doing y collision detection relative to position with x translation added improves the accuracy
               of corner collision detection. */
            for (column in getOccupyingTilesH(nextV + finalTranslation.xComponent())) {
                if (world.map.isSolid(Point(column, row))) {
                    finalTranslation = Vec(finalTranslation.x, 0f)
                    val correctedY = row * world.map.tileSize - (ySign * (extents.y + 0.00390625f)) - (world.map.tileSize * (ySign - 1) / 2f)
                    position = Vec(position.x, correctedY)
                    onGround = sign(gravity) != sign(translation.y)
                    break
                }
            }
        }

        return finalTranslation
    }

}
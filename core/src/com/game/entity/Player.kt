package com.game.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.game.gamestate.World
import com.game.graphics.Canvas
import com.game.maths.Maths
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper
import java.awt.Point
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

class Player(world: World, position: Vec): Entity(world, Vec(32f, 32f), position) {

    val texturePath = "player.png"
    val walkAcceleration = 0.2f
    val walkDeceleration = 0.2f
    val brakeDeceleration = 0.4f
    val midairBrakeDeceleration = 0.8f
    val walkSpeed = 3f
    val jumpSpeed = 4.5f
    val gravity = 0.2f
    val terminalVelocity = 5f

    var touchingWallLeft = false
    var touchingWallRight = false
    var onGround = false
    var jumpInputBuffer = 0
    var coyoteTime = 0

    init {
        AssetManagerWrapper.INSTANCE.loadTexture(texturePath)
    }

    override fun entityUpdateEarly(delta: Float) {
        if (velocity.y != 0f) {
            onGround = false
        }

        if (velocity.x != 0f) {
            touchingWallLeft = false
            touchingWallRight = false
        }
    }

    override fun entityUpdateLate(delta: Float) {
        val inLeft = Gdx.input.isKeyPressed(Input.Keys.A)
        val inRight = Gdx.input.isKeyPressed(Input.Keys.D)
        val inJump = Gdx.input.isKeyJustPressed(Input.Keys.SPACE)

        if (onGround) {
            coyoteTime = 6
        } else if(coyoteTime > 0) {
            coyoteTime--
        }

        if (inJump) {
            jumpInputBuffer = 20
        } else if (jumpInputBuffer > 0) {
            jumpInputBuffer--
        }

        if (inLeft != inRight) {
            val targetDirection = if (inLeft) { -1f } else { 1f }

            val acceleration = if (velocity.x == 0f || sign(velocity.x) == targetDirection) {
                walkAcceleration * targetDirection
            } else if (onGround) {
                brakeDeceleration
            } else {
                midairBrakeDeceleration
            } * targetDirection

            velocity = Vec(Maths.clamp(velocity.x + acceleration, -walkSpeed, walkSpeed), velocity.y)
        } else if (velocity.x != 0f) {
            val movingRight = velocity.x > 0f

            velocity = if (movingRight) {
                Vec(max(velocity.x - walkDeceleration, 0f), velocity.y)
            } else {
                Vec(min(velocity.x + walkDeceleration, 0f), velocity.y)
            }
        }

//        velocity = if (inLeft != inRight) {
//            Vec(if (inLeft) { -walkSpeed } else { walkSpeed }, velocity.y)
//        } else {
//            Vec(0f, velocity.y)
//        }

        if (coyoteTime > 0 && jumpInputBuffer > 0) {
            coyoteTime = 0
            velocity = Vec(velocity.x, jumpSpeed)
        } else if (!onGround) {
            velocity = Vec(velocity.x, max(velocity.y - gravity, -terminalVelocity))
        }
    }

    override fun draw(canvas: Canvas) {
        canvas.drawTextureCentred(AssetManagerWrapper.INSTANCE.getTexture(texturePath), position)
    }

    override fun onRemoved() {
        AssetManagerWrapper.INSTANCE.unload(texturePath)
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
                    if (xSign > 0) {
                        touchingWallRight = true
                    } else {
                        touchingWallLeft = true
                    }
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
                    onGround = true
                    break
                }
            }
        }

        return finalTranslation
    }

}
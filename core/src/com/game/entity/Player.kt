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

// TODO: short jumps / high jumps
class Player(world: World, position: Vec): Entity(world, Vec(10f, 19f), position) {

    val animationPath = "player"
    val animation = AssetManagerWrapper.INSTANCE.fetchAnimation(animationPath)

    val walkAcceleration = 0.2f
    val walkDeceleration = 0.3f
    val wallJumpDeceleration = 0.01f
    val brakeDeceleration = 0.6f
    val midairBrakeDeceleration = 1.0f
    val wallJumpBrakeDeceleration = 0.05f
    val walkSpeed = 3f
    val jumpSpeed = 4.5f
    val wallJumpHSpeed = 3f
    val wallJumpVSpeed = 5f
    val gravity = 0.2f
    val wallSlideGravity = 0.02f
    val terminalVelocity = 5f
    val wallSlideTerminalVelocity = 1.5f

    var touchingWallLeft = false
    var touchingWallRight = false
    var lastWallTouchDirection = 1
    var onGround = false
    var wallJumping = false
    var jumpInputBuffer = 0
    var coyoteTime = 0
    var wallCoyoteTime = 0

    var currentAnimation = "NONE"
    var animationTime = 0f
    var facingRight = true

    override fun entityUpdateEarly(delta: Float) {
        if (velocity.y != 0f) {
            onGround = false
        }
    }

    override fun entityUpdateLate(delta: Float) {
        val inLeft = Gdx.input.isKeyPressed(Input.Keys.A)
        val inRight = Gdx.input.isKeyPressed(Input.Keys.D)
        val inJump = Gdx.input.isKeyJustPressed(Input.Keys.SPACE)

        checkTouchingWall()

        var wallSliding = !onGround && (touchingWallLeft || touchingWallRight) && velocity.y < 0 && ((inRight && touchingWallRight) || (inLeft && touchingWallLeft))

        if (touchingWallLeft || touchingWallRight) {
            wallCoyoteTime = 8
            lastWallTouchDirection = if (touchingWallLeft) { 1 } else { -1 }
        } else {
            wallCoyoteTime--
        }

        if (onGround) {
            coyoteTime = 6
            wallJumping = false
            wallCoyoteTime = 0
        } else if(coyoteTime > 0) {
            coyoteTime--
        }

        if (inJump) {
            jumpInputBuffer = 20
        } else if (jumpInputBuffer > 0) {
            jumpInputBuffer--
        }

        if (inLeft != inRight) {
            if (onGround) {
                facingRight = inRight
            }
            val targetDirection = if (inLeft) { -1f } else { 1f }
            val acceleration = if (velocity.x == 0f || sign(velocity.x) == targetDirection) {
                walkAcceleration * targetDirection
            } else if (onGround) {
                brakeDeceleration * targetDirection
            } else if (wallJumping) {
                wallJumpBrakeDeceleration
            } else {
                midairBrakeDeceleration
            } * targetDirection
            val maxSpeed = if (wallJumping) {
                wallJumpHSpeed
            } else {
                walkSpeed
            }
            velocity = Vec(Maths.clamp(velocity.x + acceleration, -maxSpeed, maxSpeed), velocity.y)
        } else if (velocity.x != 0f) {
            val movingRight = velocity.x > 0f
            val deceleration = if (wallJumping) { wallJumpDeceleration } else { walkDeceleration }
            velocity = if (movingRight) {
                Vec(max(velocity.x - deceleration, 0f), velocity.y)
            } else {
                Vec(min(velocity.x + deceleration, 0f), velocity.y)
            }
        }

        if (coyoteTime > 0 && jumpInputBuffer > 0) {
            coyoteTime = 0
            jumpInputBuffer = 0
            onGround = false
            velocity = Vec(velocity.x, jumpSpeed)
        } else if (wallCoyoteTime > 0 && !onGround && jumpInputBuffer > 10) {
            coyoteTime = 0
            wallCoyoteTime = 0
            jumpInputBuffer = 0
            wallJumping = true
            onGround = false
            wallSliding = false
            velocity = Vec(wallJumpHSpeed * lastWallTouchDirection, wallJumpVSpeed)
        } else {
            val acceleration = if (wallSliding) { wallSlideGravity } else { gravity }
            val max = if (wallSliding) { wallSlideTerminalVelocity } else { terminalVelocity }
            velocity = Vec(velocity.x, max(velocity.y - acceleration, -max))
        }

        if (!onGround && velocity.x != 0f) {
            facingRight = velocity.x > 0f
        }

        val lastAnimation = currentAnimation

        currentAnimation = if(wallSliding) {
            "slide"
        } else if (!onGround) {
            "jump"
        } else if (onGround && velocity.x != 0f) {
            "walk"
        } else {
            "idle"
        }

        if (currentAnimation != lastAnimation) {
            animationTime = 0f
        } else {
            animationTime += delta
        }
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRegionCentred(animation[currentAnimation].getKeyFrame(animationTime), position, xScale = if(facingRight) { 1f } else { -1f })
    }

    override fun onRemoved() {
        AssetManagerWrapper.INSTANCE.unload(animationPath)
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
                    onGround = true
                    break
                }
            }
        }

        return finalTranslation
    }

    fun checkTouchingWall() {
        val checkDistance = Vec(0.0625f, 0f)
        touchingWallLeft = world.map.isSolid(world.map.toMapPos(position - extents.xComponent() - checkDistance))
        touchingWallRight = world.map.isSolid(world.map.toMapPos(position + extents.xComponent() + checkDistance))
    }

}
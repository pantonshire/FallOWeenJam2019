package com.game.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.game.gamestate.World
import com.game.level.Modifiers
import com.game.maths.Maths
import com.game.maths.Vec
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

// TODO: short jumps / high jumps
abstract class Playable(world: World, size: Vec, val modifiers: Array<String>, initialPosition: Vec): PhysicsEntity(world, size, 0.2f, initialPosition) {

    protected var walkAcceleration = 0.2f
    protected var walkDeceleration = 0.4f
    protected var wallJumpDeceleration = 0.01f
    protected var brakeDeceleration = 0.6f
    protected var midairBrakeDeceleration = 1.0f
    protected var wallJumpBrakeDeceleration = 0.05f
    protected var walkSpeed = 3f
    protected var jumpSpeed = 4.5f
    protected var wallJumpHSpeed = 2.5f
    protected var wallJumpVSpeed = 5f
    protected var wallSlideGravity = 0.02f
    protected var terminalVelocity = 5f
    protected var wallSlideTerminalVelocity = 1.5f

    protected var touchingWallLeft = false
    protected var touchingWallRight = false
    protected var lastWallTouchDirection = 1
    protected var wallJumping = false
    protected var jumpInputBuffer = 0
    protected var coyoteTime = 0
    protected var wallCoyoteTime = 0

    protected var action = "idle"
        private set
    protected var facingRight = true
        private set

    var isDead = false
        private set

    init {
        if (modifier(Modifiers.INV_GRAVITY)) {
            gravity = -0.2f
        }
    }

    fun modifier(name: String) =
            name in modifiers

    fun kill() {
        isDead = true
    }

    override fun entityUpdateLate(delta: Float) {
        val inLeft = Gdx.input.isKeyPressed(Input.Keys.A)
        val inRight = Gdx.input.isKeyPressed(Input.Keys.D)
        val inJump = Gdx.input.isKeyJustPressed(Input.Keys.SPACE)

        checkTouchingWall()

        var wallSliding = !onGround && (touchingWallLeft || touchingWallRight) && velocity.y * sign(gravity) <= 0 && ((inRight && touchingWallRight) || (inLeft && touchingWallLeft))

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

        val jumping = if (modifier(Modifiers.POGO)) {
            onGround
        } else {
            coyoteTime > 0 && jumpInputBuffer > 0
        }

        if (jumping) {
            coyoteTime = 0
            jumpInputBuffer = 0
            onGround = false
            if (modifier(Modifiers.JUMP_INV_GRAVITY)) {
                gravity = -gravity
            } else {
                velocity = Vec(velocity.x, jumpSpeed * sign(gravity))
            }
        } else if (wallCoyoteTime > 0 && !onGround && jumpInputBuffer > 10) {
            coyoteTime = 0
            wallCoyoteTime = 0
            jumpInputBuffer = 0
            wallJumping = true
            onGround = false
            wallSliding = false
            if (modifier(Modifiers.JUMP_INV_GRAVITY)) {
                gravity = -gravity
                velocity = Vec(wallJumpHSpeed * lastWallTouchDirection, velocity.y)
            } else {
                velocity = Vec(wallJumpHSpeed * lastWallTouchDirection, wallJumpVSpeed * sign(gravity))
            }
        } else {
            val acceleration = if (wallSliding) { wallSlideGravity } else { gravity }
            val max = if (wallSliding) { wallSlideTerminalVelocity } else { terminalVelocity }
            velocity = if (gravity < 0) {
                Vec(velocity.x, min(velocity.y + abs(acceleration), max))
            } else {
                Vec(velocity.x, max(velocity.y - acceleration, -max))
            }
        }

        if (!onGround && velocity.x != 0f) {
            facingRight = velocity.x > 0f
        }

        action = if(wallSliding) {
            "slide"
        } else if (!onGround) {
            "jump"
        } else if (onGround && velocity.x != 0f) {
            "walk"
        } else {
            "idle"
        }
    }

    private fun checkTouchingWall() {
        val checkDistance = Vec(0.0625f, 0f)
        touchingWallLeft = world.map.isSolid(world.map.toMapPos(position - extents.xComponent() - checkDistance))
        touchingWallRight = world.map.isSolid(world.map.toMapPos(position + extents.xComponent() + checkDistance))
    }

}
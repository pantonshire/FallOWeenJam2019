package com.game.entity

import com.game.gamestate.World
import com.game.graphics.Canvas
import com.game.maths.Vec
import com.game.tilemap.TileMap

abstract class Entity(val world: World, val size: Vec, initialPosition: Vec) {

    val extents = this.size / 2f

    var position = initialPosition
    var velocity = Vec()

    var retired = false
        private set

    abstract fun draw(canvas: Canvas)

    abstract fun onRemoved()

    abstract fun onSpawn()

    open fun entityUpdateEarly(delta: Float) {}

    open fun entityUpdateLate(delta: Float) {}

    open fun handleCollisions(nextH: Vec, nextV: Vec, translation: Vec): Vec =
            translation

    fun update(delta: Float) {
        this.entityUpdateEarly(delta)

        this.velocity = this.handleCollisions(
                Vec(this.position.x + this.velocity.x, this.position.y),
                Vec(this.position.x, this.position.y + this.velocity.y),
                this.velocity
        )

        this.position += this.velocity

        this.entityUpdateLate(delta)
    }

    fun retire() {
        retired = true
    }

    fun intersects(other: Entity, futurePosition: Vec = this.position): Boolean =
            futurePosition.x - this.extents.x < other.position.x + other.extents.x &&
                futurePosition.x + this.extents.x > other.position.x - other.extents.x &&
                futurePosition.y - this.extents.y < other.position.y + other.extents.y &&
                futurePosition.y + this.extents.y > other.position.y - other.extents.y

    fun getOccupyingTilesH(futurePosition: Vec = this.position) =
            this.world.map.toMapX(futurePosition.x - this.extents.x)..this.world.map.toMapX(futurePosition.x + this.extents.x)

    fun getOccupyingTilesV(futurePosition: Vec = this.position) =
            this.world.map.toMapX(futurePosition.y - this.extents.y)..this.world.map.toMapX(futurePosition.y + this.extents.y)

}
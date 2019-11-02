package com.game.gamestate

import com.game.entity.Entity
import com.game.entity.Playable
import com.game.graphics.Canvas
import com.game.tilemap.TileMap

abstract class World: GameState() {

    abstract val map: TileMap
    abstract val player: Playable

    val entities = ArrayList<Entity>()

    override fun update(delta: Float) {
        this.entities.forEach { it.update(delta) }
        this.entities.filter { it.retired }.forEach { it.onRemoved() }
        this.entities.removeIf { it.retired }
    }

    override fun draw(canvas: Canvas) {
        this.map.draw(canvas)
        this.entities.forEach { it.draw(canvas) }
    }

    override fun onExit() {
        this.entities.forEach { it.onRemoved() }
    }

    fun spawn(entity: Entity) {
        this.entities.add(entity)
        entity.onSpawn()
    }

}
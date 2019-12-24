/*
 * Copyright (C) 2019 Thomas Panton
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.game.gamestate

import com.game.entity.Entity
import com.game.entity.Playable
import com.game.graphics.Canvas
import com.game.tilemap.TileMap

abstract class World: GameState() {

    abstract val map: TileMap
    abstract val player: Playable

    val entities = ArrayList<Entity>()
    val toSpawn = ArrayList<Entity>()

    override fun update(delta: Float) {
        this.entities.addAll(toSpawn)
        this.toSpawn.forEach { it.onSpawn() }
        this.toSpawn.clear()
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
        this.entities += entity
        entity.onSpawn()
    }

    fun queueSpawn(entity: Entity) {
        this.toSpawn += entity
    }

}
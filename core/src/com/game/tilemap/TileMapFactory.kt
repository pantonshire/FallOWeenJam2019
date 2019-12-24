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

package com.game.tilemap

import com.badlogic.gdx.Gdx
import com.game.maths.Vec
import java.awt.Point

object TileMapFactory {

    fun loadMap(mapFile: String, tileset: String, origin: Vec = Vec()): TileMap {
        val lines = Gdx.files.internal(mapFile).readString().split("\n").map { it -> it.trim() }

        val tileSize = lines[0].toInt()
        val width = lines[1].toInt()
        val height = lines[2].toInt()

        val map = TileMap(tileset, width, height, tileSize, origin)

        lines.subList(3, lines.size).forEachIndexed {
            row, line ->
            line.toCharArray().forEachIndexed {
                column, character ->
                map.setTileAt(Point(column, height - row - 1), getTileFromChar(character))
            }
        }

        return map
    }

    private fun getTileFromChar(character: Char): Tile {
        for (tile in Tile.tileTypes) {
            if (tile.charRepresentation == character) {
                return tile
            }
        }
        return Tile.EMPTY
    }

}
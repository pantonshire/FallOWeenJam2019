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
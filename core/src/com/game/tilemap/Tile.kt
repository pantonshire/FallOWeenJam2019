package com.game.tilemap

class Tile(val solid: Boolean, val visible: Boolean, val charRepresentation: Char, val tilesetX: Int = -1, val tilesetY: Int = -1) {

    companion object {
        val EMPTY = Tile(solid = false, visible = false, charRepresentation = '.')
        val BLOCK = Tile(solid = true, visible = true, charRepresentation = 'B', tilesetX = 1, tilesetY = 0)
    }

}
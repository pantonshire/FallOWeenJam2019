package com.game.tilemap

class Tile(val solid: Boolean, val visible: Boolean, val charRepresentation: Char, val tilesetX: Int = -1, val tilesetY: Int = -1) {

    companion object {
        val EMPTY = Tile(solid = false, visible = false, charRepresentation = '.')
        val FLOOR = Tile(solid = true, visible = true, charRepresentation = 'F', tilesetX = 0, tilesetY = 0)
        val INSIDE = Tile(solid = true, visible = true, charRepresentation = 'I', tilesetX = 1, tilesetY = 0)
        val LWALL = Tile(solid = true, visible = true, charRepresentation = 'L', tilesetX = 2, tilesetY = 0)
        val RWALL = Tile(solid = true, visible = true, charRepresentation = 'R', tilesetX = 3, tilesetY = 0)
        val CEIL = Tile(solid = true, visible = true, charRepresentation = 'C', tilesetX = 4, tilesetY = 0)
        val TLCOR = Tile(solid = true, visible = true, charRepresentation = '1', tilesetX = 5, tilesetY = 0)
        val TRCOR = Tile(solid = true, visible = true, charRepresentation = '2', tilesetX = 6, tilesetY = 0)
        val BLCOR = Tile(solid = true, visible = true, charRepresentation = '3', tilesetX = 7, tilesetY = 0)
        val BRCOR = Tile(solid = true, visible = true, charRepresentation = '4', tilesetX = 8, tilesetY = 0)

        val tileTypes = arrayOf(
                EMPTY,
                FLOOR,
                INSIDE,
                LWALL,
                RWALL,
                CEIL,
                TLCOR,
                TRCOR,
                BLCOR,
                BRCOR
        )
    }

}
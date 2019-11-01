package com.game.gamestate

import com.game.entity.Player
import com.game.graphics.Canvas
import com.game.maths.Vec
import com.game.tilemap.Tile
import com.game.tilemap.TileMap
import java.awt.Point

class TestState: World() {

    override val map: TileMap = TileMap("tileset.png", 100, 100, 32)

    init {
        spawn(Player(this, Vec(64f, 64f)))

        for (x in 0..10) {
            map.setTileAt(Point(x, 0), Tile.BLOCK)
        }

        map.setTileAt(Point(10, 1), Tile.BLOCK)
    }

}
package com.game.gamestate

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.game.entity.Player
import com.game.graphics.Canvas
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper
import com.game.tilemap.Tile
import com.game.tilemap.TileMap
import java.awt.Point

class TestState: World() {

    override val map: TileMap = TileMap("tileset.png", 100, 100, 32)

    var introTimer = 200

    init {
        spawn(Player(this, Vec(64f, 64f)))

        for (x in 0..10) {
            map.setTileAt(Point(x, 0), Tile.BLOCK)
        }

        map.setTileAt(Point(10, 1), Tile.BLOCK)

        for (y in 0..10) {
            map.setTileAt(Point(11, y), Tile.BLOCK)
        }
    }

    override fun update(delta: Float) {
        if (introTimer == 0) {
            super.update(delta)
        }
    }

    override fun draw(canvas: Canvas) {
        if (introTimer == 0) {
            super.draw(canvas)
        } else {
            canvas.drawText("ESCAPE THE ROOM", Vec(canvas.resX / 2f, canvas.resY - 150f), AssetManagerWrapper.INSTANCE.getFont("editundo.ttf"), scale = 3f, centreX = true)

            if (introTimer < 140) {
                canvas.drawText("THE BOMB WILL DETONATE", Vec(canvas.resX / 2f, canvas.resY - 220f), AssetManagerWrapper.INSTANCE.getFont("editundo.ttf"), scale = 2f, centreX = true)
            }

            if (introTimer < 100) {
                canvas.drawText("IN FIFTEEN SECONDS.", Vec(canvas.resX / 2f, canvas.resY - 260f), AssetManagerWrapper.INSTANCE.getFont("editundo.ttf"), scale = 2f, centreX = true)
            }

            introTimer--
        }
    }

}
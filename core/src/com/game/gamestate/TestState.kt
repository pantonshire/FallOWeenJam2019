package com.game.gamestate

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.game.entity.Bomb
import com.game.entity.Door
import com.game.entity.Player
import com.game.graphics.Canvas
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper
import com.game.tilemap.Tile
import com.game.tilemap.TileMap
import java.awt.Point
import kotlin.math.ceil
import kotlin.math.max

class TestState: World() {

    override val map: TileMap = TileMap("tileset.png", 100, 100, 24)

    val textLine1 = "THE BOMB WILL DETONATE"
    val textLine2 = "IN FIFTEEN SECONDS."

    val player = Player(this, Vec(64f, 64f))
    val door = Door(this, Vec(120f, 37f))
    val bomb = Bomb(this, Vec(50f, 34f))

    var introTimer = 200
    var framesLeft = 900

    init {
        spawn(bomb)
        spawn(door)

        spawn(player)

        for (x in 0..90) {
            map.setTileAt(Point(x, 0), Tile.FLOOR)
        }

        map.setTileAt(Point(0, 1), Tile.INSIDE)
        map.setTileAt(Point(4, 3), Tile.INSIDE)
        map.setTileAt(Point(10, 0), Tile.INSIDE)
        map.setTileAt(Point(10, 1), Tile.FLOOR)

        for (y in 0..10) {
            map.setTileAt(Point(11, y), Tile.RWALL)
        }
    }

    override fun update(delta: Float) {
        if (introTimer == 0) {
            super.update(delta)

            if (player.intersects(door)) {
                player.retire()
            } else {
                framesLeft--
            }
        }
    }

    override fun draw(canvas: Canvas) {
        val font = AssetManagerWrapper.INSTANCE.getFont("editundo.ttf")

        if (introTimer == 0) {
            super.draw(canvas)

            val secondsLeft = max(ceil((framesLeft / 60f)).toInt(), 0)
            val secondsStr = if (secondsLeft < 10) { "0$secondsLeft" } else { "$secondsLeft" }
            canvas.drawText(secondsStr, Vec(20f, canvas.resY - 55f), font, scale = 4f)

        } else {
            canvas.drawText("ESCAPE THE ROOM", Vec(canvas.resX / 2f, canvas.resY - 150f), font, scale = 3f, centreX = true)

            if (introTimer < 140) {
                canvas.drawText(textLine1, Vec(canvas.resX / 2f, canvas.resY - 220f), font, scale = 2f, centreX = true)
            }

            if (introTimer < 100) {
                canvas.drawText(textLine2, Vec(canvas.resX / 2f, canvas.resY - 260f), font, scale = 2f, centreX = true)
            }

            introTimer--
        }
    }

}
package com.game.tilemap

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.game.graphics.Canvas
import com.game.maths.Maths
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper
import java.awt.Point

open class TileMap(private val tilesetPath: String, val width: Int, val height: Int, val tileSize: Int, val origin: Vec = Vec()) {

    val tileset: TextureRegion = TextureRegion()

    val tiles = Array(this.width) {
        Array(this.height) {
            Tile.EMPTY
        }
    }

    init {
        AssetManagerWrapper.INSTANCE.loadTexture(tilesetPath)
    }

    fun setTileAt(pos: Point, tile: Tile) {
        tiles[pos.x][pos.y] = tile
    }

    fun tileAt(pos: Point) = if (inBounds(pos)) {
        tiles[pos.x][pos.y]
    } else {
        Tile.EMPTY
    }

    fun isSolid(pos: Point) =
            tileAt(pos).solid

    fun inBounds(pos: Point) =
            pos.x in 0 until width && pos.y in 0 until height

    fun clampMapX(mapX: Int) = when {
        mapX < 0        -> 0
        mapX >= width   -> width - 1
        else            -> mapX
    }

    fun clampMapY(mapY: Int) = when {
        mapY < 0        -> 0
        mapY >= height  -> height - 1
        else            -> mapY
    }

    fun toMapX(worldX: Float) =
            ((worldX + this.origin.x) / this.tileSize).toInt()

    fun toMapY(worldY: Float) =
            ((worldY + this.origin.y) / this.tileSize).toInt()

    fun toMapPos(worldPos: Vec) =
            Point(this.toMapX(worldPos.x), this.toMapY(worldPos.y))

    fun draw(canvas: Canvas) {
        if (AssetManagerWrapper.INSTANCE.applyTexture(tileset, tilesetPath)) {
            val visibleRange = canvas.getVisibleRange()

            var lastX = -1
            var lastY = -1

            for (x in clampMapX(toMapX(visibleRange.first.start))..clampMapX(toMapX(visibleRange.first.endInclusive))) {
                for (y in clampMapY(toMapY(visibleRange.second.start))..clampMapY(toMapY(visibleRange.second.endInclusive))) {
                    val tile = tiles[x][y]
                    if (tile.visible) {
                        if (lastX != tile.tilesetX || lastY != tile.tilesetY) {
                            tileset.setRegion(tile.tilesetX * this.tileSize, tile.tilesetY * this.tileSize, this.tileSize, this.tileSize)
                            lastX = tile.tilesetX
                            lastY = tile.tilesetY
                        }

                        canvas.drawRegion(tileset, Vec((x * this.tileSize).toFloat(), (y * this.tileSize).toFloat()))
                    }
                }
            }
        }
    }

}
package com.game.gamestate

import com.game.Main
import com.game.entity.Door
import com.game.entity.Player
import com.game.graphics.Canvas
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper
import com.game.tilemap.TileMap
import com.game.tilemap.TileMapFactory

class Tutorial2: World() {

    override val map: TileMap = TileMapFactory.loadMap("maps/tutorial2.map", "tileset1.png")
    override val player = Player(this, arrayOf(), Vec(64f, 33f))

    val door = Door(this, Vec(552f, 109f))

    var introTimer = 200

    var done = false
    var won = false
    var outroTimer = 100
    var displayText = ""

    var shownLine1 = false
    var shownLine2 = false

    init {
        AssetManagerWrapper.INSTANCE.loadTexture("blackBox.png")

        spawn(door)
        spawn(player)
    }

    override fun update(delta: Float) {
        if (introTimer <= 0 && !done) {
            super.update(delta)

            if (player.isDead) {
                done = true
                displayText = "TRY AGAIN"
                AssetManagerWrapper.INSTANCE.getSound("death.wav").play()
            } else if (player.intersects(door)) {
                player.retire()
                done = true
                won = true
                displayText = "ESCAPED!"
                AssetManagerWrapper.INSTANCE.getSound("pass.wav").play()
            }
        } else if (done) {
            outroTimer--
            if (outroTimer <= 0) {
                Main.gsm.queueState(if (won) { Tutorial3() } else { Tutorial2() })
            }
        }
    }

    override fun draw(canvas: Canvas) {
        val font = AssetManagerWrapper.INSTANCE.getFont("editundo.ttf")

        if (introTimer == 0) {
            super.draw(canvas)

            canvas.drawText("WALL JUMP WHEN", Vec(220f, 110f), font)
            canvas.drawText("TOUCHING WALL", Vec(220f, 90f), font)

            if (done) {
                canvas.drawTextureCentred(AssetManagerWrapper.INSTANCE.getTexture("blackBox.png"), Vec(canvas.resX / 2f, canvas.resY / 2f), width = 200f, height = 50f)
                canvas.drawText(displayText, Vec(canvas.resX / 2f, canvas.resY / 2f), font, scale = 2f, centreX = true, centreY = true)
            }

        } else {
            canvas.drawText("ESCAPE THE ROOM", Vec(canvas.resX / 2f, canvas.resY - 150f), font, scale = 3f, centreX = true)

            if (introTimer < 140) {
                if (!shownLine1) { AssetManagerWrapper.INSTANCE.getSound("impact.wav").play() }
                shownLine1 = true
                canvas.drawText("LET\'S TRY SOMETHING", Vec(canvas.resX / 2f, canvas.resY - 220f), font, scale = 2f, centreX = true)
            }

            if (introTimer < 100) {
                if (!shownLine2) { AssetManagerWrapper.INSTANCE.getSound("impact.wav").play() }
                shownLine2 = true
                canvas.drawText("A LITTLE MORE ADVANCED.", Vec(canvas.resX / 2f, canvas.resY - 260f), font, scale = 2f, centreX = true)
            }

            introTimer--
        }
    }

    override fun onExit() {
        super.onExit()
        AssetManagerWrapper.INSTANCE.unload("blackBox.png")
    }

}
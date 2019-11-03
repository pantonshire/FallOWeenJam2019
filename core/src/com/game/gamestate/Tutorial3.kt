package com.game.gamestate

import com.game.Main
import com.game.entity.Bomb
import com.game.entity.Door
import com.game.entity.Player
import com.game.graphics.Canvas
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper
import com.game.tilemap.TileMap
import com.game.tilemap.TileMapFactory
import kotlin.math.ceil
import kotlin.math.max

class Tutorial3: World() {

    override val map: TileMap = TileMapFactory.loadMap("maps/tutorial2.map", "tileset1.png")
    override val player = Player(this, arrayOf(), Vec(64f, 33f))

    val door = Door(this, Vec(552f, 109f))
    val bomb = Bomb(this, Vec(216f, 178f))

    var introTimer = 200
    var framesLeft = 480

    var done = false
    var won = false
    var exploded = false
    var outroTimer = 100
    var displayText = ""

    var shownLine1 = false
    var shownLine2 = false

    init {
        AssetManagerWrapper.INSTANCE.loadTexture("blackBox.png")
        AssetManagerWrapper.INSTANCE.loadTexture("explosion.png")

        spawn(bomb)
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
                displayText = "TUTORIAL COMPLETE"
                AssetManagerWrapper.INSTANCE.getSound("pass.wav").play()
            } else {
                framesLeft--
                if (framesLeft <= 0) {
                    done = true
                    exploded = true
                    displayText = "TRY AGAIN"
                    AssetManagerWrapper.INSTANCE.getSound("explosion.wav").play()
                } else if(framesLeft % 60 == 0) {
                    AssetManagerWrapper.INSTANCE.getSound("tick.wav").play(1.5f)
                }
            }
        } else if (done) {
            outroTimer--
            if (outroTimer <= 0) {
                Main.gsm.queueState(if (won) { MainMenu() } else { Tutorial3() })
            }
        }
    }

    override fun draw(canvas: Canvas) {
        val font = AssetManagerWrapper.INSTANCE.getFont("editundo.ttf")

        if (introTimer == 0) {
            super.draw(canvas)

            val secondsLeft = max(ceil((framesLeft / 60f)).toInt(), 0)
            val secondsStr = if (secondsLeft < 10) { "0$secondsLeft" } else { "$secondsLeft" }
            canvas.drawTextureCentred(AssetManagerWrapper.INSTANCE.getTexture("blackBox.png"), Vec(45f, canvas.resY - 45f), width = 72f, height = 72f)
            canvas.drawText(secondsStr, Vec(45f, canvas.resY - 45f), font, scale = 2f, centreX = true, centreY = true)

            if (done) {
                if (exploded) {
                    canvas.drawTextureCentred(AssetManagerWrapper.INSTANCE.getTexture("explosion.png"), bomb.position, width = 32f * (101 - outroTimer), height = 32f * (101 - outroTimer))
                }

                canvas.drawTextureCentred(AssetManagerWrapper.INSTANCE.getTexture("blackBox.png"), Vec(canvas.resX / 2f, canvas.resY / 2f), width = if (won) { 350f } else { 200f }, height = 50f)
                canvas.drawText(displayText, Vec(canvas.resX / 2f, canvas.resY / 2f), font, scale = 2f, centreX = true, centreY = true)
            }

        } else {
            canvas.drawText("ESCAPE THE ROOM", Vec(canvas.resX / 2f, canvas.resY - 150f), font, scale = 3f, centreX = true)

            if (introTimer < 140) {
                if (!shownLine1) { AssetManagerWrapper.INSTANCE.getSound("impact.wav").play() }
                shownLine1 = true
                canvas.drawText("THE BOMB WILL DETONATE", Vec(canvas.resX / 2f, canvas.resY - 220f), font, scale = 2f, centreX = true)
            }

            if (introTimer < 100) {
                if (!shownLine2) { AssetManagerWrapper.INSTANCE.getSound("impact.wav").play() }
                shownLine2 = true
                canvas.drawText("IN EIGHT SECONDS.", Vec(canvas.resX / 2f, canvas.resY - 260f), font, scale = 2f, centreX = true)
            }

            introTimer--
        }
    }

    override fun onExit() {
        super.onExit()
        AssetManagerWrapper.INSTANCE.unload("blackBox.png")
        AssetManagerWrapper.INSTANCE.unload("explosion.png")
    }

}
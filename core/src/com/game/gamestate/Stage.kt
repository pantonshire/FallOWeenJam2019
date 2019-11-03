package com.game.gamestate

import com.game.Main
import com.game.entity.Entity
import com.game.graphics.Canvas
import com.game.level.Score
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper
import com.game.tilemap.TileMap
import com.game.tilemap.TileMapFactory
import kotlin.math.ceil
import kotlin.math.max

abstract class Stage(val nextState: GameState, val stageNo: Int, mapFile: String, tileset: String, bombFrames: Int, val line1: String, val line2: String): World() {

    abstract val door: Entity
    abstract val bomb: Entity

    override val map: TileMap = TileMapFactory.loadMap("maps/${mapFile}.map", "${tileset}.png")

//    var introTimer = 200
    var introTimer = 0
    var framesLeft = bombFrames

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
    }

    protected fun spawnEssentialEntities() {
        spawn(bomb)
        spawn(door)
        spawn(player)
    }

    override fun update(delta: Float) {
        if (introTimer <= 0 && !done) {
            super.update(delta)
            if (player.isDead) {
                done = true
                displayText = "FAILURE"
                AssetManagerWrapper.INSTANCE.getSound("death.wav").play()
            } else if (player.intersects(door)) {
                Score.winStage()
                player.retire()
                done = true
                won = true
                displayText = "ESCAPED!"
                AssetManagerWrapper.INSTANCE.getSound("pass.wav").play()
            } else {
                framesLeft--
                if (framesLeft <= 0) {
                    done = true
                    exploded = true
                    displayText = "KABOOM!"
                    AssetManagerWrapper.INSTANCE.getSound("explosion.wav").play()
                } else if(framesLeft % 60 == 0) {
                    AssetManagerWrapper.INSTANCE.getSound("tick.wav").play(1.5f)
                }
            }
        } else if (done) {
            outroTimer--
            if (outroTimer <= 0) {
                Main.gsm.queueState(nextState)
            }
        }
    }

    override fun draw(canvas: Canvas) {
        val font = AssetManagerWrapper.INSTANCE.getFont("editundo.ttf")

        if (introTimer == 0) {
            super.draw(canvas)

            val secondsLeft = max(ceil((framesLeft / 60f)).toInt(), 0)
            val secondsStr = if (secondsLeft < 10) { "0$secondsLeft" } else { "$secondsLeft" }
            canvas.drawTextureCentred(AssetManagerWrapper.INSTANCE.getTexture("blackBox.png"), Vec(35f, canvas.resY - 35f), width = 48f, height = 48f)
            canvas.drawText(secondsStr, Vec(35f, canvas.resY - 35f), font, scale = 2f, centreX = true, centreY = true)

            if (done) {
                if (exploded) {
                    canvas.drawTextureCentred(AssetManagerWrapper.INSTANCE.getTexture("explosion.png"), bomb.position, width = 32f * (101 - outroTimer), height = 32f * (101 - outroTimer))
                }

                canvas.drawTextureCentred(AssetManagerWrapper.INSTANCE.getTexture("blackBox.png"), Vec(canvas.resX / 2f, canvas.resY / 2f), width = 200f, height = 50f)
                canvas.drawText(displayText, Vec(canvas.resX / 2f, canvas.resY / 2f), font, scale = 2f, centreX = true, centreY = true)
            }

        } else {
            canvas.drawText("ESCAPE THE ROOM", Vec(canvas.resX / 2f, canvas.resY - 150f), font, scale = 3f, centreX = true)

            if (introTimer < 140) {
                if (!shownLine1) { AssetManagerWrapper.INSTANCE.getSound("impact.wav").play() }
                shownLine1 = true
                canvas.drawText(line1, Vec(canvas.resX / 2f, canvas.resY - 220f), font, scale = 2f, centreX = true)
            }

            if (introTimer < 100) {
                if (!shownLine2) { AssetManagerWrapper.INSTANCE.getSound("impact.wav").play() }
                shownLine2 = true
                canvas.drawText(line2, Vec(canvas.resX / 2f, canvas.resY - 260f), font, scale = 2f, centreX = true)
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
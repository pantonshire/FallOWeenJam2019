package com.game.gamestate

import com.game.Main
import com.game.entity.Entity
import com.game.graphics.Canvas
import com.game.level.Level
import com.game.level.Score
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper
import com.game.tilemap.TileMap
import com.game.tilemap.TileMapFactory
import kotlin.math.ceil
import kotlin.math.max

abstract class Stage(
        private val level: Level,
        private val nextState: GameState,
        public val stageNo: Int,
        mapFile: String,
        tileset: String,
        bombFrames: Int,
        private val intro: Array<String>,
        skipIntro: Boolean = false,
        private val hasTimeLimit: Boolean = true,
        private val retryOnDeath: Boolean = false
): World() {

    abstract val door: Entity
    abstract val bomb: Entity?

    override val map: TileMap = TileMapFactory.loadMap("maps/${mapFile}.map", "${tileset}.png")

    private val introLength = if (skipIntro) { 0 } else { 200 }
    private var introTimer = introLength
    private var framesLeft = bombFrames

    private var done = false
    private var won = false
    private var exploded = false
    private val outroLength = 40
    private val explosionLength = 80
    private var outroTimer = 0
    private var displayText = ""

    private var shownLine1 = false
    private var shownLine2 = false

    init {
        AssetManagerWrapper.INSTANCE.loadTexture("particle.png")
        AssetManagerWrapper.INSTANCE.loadTexture("blackBox.png")
        AssetManagerWrapper.INSTANCE.loadTexture("explosion.png")
    }

    protected open fun spawnEssentialEntities() {
        if (bomb != null) { spawn(bomb!!) }
        spawn(door)
        spawn(player)
    }

    override fun update(delta: Float) {
        if (introTimer <= 0 && !done) {
            super.update(delta)
            if (player.isDead) {
                done = true
                displayText = "FAILURE"
                outroTimer = outroLength
                AssetManagerWrapper.INSTANCE.getSound("death.wav").play()
            } else if (player.intersects(door)) {
                Score.winStage()
                player.retire()
                done = true
                won = true
                displayText = "ESCAPED!"
                outroTimer = outroLength
                AssetManagerWrapper.INSTANCE.getSound("pass.wav").play()
            } else if (hasTimeLimit) {
                framesLeft--
                if (framesLeft <= 0) {
                    done = true
                    exploded = true
                    displayText = "KABOOM!"
                    outroTimer = explosionLength
                    AssetManagerWrapper.INSTANCE.getSound("explosion.wav").play()
                } else if(framesLeft % 60 == 0) {
                    AssetManagerWrapper.INSTANCE.getSound("tick.wav").play(1.5f)
                }
            }
        } else if (done) {
            outroTimer--
            if (outroTimer <= 0) {
                Main.gsm.queueState(if (!won && retryOnDeath) {
                    level.makeStage(stageNo, nextState, skipIntro = true)
                } else {
                    nextState
                })
            }
        }
    }

    override fun draw(canvas: Canvas) {
        val font = AssetManagerWrapper.INSTANCE.getFont("editundo.ttf")

        if (introTimer == 0) {
            super.draw(canvas)

            if (hasTimeLimit) {
                val secondsLeft = max(ceil((framesLeft / 60f)).toInt(), 0)
                val secondsStr = if (secondsLeft < 10) {
                    "0$secondsLeft"
                } else {
                    "$secondsLeft"
                }
                canvas.drawTextureCentred(AssetManagerWrapper.INSTANCE.getTexture("blackBox.png"), Vec(35f, canvas.resY - 35f), width = 48f, height = 48f)
                canvas.drawText(secondsStr, Vec(35f, canvas.resY - 35f), font, scale = 2f, centreX = true, centreY = true)
            }

            if (done) {
                if (exploded) {
                    val explosionCentre = bomb?.position ?: Vec.NULL
                    val explosionScale = 32f * (explosionLength - outroTimer + 1)
                    canvas.drawTextureCentred(AssetManagerWrapper.INSTANCE.getTexture("explosion.png"), explosionCentre, width = explosionScale, height = explosionScale)
                }

                canvas.drawTextureCentred(AssetManagerWrapper.INSTANCE.getTexture("blackBox.png"), Vec(canvas.resX / 2f, canvas.resY / 2f), width = 200f, height = 50f)
                canvas.drawText(displayText, Vec(canvas.resX / 2f, canvas.resY / 2f), font, scale = 2f, centreX = true, centreY = true)
            }
        } else {
            canvas.drawText("ESCAPE THE ROOM", Vec(canvas.resX / 2f, canvas.resY - 150f), font, scale = 3f, centreX = true)

            if (introTimer < 140) {
                if (!shownLine1) { AssetManagerWrapper.INSTANCE.getSound("impact.wav").play() }
                shownLine1 = true
                canvas.drawText(intro[0], Vec(canvas.resX / 2f, canvas.resY - 220f), font, scale = 2f, centreX = true)
            }

            if (introTimer < 100) {
                if (!shownLine2) { AssetManagerWrapper.INSTANCE.getSound("impact.wav").play() }
                shownLine2 = true
                canvas.drawText(intro[1], Vec(canvas.resX / 2f, canvas.resY - 260f), font, scale = 2f, centreX = true)
            }

            introTimer--
        }
    }

    override fun onExit() {
        super.onExit()
        AssetManagerWrapper.INSTANCE.unload("particle.png")
        AssetManagerWrapper.INSTANCE.unload("blackBox.png")
        AssetManagerWrapper.INSTANCE.unload("explosion.png")
    }

}
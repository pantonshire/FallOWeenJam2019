package com.game.gamestate

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.game.Main
import com.game.graphics.Canvas
import com.game.level.Level
import com.game.level.LevelFactory
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper
import com.game.level.Score

class LevelSelect: GameState() {

    private val maxOption = 4
    private var option = 0

    override fun update(delta: Float) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            option--
            if (option < 0) {
                option = maxOption
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            option++
            if (option > maxOption) {
                option = 0
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            AssetManagerWrapper.INSTANCE.getSound("select.wav").play()
            when (option) {
                0 -> {
                    Score.newLevel()
                    Main.gsm.queueState(LevelFactory.assembleLevel(Level.TUTORIAL, baseCase = LevelSelect(), shuffle = false))
                }
                1 -> {
                    Score.newLevel()
                    Main.gsm.queueState(LevelFactory.assembleLevel(Level.JAM1))
                }
                2 -> {
                    Score.newLevel()
                    Main.gsm.queueState(LevelFactory.assembleLevel(Level.JAM2))
                }
                3 -> {
                    Score.newLevel()
                    Main.gsm.queueState(LevelFactory.assembleLevel(Level.JAM3))
                }
                4 -> Main.gsm.queueState(MainMenu())
            }
        }
    }

    override fun draw(canvas: Canvas) {
        val font = AssetManagerWrapper.INSTANCE.getFont("editundo.ttf")
        canvas.drawText("SELECT A LEVEL", Vec(canvas.resX / 2f, canvas.resY - 60f), font, scale = 3f, centreX = true)

        canvas.drawText("TUTORIAL", Vec(canvas.resX / 2f, canvas.resY - 120f), font, scale = 2f, centreX = true)
        canvas.drawText("LEVEL 1", Vec(canvas.resX / 2f, canvas.resY - 160f), font, scale = 2f, centreX = true)
        canvas.drawText("LEVEL 2", Vec(canvas.resX / 2f, canvas.resY - 200f), font, scale = 2f, centreX = true)
        canvas.drawText("LEVEL 3", Vec(canvas.resX / 2f, canvas.resY - 240f), font, scale = 2f, centreX = true)
        canvas.drawText("BACK", Vec(canvas.resX / 2f, canvas.resY - 280f), font, scale = 2f, centreX = true)

        canvas.drawText(">          <", Vec(canvas.resX / 2f, canvas.resY - 120f - (40f * option)), font, scale = 2f, centreX = true)
    }

    override fun onExit() {

    }

}
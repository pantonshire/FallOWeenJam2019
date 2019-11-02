package com.game.gamestate

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.game.Main
import com.game.graphics.Canvas
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper

class MainMenu: GameState() {

    private val maxOption = 1
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
            if (option == 0) {
                Main.gsm.queueState(LevelSelect())
            } else if (option == 1) {
                Gdx.app.exit()
            }
        }
    }

    override fun draw(canvas: Canvas) {
        val font = AssetManagerWrapper.INSTANCE.getFont("editundo.ttf")
        canvas.drawText("ESCAPE THE ROOM", Vec(canvas.resX / 2f, canvas.resY - 120f), font, scale = 4f, centreX = true)

        canvas.drawText("PLAY", Vec(canvas.resX / 2f, canvas.resY - 200f), font, scale = 2f, centreX = true)
        canvas.drawText("EXIT", Vec(canvas.resX / 2f, canvas.resY - 250f), font, scale = 2f, centreX = true)

        canvas.drawText(">      <", Vec(canvas.resX / 2f, canvas.resY - 200f - (50f * option)), font, scale = 2f, centreX = true)
    }

    override fun onExit() {

    }

}
package com.game.gamestate

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.game.Main
import com.game.graphics.Canvas
import com.game.level.Score
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper

class ResultsScreen(val levelName: String, val max: Int): GameState() {

    var timeLeft = 40
    var currentStage = 0
    var currentScore = 0

    override fun update(delta: Float) {
        if (currentStage == 2 && timeLeft == 0 && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Main.gsm.queueState(LevelSelect())
        }
    }

    override fun draw(canvas: Canvas) {
        val font = AssetManagerWrapper.INSTANCE.getFont("editundo.ttf")
        canvas.drawText("$levelName RESULTS", Vec(canvas.resX / 2f, canvas.resY - 120f), font, scale = 3f, centreX = true)

        if (timeLeft > 0) {
            timeLeft--
        }

        if (currentStage == 0 && timeLeft <= 0) {
            currentStage++
            timeLeft = 20
        }

        if (currentStage >= 1) {
            if (currentStage == 1 && timeLeft <= 0) {
                if (currentScore < Score.score) {
                    currentScore++
                    timeLeft = 10
                } else {
                    currentStage++
                    timeLeft = 40
                }
            }

            canvas.drawText("YOU ESCAPED:", Vec(canvas.resX / 2f, canvas.resY - 180f), font, scale = 2f, centreX = true)
            canvas.drawText("$currentScore of $max ROOMS", Vec(canvas.resX / 2f, canvas.resY - 210f), font, scale = 2f, centreX = true)

            if (currentStage == 2) {
                canvas.drawText(getPersonalisedResultMessage(), Vec(canvas.resX / 2f, canvas.resY - 240f), font, scale = 2f, centreX = true)
                if (timeLeft == 0) {
                    canvas.drawText("(PRESS SPACE TO CONTINUE)", Vec(canvas.resX / 2f, canvas.resY - 300f), font, scale = 2f, centreX = true)
                }
            }
        }
    }

    override fun onExit() {

    }

    private fun getPersonalisedResultMessage() = when {
        Score.score == max      -> "CONGRATULATIONS!"
        Score.score > max / 2   -> "WELL DONE!"
        else                    -> "BETTER LUCK NEXT TIME!"
    }

}
package com.game.gamestate

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.game.Main
import com.game.gameplay.Level
import com.game.gameplay.Score
import com.game.graphics.Canvas
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper

class ResultsScreen(private val level: Level): GameState() {

    var timeLeft = 40
    var currentPhase = 0
    var currentScore = 0

    override fun update(delta: Float) {
        if (currentPhase == 2 && timeLeft == 0 && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Main.gsm.queueCollapseTo(LevelSelect())
        }
    }

    override fun draw(canvas: Canvas) {
        val font = AssetManagerWrapper.INSTANCE.getFont("editundo.ttf")
        canvas.drawText("${level.levelName} RESULTS", Vec(canvas.resX / 2f, canvas.resY - 120f), font, scale = 3f, centreX = true)

        if (timeLeft > 0) {
            timeLeft--
        }

        if (currentPhase == 0 && timeLeft <= 0) {
            currentPhase++
            timeLeft = 20
        }

        if (currentPhase >= 1) {
            if (currentPhase == 1 && timeLeft <= 0) {
                timeLeft = if (currentScore < Score.currentScore) {
                    currentScore++
                    10
                } else {
                    currentPhase++
                    40
                }
            }

            canvas.drawText("YOU ESCAPED:", Vec(canvas.resX / 2f, canvas.resY - 180f), font, scale = 2f, centreX = true)
            canvas.drawText("$currentScore OF ${level.noStages} ROOMS", Vec(canvas.resX / 2f, canvas.resY - 210f), font, scale = 2f, centreX = true)

            if (currentPhase == 2) {
                canvas.drawText(getPersonalisedResultMessage(), Vec(canvas.resX / 2f, canvas.resY - 240f), font, scale = 2f, centreX = true)
                if (timeLeft == 0) {
                    canvas.drawText("(PRESS SPACE TO CONTINUE)", Vec(canvas.resX / 2f, canvas.resY - 300f), font, scale = 2f, centreX = true)
                }
            }
        }
    }

    override fun onEnter() {

    }

    override fun onExit() {

    }

    private fun getPersonalisedResultMessage() = when {
        Score.isNewHighScore() && Score.hasPlayedBefore()   -> "NEW HIGH SCORE!"
        Score.currentScore == level.noStages                -> "PERFECT! CONGRATULATIONS!"
        Score.currentScore > level.noStages / 2             -> "WELL DONE!"
        else                                                -> "BETTER LUCK NEXT TIME!"
    }

}
package com.game.gamestate

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.game.Main
import com.game.graphics.Canvas
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper
import com.game.level.Score
import com.game.random.Dice

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
            if (option == 0) {
                Main.gsm.queueState(Tutorial1())
            } else if (option == 1) {
                Score.newLevel()
                Main.gsm.queueState(assembleLevel(0))
            } else if (option == 2) {

            } else if (option == 4) {
                Main.gsm.queueState(MainMenu())
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

    private fun assembleLevel(levelID: Int): GameState {
        val noStages = getNoStages(levelID)
        return getStage(levelID, 0, assembleLevelRecursive(levelID, MutableList(getNoStages(levelID) - 1) { it + 1 }, 1, ResultsScreen(getLevelName(levelID), noStages)), 0)
    }

    private fun assembleLevelRecursive(levelID: Int, stageIDs: MutableList<Int>, depth: Int, baseCase: GameState): GameState {
        if (stageIDs.isEmpty()) {
            return baseCase
        }

        val index = Dice.FAIR.roll(stageIDs.indices)
        val stageID = stageIDs[index]
        stageIDs.removeAt(index)
        return getStage(levelID, stageID, assembleLevelRecursive(levelID, stageIDs, depth + 1, baseCase), depth)
    }

    private fun getStage(levelID: Int, stageID: Int, nextState: GameState, orderedStageNo: Int) = when (levelID) {
        0 -> when (stageID) {
            0       -> L1S1(nextState, orderedStageNo)
            1       -> L1S2(nextState, orderedStageNo)
            2       -> L1S3(nextState, orderedStageNo)
            3       -> L1S4(nextState, orderedStageNo)
            4       -> L1S5(nextState, orderedStageNo)
            5       -> L1S6(nextState, orderedStageNo)
            6       -> L1S7(nextState, orderedStageNo)
            else    -> L1S1(nextState, orderedStageNo)
        }

        else -> L1S1(nextState, orderedStageNo)
    }

    private fun getNoStages(levelID: Int) = when (levelID) {
        0       -> 7
        else    -> 1
    }

    private fun getLevelName(levelID: Int) = when (levelID) {
        0       -> "LEVEL 1"
        else    -> "UNDEFINED"
    }

}
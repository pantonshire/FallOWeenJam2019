package com.game.level

import com.game.gamestate.GameState
import com.game.gamestate.Level1Stage
import com.game.gamestate.Level2Stage
import com.game.gamestate.Level3Stage

enum class Level(val levelName: String, val noStages: Int) {

    JAM1("Level 1", 7),
    JAM2("Level 2", 7),
    JAM3("Level 3", 4);

    fun makeStage(stageID: Int, nextState: GameState) = when (this) {
        JAM1 -> Level1Stage(nextState, stageID)
        JAM2 -> Level2Stage(nextState, stageID)
        JAM3 -> Level3Stage(nextState, stageID)
    }

}
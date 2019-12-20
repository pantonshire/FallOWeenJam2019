package com.game.level

import com.game.gamestate.*

enum class Level(val levelName: String, val noStages: Int) {

    TUTORIAL("Tutorial", 3),
    JAM1("Level 1", 7),
    JAM2("Level 2", 7),
    JAM3("Level 3", 4);

    fun makeStage(stageID: Int, nextState: GameState, skipIntro: Boolean = false) = when (this) {
        TUTORIAL -> TutorialStage(this, nextState, stageID, skipIntro = skipIntro)
        JAM1 -> Level1Stage(this, nextState, stageID, skipIntro = skipIntro)
        JAM2 -> Level2Stage(this, nextState, stageID, skipIntro = skipIntro)
        JAM3 -> Level3Stage(this, nextState, stageID, skipIntro = skipIntro)
    }

}
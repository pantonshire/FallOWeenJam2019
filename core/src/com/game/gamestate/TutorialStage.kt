package com.game.gamestate

import com.game.entity.*
import com.game.level.Level
import com.game.maths.Vec

class TutorialStage(level: Level, nextState: GameState, stageNo: Int, skipIntro: Boolean = false): Stage(
        level,
        nextState,
        stageNo,
        when (stageNo) {
            1, 2 -> "tutorial2"
            else -> "tutorial1"
        },
        "tileset1",
        480,
        when (stageNo) {
            1 -> arrayOf("NOW LET\'S TRY", "SOME WALL JUMPS.")
            2 -> arrayOf("THE BOMB WILL DETONATE", "IN EIGHT SECONDS.")
            else -> arrayOf("ESCAPE VIA", "THE DOOR.")
        },
        skipIntro = skipIntro,
        hasTimeLimit = stageNo == 2,
        retryOnDeath = true
) {

    override val player = Player(this, arrayOf(), Vec(64f, 33f))
    override val door = Door(this, Vec(552f, 109f))

    override val bomb: Entity? = if (stageNo == 2) {
        Bomb(this, Vec(216f, 178f))
    } else {
        null
    }

    init {
        spawnEssentialEntities()
    }

}
package com.game.gameplay

import com.game.gamestate.*
import com.game.random.Dice

object LevelFactory {

    fun assembleLevel(level: Level, baseCase: GameState = ResultsScreen(level), scored: Boolean = true, shuffle: Boolean = true): Stage {
        val firstStage = level.makeStage(0,
                assembleLevelRecursive(level, MutableList(level.noStages - 1) { it + 1 }, baseCase, shuffle))

        if (scored) {
            Score.startLevel(level)
        }

        return firstStage
    }

    private fun assembleLevelRecursive(level: Level, stageIDs: MutableList<Int>, baseCase: GameState, shuffle: Boolean): GameState {
        if (stageIDs.isEmpty()) {
            return baseCase
        }

        val index = if (shuffle) { Dice.FAIR.roll(stageIDs.indices) } else { 0 }
        val stageID = stageIDs[index]
        stageIDs.removeAt(index)
        return level.makeStage(stageID, assembleLevelRecursive(level, stageIDs, baseCase, shuffle))
    }

}
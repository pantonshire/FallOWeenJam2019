package com.game.level

import com.game.gamestate.*
import com.game.random.Dice

object LevelFactory {

    fun assembleLevel(level: Level): Stage {
        return level.makeStage(0, assembleLevelRecursive(
                level,
                MutableList(level.noStages - 1) { it + 1 },
                    ResultsScreen(level.levelName, level.noStages)
                )
        )
    }

    private fun assembleLevelRecursive(level: Level, stageIDs: MutableList<Int>, baseCase: GameState): GameState {
        if (stageIDs.isEmpty()) {
            return baseCase
        }

        val index = Dice.FAIR.roll(stageIDs.indices)
        val stageID = stageIDs[index]
        stageIDs.removeAt(index)
        return level.makeStage(stageID, assembleLevelRecursive(level, stageIDs, baseCase))
    }

}
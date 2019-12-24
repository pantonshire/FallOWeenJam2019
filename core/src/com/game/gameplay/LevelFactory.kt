/*
 * Copyright (C) 2019 Thomas Panton
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.game.gameplay

import com.game.gamestate.GameState
import com.game.gamestate.ResultsScreen
import com.game.gamestate.Stage
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
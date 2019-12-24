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
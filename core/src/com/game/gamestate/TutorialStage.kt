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

package com.game.gamestate

import com.game.entity.Bomb
import com.game.entity.Door
import com.game.entity.Entity
import com.game.entity.Player
import com.game.gameplay.Level
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
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

import com.game.entity.*
import com.game.gameplay.Level
import com.game.gameplay.Modifiers
import com.game.maths.Vec

class Level2Stage(level: Level, nextState: GameState, stageNo: Int, skipIntro: Boolean = false): Stage(
        level,
        nextState,
        stageNo,
        "level2",
        "tileset1",
        720,
        when (stageNo) {
            1 -> arrayOf("GRAVITY IS", "REVERSED.")
            2 -> arrayOf("THE BOMB", "IS THE EXIT.")
            3 -> arrayOf("JUMPING ON SPRINGS", "REVERSES GRAVITY.")
            4 -> arrayOf("YOU INTERMITTENTLY", "BECOME INVISIBLE.")
            5 -> arrayOf("MOVEMENT CONTROLS", "ARE INVERTED.")
            6 -> arrayOf("ICE", "PHYSICS.")
            else -> arrayOf("JUMP ON SPRINGS", "FOR EXTRA HEIGHT.")
        },
        skipIntro = skipIntro
) {

    override val player = Player(
            this,
            when (stageNo) {
                1 -> arrayOf(Modifiers.INV_GRAVITY)
                4 -> arrayOf(Modifiers.FADE)
                5 -> arrayOf(Modifiers.INV_CONTROLS)
                6 -> arrayOf(Modifiers.ICE_PHYSICS)
                else -> arrayOf()
            },
            Vec(64f, 34f)
    )

    override val door = if (stageNo == 2) {
        Bomb(this, Vec(80f, 274f))
    } else {
        Door(this, Vec(432f, 277f))
    }

    override val bomb = if (stageNo == 2) {
        door
    } else {
        Bomb(this, Vec(80f, 274f))
    }

    init {
        val springsFlip = stageNo == 3

        spawn(Spring(this, Vec(228f, 31f), flip = springsFlip))
        spawn(Spring(this, Vec(348f, 31f), flip = springsFlip))
        spawn(Spring(this, Vec(372f, 31f), flip = springsFlip))
        spawn(Spring(this, Vec(588f, 79f), flip = springsFlip))
        spawn(Spring(this, Vec(372f, 329f), inverted = true, flip = springsFlip))

        spawn(SpikeBlock(this, Vec(276f, 264f), maxWait = 60))
        spawn(SpikeBlock(this, Vec(180f, 288f), maxWait = 120))
        spawn(Spike(this, Vec(396f, 151f)))
        spawn(Spike(this, Vec(540f, 329f), inverted = true))

        for (i in 0..4) {
            spawn(Spike(this, Vec(468f + (24f * i), 31f)))
        }

        spawnEssentialEntities()
    }

    override fun spawnEssentialEntities() {
        if (stageNo == 2) {
            spawn(Door(this, Vec(432f, 277f)))
            spawn(door)
            spawn(player)
        } else {
            super.spawnEssentialEntities()
        }
    }

}
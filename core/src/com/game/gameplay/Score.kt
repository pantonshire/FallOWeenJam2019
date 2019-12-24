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

object Score {

    const val NOT_PLAYED = -1

    var currentLevel: Level? = null
        private set
    var currentScore = 0
        private set
    var previousHighScore = 0
        private set

    private val highScores = HashMap<Level, Int>()

    fun startLevel(level: Level) {
        currentLevel = level
        previousHighScore = highScoreFor(currentLevel)
        currentScore = 0
    }

    fun onStageWon() {
        currentScore++
        commitScore()
    }

    fun commitScore() {
        if (currentLevel != null && isNewHighScore()) {
            highScores[currentLevel!!] = currentScore
        }
    }

    fun isNewHighScore() =
            previousHighScore == NOT_PLAYED || currentScore > previousHighScore

    fun hasPlayedBefore() =
            previousHighScore != NOT_PLAYED

    fun highScoreFor(level: Level?) =
            highScores[level] ?: NOT_PLAYED

}
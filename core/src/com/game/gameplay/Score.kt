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
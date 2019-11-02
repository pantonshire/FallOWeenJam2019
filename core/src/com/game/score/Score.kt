package com.game.score

object Score {

    var score = 0
        private set

    fun newLevel() {
        score = 0
    }

    fun winStage() {
        score++
    }

}
package com.game.level

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
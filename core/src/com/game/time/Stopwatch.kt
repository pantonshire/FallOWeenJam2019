package com.game.time

import com.game.maths.Angle

class Stopwatch {

    var ticks: Int = 0
        private set

    fun angle(cycleTicks: Int) =
            Angle.fromPercent(ticks / cycleTicks.toFloat())

    fun tick() {
        ticks++
    }

    fun reset() {
        ticks = 0
    }

}
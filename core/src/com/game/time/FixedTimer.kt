package com.game.time

import com.game.maths.Angle

class FixedTimer(val maxTicks: Int, initialTicks: Int = maxTicks) {

    var remainingTicks: Int = initialTicks
        private set

    fun elapsedTicks(): Int =
            maxTicks - remainingTicks

    fun angle() =
            Angle.fromPercent(elapsedTicks() / maxTicks.toFloat())

    fun tick(): Boolean {
        return if (remainingTicks > 0) {
            remainingTicks--
            remainingTicks == 0
        } else {
            false
        }
    }

    fun doBeforeTick(onTick: (remaining: Int, elapsed: Int) -> Unit): Boolean {
        onTick(remainingTicks, elapsedTicks())
        return tick()
    }

    fun doAfterTick(onTick: (remaining: Int, elapsed: Int) -> Unit): Boolean {
        val isDone = tick()
        onTick(remainingTicks, elapsedTicks())
        return isDone
    }

    fun reset() {
        remainingTicks = maxTicks
    }

}
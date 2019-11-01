package com.game.random

import com.game.maths.Angle
import com.game.maths.Maths
import com.game.maths.Vec

abstract class Dice {

    companion object {
        val FAIR = FairDice()
    }

    /**
     * Returns a random integer in the specified range. Range can include negative values.
     * @param range the possible range of the random number
     * @return a random integer in the range
     */
    abstract fun roll(range: IntRange): Int

    abstract fun rollF(range: ClosedFloatingPointRange<Float>): Float

    abstract fun rollD(range: ClosedFloatingPointRange<Double>): Double

    abstract fun chance(p: Double): Boolean

    abstract fun flip(): Boolean

    fun rollAngle() =
            Angle(rollF(0f..Maths.TAU))

    fun rollInaccuracy(max: Angle) =
            Angle(rollF(-max.radians..max.radians))

    fun rollVecInArea(radius: Float) =
            Vec(rollF(0f..radius), rollAngle())

    fun rollVecOnCircumference(radius: Float) =
            Vec(radius, rollAngle())

    fun rollVecInRect(xRange: ClosedFloatingPointRange<Float>, yRange: ClosedFloatingPointRange<Float>) =
            Vec(rollF(xRange), rollF(yRange))

}
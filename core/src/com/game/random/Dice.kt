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

    abstract fun<T> choose(array: Array<T>): T

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
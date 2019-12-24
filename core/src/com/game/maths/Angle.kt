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

package com.game.maths

class Angle(radians: Float): Approximatable<Angle>, Comparable<Angle> {

    companion object {
        val ZERO = Angle(0f)
        val QUARTER_AW = Angle(Maths.PI / 2f)
        val HALF = Angle(Maths.PI)
        val QUARTER_CW = Angle(3f * Maths.PI / 2f)

        fun fromDegrees(degrees: Float) = Angle(degrees * Maths.DEG_TO_RAD)
        fun fromPercent(percentage: Float) = Angle(Maths.PI * (percentage * 2f))
    }

    val radians = Maths.modTau(radians)

    fun degrees() =
            this.radians / Maths.DEG_TO_RAD

    fun libGDXForm(): Float {
        var converted = radians
        while(converted > Maths.PI) { converted -= Maths.TAU }
        while(converted <= -Maths.PI) { converted += Maths.TAU }
        return converted //Might need to be -converted
    }

    fun sin() =
            kotlin.math.sin(this.radians)

    fun cos() =
            kotlin.math.cos(this.radians)

    fun tan() =
            kotlin.math.tan(this.radians)

    fun sec() =
            1f / cos()

    fun csc() =
            1f / sin()

    fun cot() =
            1f / tan()

    fun shortestDirectionTo(target: Angle) = when {
        this - target < target - this   -> RotationDirection.CLOCKWISE
        else                            -> RotationDirection.ANTICLOCKWISE
    }

    /**
     * Finds the smallest angle between the two angles. This should always be ≤ 180°.
     * @param other the angle to find the smallest angle to
     * @return the smallest angle between the two angles
     */
    infix fun diff(other: Angle) =
            minOf(this - other, other - this)

    operator fun plus(other: Angle) =
            Angle(this.radians + other.radians)

    operator fun minus(other: Angle) =
            Angle(this.radians - other.radians)

    operator fun times(scalar: Float) =
            Angle(this.radians * scalar)

    operator fun Float.times(angle: Angle) =
            Angle(angle.radians * this)

    operator fun div(denominator: Float) =
            Angle(this.radians / denominator)

    override operator fun compareTo(other: Angle) =
            this.radians.compareTo(other.radians)

    override fun approx(other: Angle, error: Float) =
            diff(other).radians < error

    override fun toString() =
            "${this.degrees()}°"

}
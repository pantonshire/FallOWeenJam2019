package com.game.maths

import kotlin.math.*

object Maths {

    const val PI = 3.1415926536f
    const val TAU = 6.2831853072f
    const val DEG_TO_RAD = 0.01745329252f
    const val DEFAULT_FLOAT_APPROX = 0.000244140625f // 2^(-12)

    fun roundOutwards(x: Float) = when {
        x > 0f  -> ceil(x)
        else    -> floor(x)
    }.toInt()

    fun clamp(x: Float, min: Float, max: Float) = when {
        x < min -> min
        x > max -> max
        else    -> x
    }

    fun mod(x: Float, modulo: Float) =
            (x % modulo + modulo) % modulo

    fun modTau(theta: Float) =
            mod(theta, TAU)

    fun approx(x: Float, y: Float, error: Float = DEFAULT_FLOAT_APPROX) =
            abs(x - y) < error

    fun approxZero(x: Float, error: Float = DEFAULT_FLOAT_APPROX) =
            abs(x) < error


    fun contSqrtCos(angle: Angle): Float {
        val cosine = angle.cos()
        return sqrt(abs(cosine)) * sign(cosine)
    }

    /** Varies sinusoidally from 0 to 1, starting at 0 when radians is 0. */
    fun inhale(angle: Angle) =
            clamp((1f - angle.cos()) / 2f, 0f, 1f)

    /** Varies sinusoidally from 0 to 1, starting at 1 when radians is 0. */
    fun exhale(angle: Angle) =
            clamp((1f + angle.cos()) / 2f, 0f, 1f)

}
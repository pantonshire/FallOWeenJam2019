package com.game.maths

interface Approximatable<T> {

    fun approx(other: T, error: Float): Boolean

    infix fun approx(other: T) =
            approx(other, Maths.DEFAULT_FLOAT_APPROX)

}
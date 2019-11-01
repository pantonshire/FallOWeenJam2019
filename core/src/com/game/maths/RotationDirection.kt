package com.game.maths

enum class RotationDirection(val direction: Float) {

    ANTICLOCKWISE(1f),
    CLOCKWISE(-1f);

    fun apply(angle: Angle) = angle * direction

}
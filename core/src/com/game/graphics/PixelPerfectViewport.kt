package com.game.graphics

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.game.maths.Vec
import kotlin.math.floor
import kotlin.math.roundToInt

class PixelPerfectViewport(worldSize: Vec, camera: Camera): ScalingViewport(Scaling.fill, worldSize.x, worldSize.y, camera) {

    override fun update(screenWidth: Int, screenHeight: Int, centerCamera: Boolean) {
        val scaled = scaling.apply(worldWidth, worldHeight, screenWidth.toFloat(), screenHeight.toFloat())
        val viewportWidth = (worldWidth * floor(scaled.x / worldWidth)).toInt()
        val viewportHeight = (worldHeight * floor(scaled.y / worldHeight)).toInt()
        setScreenBounds((screenWidth - viewportWidth) / 2, (screenHeight - viewportHeight) / 2, viewportWidth, viewportHeight)
        apply(centerCamera)
    }

}
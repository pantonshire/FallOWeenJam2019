package com.game.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.game.gamestate.MenuScreen
import com.game.graphics.Canvas
import com.game.maths.Vec
import com.game.settings.Settings
import com.game.time.Stopwatch

open class Flabel(
        menu: MenuScreen,
        pos: Vec,
        font: BitmapFont,
        text: String,
        scale: Float,
        colour: Color,
        centreX: Boolean,
        centreY: Boolean,
        private val timePeriodTicks: Int,
        private val amplitude: Float,
        onSelect: () -> Unit = {}
): Label(menu, pos, font, text, scale, colour, centreX, centreY, onSelect) {

    private val stopwatch = Stopwatch()

    override fun draw(canvas: Canvas, origin: Vec) {
        stopwatch.tick()

        val offset = if (Settings.getBool("ui-movement")) {
            Vec(0f, amplitude * stopwatch.angle(timePeriodTicks).sin())
        } else {
            Vec.NULL
        }

        super.draw(canvas, origin + offset)
    }

}
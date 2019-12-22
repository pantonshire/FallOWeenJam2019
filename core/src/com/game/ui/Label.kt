package com.game.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.game.gamestate.MenuScreen
import com.game.graphics.Canvas
import com.game.maths.Vec

open class Label(
        menu: MenuScreen,
        pos: Vec,
        private val font: BitmapFont,
        private val text: String,
        private val scale: Float,
        private val colour: Color,
        private val centreX: Boolean,
        private val centreY: Boolean,
        onSelect: () -> Unit = {}
): MenuElement(menu, pos, onSelect) {

    val textSize: Vec

    init {
        font.data.setScale(scale, scale)
        val layout = GlyphLayout(font, text)
        textSize = Vec(layout.width, layout.height)
    }

    override fun draw(canvas: Canvas, origin: Vec) {
        canvas.drawText(text, origin + pos, font, colour, centreX, centreY, scale)
    }

    override fun selectionBoxPosition() = Vec(
            if (centreX) { pos.x } else { pos.x + (textSize.x / 2f) },
            if (centreY) { pos.y } else { pos.y + (textSize.y / 2f) }
    )

    override fun selectionBoxSize() = Vec(
            textSize.x + 64f,
            textSize.y + 16f
    )

}
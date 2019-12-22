package com.game.ui

import com.game.gamestate.MenuScreen
import com.game.graphics.Canvas
import com.game.maths.Vec

abstract class MenuElement(val menu: MenuScreen, val pos: Vec, private val onSelect: () -> Unit = {}) {

    abstract fun draw(canvas: Canvas, origin: Vec)

    abstract fun selectionBoxPosition(): Vec
    abstract fun selectionBoxSize(): Vec

    fun select() {
        onSelect()
    }

}
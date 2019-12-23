package com.game.gamestate

import com.game.graphics.Canvas
import com.game.ui.MenuElement

abstract class MenuScreen: GameState() {

    private val menuElements = ArrayList<MenuElement>()

    protected fun addElement(element: MenuElement) {
        menuElements += element
    }

    override fun draw(canvas: Canvas) {
        menuElements.forEach { it.draw(canvas, canvas.centre) }
    }

}
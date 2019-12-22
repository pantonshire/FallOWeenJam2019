package com.game.gamestate

import com.game.Main
import com.game.graphics.Canvas
import com.game.maths.Maths
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper
import com.game.time.FixedTimer
import com.game.ui.MenuElement

abstract class MenuScreen(private val previous: MenuScreen?): GameState() {

    private val menuElements = ArrayList<MenuElement>()

    protected fun addElement(element: MenuElement) {
        menuElements += element
    }

    fun pop() {
        Main.gsm.queueMenu(previous)
    }

    override fun draw(canvas: Canvas) {
        menuElements.forEach { it.draw(canvas, canvas.centre) }
    }

}
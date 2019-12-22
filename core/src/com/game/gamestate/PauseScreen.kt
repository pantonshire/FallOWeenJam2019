package com.game.gamestate

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.game.graphics.Canvas
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper
import com.game.ui.Flabel
import com.game.ui.Label

class PauseScreen(previous: MenuScreen?): MenuScreen(previous) {

    init {
        val font = AssetManagerWrapper.INSTANCE.getFont("editundo.ttf")
        addElement(Label(this, Vec(0f, 120f), font, "PAUSED", 3f, Color.WHITE, true, true))
    }

    override fun update(delta: Float) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            pop()
        }
    }

    override fun onEnter() {

    }

    override fun onExit() {

    }

}
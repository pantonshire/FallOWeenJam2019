package com.game.gamestate

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.game.Main
import com.game.gameplay.Level
import com.game.gameplay.LevelFactory
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper
import com.game.ui.Label
import com.game.ui.TextOption

class ExitLevelScreen: OptionsMenuScreen() {

    init {
        val font = AssetManagerWrapper.INSTANCE.getFont("editundo.ttf")

        addElement(Label(this, Vec(0f, 120f), font, "ARE YOU SURE?", 3f, Color.WHITE, true, true))
        addElement(Label(this, Vec(0f, 80f), font, "YOUR PROGRESS WILL BE LOST.", 2f, Color.WHITE, true, true))

        addOption(TextOption(this, Vec(0f, 0f), font, "YES", 2f, true, true) {
            Main.gsm.queueCollapseTo(LevelSelect())
        })

        addOption(TextOption(this, Vec(0f, -40f), font, "NO", 2f, true, true) {
            Main.gsm.queuePop()
        })
    }

    override fun update(delta: Float) {
        super.update(delta)

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            Main.gsm.queuePop()
        }
    }

    override fun onEnter() {

    }

    override fun onExit() {

    }

}
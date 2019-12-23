package com.game.gamestate

import com.badlogic.gdx.graphics.Color
import com.game.Main
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper
import com.game.ui.Flabel
import com.game.ui.Label
import com.game.ui.TextOption

class MainMenu: OptionsMenuScreen() {

    init {
        val font = AssetManagerWrapper.INSTANCE.getFont("editundo.ttf")

        addElement(Flabel(this, Vec(0f, 80f), font, "TINY ESCAPES", 4f, Color.WHITE, true, true, 120, 4f))
        addElement(Label(this, Vec(0f, 20f), font, "BY TOM PANTON", 2f, Color.WHITE, true, true))

        addOption(TextOption(this, Vec(0f, -60f), font, "PLAY", 2f, true, true) {
            Main.gsm.queueCollapseTo(LevelSelect())
        })

        addOption(TextOption(this, Vec(0f, -100f), font, "QUIT", 2f, true, true) {
           Main.exitGame()
        })
    }

    override fun onEnter() {

    }

    override fun onExit() {

    }

}
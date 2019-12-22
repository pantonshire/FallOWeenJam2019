package com.game.gamestate

import com.badlogic.gdx.graphics.Color
import com.game.Main
import com.game.gameplay.Level
import com.game.gameplay.LevelFactory
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper
import com.game.ui.Flabel
import com.game.ui.TextOption

class LevelSelect: OptionsMenuScreen(null) {

    init {
        val font = AssetManagerWrapper.INSTANCE.getFont("editundo.ttf")

        addElement(Flabel(this, Vec(0f, 100f), font, "SELECT A LEVEL", 3f, Color.WHITE, true, true, 120, 4f))

        addOption(TextOption(this, Vec(0f, 40f), font, "TUTORIAL", 2f, true, true) {
            Main.gsm.queueState(LevelFactory.assembleLevel(Level.TUTORIAL,
                    scored = false,
                    baseCase = LevelSelect(),
                    shuffle = false
            ))
        })

        addOption(TextOption(this, Vec(0f, 0f), font, "LEVEL 1", 2f, true, true) {
            Main.gsm.queueState(LevelFactory.assembleLevel(Level.JAM1))
        })

        addOption(TextOption(this, Vec(0f, -40f), font, "LEVEL 2", 2f, true, true) {
            Main.gsm.queueState(LevelFactory.assembleLevel(Level.JAM2))
        })

        addOption(TextOption(this, Vec(0f, -80f), font, "LEVEL 3", 2f, true, true) {
            Main.gsm.queueState(LevelFactory.assembleLevel(Level.JAM3))
        })

        addOption(TextOption(this, Vec(0f, -120f), font, "BACK", 2f, true, true) {
            Main.gsm.queueState(MainMenu())
        })
    }

    override fun onEnter() {

    }

    override fun onExit() {

    }

}
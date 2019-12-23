package com.game.gamestate

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.game.Main
import com.game.audio.AudioManager
import com.game.graphics.Canvas
import com.game.resources.AssetManagerWrapper
import com.game.settings.Settings


class LaunchState: GameState() {

    override fun onEnter() {
        AssetManagerWrapper.INSTANCE.waitLoadAssets()
        AudioManager.setAudioEnabled(true)
        Gdx.graphics.setWindowedMode(1280, 720)
        Settings.initialise()
    }

    override fun onExit() {

    }

    override fun update(delta: Float) {
        Main.gsm.queueCollapseTo(MainMenu())
    }

    override fun draw(canvas: Canvas) {

    }

}
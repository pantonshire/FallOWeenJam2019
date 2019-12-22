package com.game.gamestate

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.lwjgl.LwjglGraphics
import com.badlogic.gdx.graphics.Pixmap
import com.game.Main
import com.game.audio.AudioManager
import com.game.graphics.Canvas
import com.game.resources.AssetManagerWrapper
import org.lwjgl.input.Mouse


class LaunchState: GameState() {

    override fun onEnter() {
        AssetManagerWrapper.INSTANCE.waitLoadAssets()

        AudioManager.setAudioEnabled(true)

        Gdx.graphics.setWindowedMode(1280, 720)
        Gdx.graphics.setUndecorated(true)

        val cursorPixmap = Pixmap(Gdx.files.internal("transparent.png"))
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(cursorPixmap, 0, 0))
        cursorPixmap.dispose()

//        Gdx.input.isCursorCatched = true

        Main.gsm.queueState(MainMenu())
    }

    override fun onExit() {

    }

    override fun update(delta: Float) {

    }

    override fun draw(canvas: Canvas) {

    }

}
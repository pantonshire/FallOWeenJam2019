package com.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.game.gamestate.GameStateManager
import com.game.gamestate.TestState
import com.game.resources.AssetManagerWrapper

class Main: ApplicationAdapter() {

    val gsm = GameStateManager()

    override fun create() {
        AssetManagerWrapper.INSTANCE.initialise()
        AssetManagerWrapper.INSTANCE.loadFont("editundo.ttf", 16)
        gsm.queueState(TestState())
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        gsm.update(Gdx.graphics.deltaTime)
        AssetManagerWrapper.INSTANCE.waitLoadAssets() //replaced update()
        gsm.render()
    }

    override fun dispose() {
        AssetManagerWrapper.INSTANCE.unload("editundo.ttf")
        gsm.onExit()
    }

}
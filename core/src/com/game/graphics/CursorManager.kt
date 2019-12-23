package com.game.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Cursor
import com.badlogic.gdx.graphics.Pixmap

object CursorManager {

    private val hiddenCursor = Gdx.graphics.newCursor(Pixmap(Gdx.files.internal("transparent.png")), 0, 0)

    fun hideCursor() {
        Gdx.graphics.setCursor(hiddenCursor)
    }

    fun revealCursor() {
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow)
    }

}
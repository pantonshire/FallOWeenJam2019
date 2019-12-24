/*
 * Copyright (C) 2019 Thomas Panton
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

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
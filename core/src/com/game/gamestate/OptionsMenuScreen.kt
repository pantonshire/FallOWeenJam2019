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

package com.game.gamestate

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.game.audio.AudioManager
import com.game.audio.SoundCategory
import com.game.graphics.Canvas
import com.game.maths.Maths
import com.game.maths.Vec
import com.game.resources.AssetManagerWrapper
import com.game.settings.Settings
import com.game.time.FixedTimer
import com.game.ui.MenuElement

abstract class OptionsMenuScreen(
        private val largestOptionX: Boolean = true,
        private val largestOptionY: Boolean = false
): MenuScreen() {

    private val selectPulseTimer = FixedTimer(15, 0)
    private val options = ArrayList<MenuElement>()
    private var currentOption = 0

    fun isSelected(element: MenuElement) =
            options[currentOption] == element

    protected fun addOption(element: MenuElement) {
        options += element
        addElement(element)
    }

    protected open fun handleOptionInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectPulseTimer.reset()
            currentOption--
            if (currentOption < 0) {
                currentOption = options.size - 1
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectPulseTimer.reset()
            currentOption++
            if (currentOption >= options.size) {
                currentOption = 0
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            options[currentOption].select()
            AudioManager.playSound(AssetManagerWrapper.INSTANCE.getSound("select.wav"), SoundCategory.UI)
        }
    }

    protected fun drawSelectBox(canvas: Canvas, position: Vec, size: Vec) {
        val boxScale = if (Settings.getBool("ui-movement")) {
            1f + 0.125f * Maths.inhale(selectPulseTimer.angle())
        } else {
            1f
        }

        canvas.drawTextureCentred(
                AssetManagerWrapper.INSTANCE.getTexture("whiteBox.png"),
                canvas.centre + position,
                width = size.x * boxScale, height = size.y * boxScale
        )
    }

    override fun update(delta: Float) {
        selectPulseTimer.tick()
        handleOptionInput()
    }

    override fun draw(canvas: Canvas) {
        val boxSize = Vec(
                if (largestOptionX) {
                    options.map { it.selectionBoxSize().x }.maxBy { it } ?: options[currentOption].selectionBoxSize().x
                } else {
                    options[currentOption].selectionBoxSize().x
                },

                if (largestOptionY) {
                    options.map { it.selectionBoxSize().y }.maxBy { it } ?: options[currentOption].selectionBoxSize().y
                } else {
                    options[currentOption].selectionBoxSize().y
                }
        )

        drawSelectBox(canvas, options[currentOption].selectionBoxPosition(), boxSize)
        super.draw(canvas)
    }

}
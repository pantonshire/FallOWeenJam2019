package com.game.gamestate

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.game.Main
import com.game.audio.AudioManager
import com.game.audio.SoundCategory
import com.game.gameplay.Level
import com.game.gameplay.LevelFactory
import com.game.graphics.Canvas
import com.game.maths.Angle
import com.game.maths.Maths
import com.game.maths.Vec
import com.game.random.Dice
import com.game.resources.AssetManagerWrapper
import com.game.time.FixedTimer
import com.game.ui.MenuElement

abstract class OptionsMenuScreen(
        previous: MenuScreen?,
        private val largestOptionX: Boolean = true,
        private val largestOptionY: Boolean = false
): MenuScreen(previous) {

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
        val boxScale = 1f + 0.125f * Maths.inhale(selectPulseTimer.angle())
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
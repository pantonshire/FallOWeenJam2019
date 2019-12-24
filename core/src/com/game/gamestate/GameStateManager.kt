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

import java.util.*

class GameStateManager(initialState: GameState? = null) {

    private var activeStates = Stack<GameState>()
    private var pushStates = LinkedList<GameState>()
    private var replaceState: GameState? = null
    private var collapseState: GameState? = null
    private var popQueued = false

    init {
        if (initialState != null) {
            pushState(initialState)
        }
    }

    private fun pushState(state: GameState) {
        activeStates.push(state)
        state.onEnter()
    }

    private fun popState() {
        if (activeStates.isNotEmpty()) {
            activeStates.pop().onExit()
        }
    }

    private fun clearStates() {
        while (activeStates.isNotEmpty()) {
            activeStates.pop().onExit()
        }
    }

    private fun topState() = if (activeStates.isEmpty()) {
        null
    } else {
        activeStates.peek()
    }

    fun queueCollapseTo(state: GameState) {
        collapseState = state
    }

    fun queueReplaceTop(state: GameState) {
        replaceState = state
    }

    fun queuePush(state: GameState) {
        pushStates.add(state)
    }

    fun queuePop() {
        popQueued = true
    }

    fun update(delta: Float) {
        if (replaceState != null) {
            popState()
            pushState(replaceState!!)
            replaceState = null
        }

        if (popQueued) {
            popState()
            popQueued = false
        }

        if (pushStates.isNotEmpty()) {
            pushStates.forEach { pushState(it) }
            pushStates.clear()
        }

        if (collapseState != null) {
            clearStates()
            pushState(collapseState!!)
            collapseState = null
        }

        topState()?.update(delta)
    }

    fun render() {
        topState()?.render()
    }

    fun resize(width: Int, height: Int) {
        topState()?.resize(width, height)
    }

    fun onExit() {
        clearStates()
    }

}
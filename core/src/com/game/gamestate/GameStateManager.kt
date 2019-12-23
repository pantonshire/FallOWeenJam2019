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
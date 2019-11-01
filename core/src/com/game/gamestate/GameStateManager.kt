package com.game.gamestate

class GameStateManager(initialState: GameState? = null) {

    private var currentState: GameState? = initialState
    private var nextState: GameState? = null

    fun queueState(state: GameState) {
        this.nextState = state
    }

    fun isActive(state: GameState) =
            this.currentState == state

    fun update(delta: Float) {
        if (this.nextState != null) {
            this.currentState?.onExit()
            this.currentState = nextState!!
            this.nextState = null
        }

        if (this.currentState != null) {
            this.currentState!!.update(delta)
        }
    }

    fun render() {
        if (this.currentState != null) {
            this.currentState!!.render()
        }
    }

    fun onExit() {
        currentState?.onExit()
    }

}
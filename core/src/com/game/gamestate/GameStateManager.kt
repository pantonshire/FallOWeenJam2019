package com.game.gamestate

class GameStateManager(initialState: GameState? = null) {

    private var currentState: GameState? = initialState
    private var nextState: GameState? = null
    private var currentMenu: MenuScreen? = null
    private var nextMenu: MenuScreen? = null
    private var menuQueued: Boolean = false

    fun queueState(state: GameState) {
        this.nextState = state
    }

    fun queueMenu(menu: MenuScreen?) {
        this.nextMenu = menu
        this.menuQueued = true
    }

    fun isActive(state: GameState) =
            this.currentState == state

    fun update(delta: Float) {
        if (this.menuQueued) {
            this.currentMenu?.onExit()
            this.currentMenu = this.nextMenu
            this.currentMenu?.onEnter()
            this.menuQueued = false
        }

        if (this.nextState != null) {
            this.currentState?.onExit()
            this.currentState = this.nextState
            this.nextState = null
            this.currentState!!.onEnter()
        }

        if (this.currentMenu != null) {
            this.currentMenu!!.update(delta)
        } else if (this.currentState != null) {
            this.currentState!!.update(delta)
        }
    }

    fun render() {
        if (this.currentMenu != null) {
            this.currentMenu!!.render()
        } else if (this.currentState != null) {
            this.currentState!!.render()
        }
    }

    fun resize(width: Int, height: Int) {
        this.currentState?.resize(width, height)
    }

    fun onExit() {
        currentState?.onExit()
    }

}
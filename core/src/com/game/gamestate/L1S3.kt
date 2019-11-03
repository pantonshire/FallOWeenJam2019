package com.game.gamestate

import com.game.entity.*
import com.game.maths.Vec

class L1S3(nextState: GameState, stageNo: Int): Stage(
        nextState,
        stageNo,
        "level1",
        "tileset1",
        720,
        "YOU PLAY",
        "AS THE DOOR."
) {

    override val player = PlayableDoor(this, arrayOf(), Vec(40f, 205f))
    override val door = StaticPlayer(this, Vec(40f, 33f))
    override val bomb = Bomb(this, Vec(80f, 34f))

    init {
        for (i in 0..3) {
            spawn(Spike(this, Vec(396f + (24f * i), 31f)))
        }

        spawn(Spike(this, Vec(300f, 199f)))
        spawn(Spike(this, Vec(228f, 161f), true))
        spawn(Spike(this, Vec(252f, 161f), true))
        spawn(Spike(this, Vec(372f, 329f), true))
        spawn(Spike(this, Vec(252f, 329f), true))
        spawn(Spike(this, Vec(228f, 329f), true))
        spawn(SpikeBlock(this, Vec(168f, 264f)))

        spawnEssentialEntities()
    }

}
package com.game.gamestate

import com.game.entity.*
import com.game.level.Modifiers
import com.game.maths.Vec

class L3S3(nextState: GameState, stageNo: Int): Stage(
        nextState,
        stageNo,
        "level3",
        "tileset1",
        600,
        "YOU ONLY HAVE",
        "TEN SECONDS."
) {

    override val player = Player(this, arrayOf(), Vec(64f, 250f))
    override val door = Door(this, Vec(40f, 61f))
    override val bomb = Bomb(this, Vec(90f, 250f))

    init {
        spawn(Spring(this, Vec(444f, 127f)))

        spawn(Spike(this, Vec(228f, 329f), inverted = true))
        spawn(Spike(this, Vec(228f, 247f)))
        spawn(Spike(this, Vec(348f, 329f), inverted = true))
        spawn(Spike(this, Vec(348f, 247f)))
        spawn(Spike(this, Vec(126f, 209f), inverted = true))
        spawn(Spike(this, Vec(102f, 209f), inverted = true))
        spawn(Spike(this, Vec(78f, 209f), inverted = true))
        spawn(SpikeBlock(this, Vec(252f, 312f)))
        spawn(SpikeBlock(this, Vec(372f, 312f)))
        spawn(SpikeBlock(this, Vec(540f, 312f)))
        spawn(SpikeBlock(this, Vec(300f, 36f), maxWait = 10))
        spawn(SpikeBlock(this, Vec(228f, 36f), maxWait = 10))
        spawn(SpikeBlock(this, Vec(156f, 36f), maxWait = 10))

        for (i in 0..5) {
            if (i != 2) {
                spawn(Spike(this, Vec(396f + (24f * i), 103f)))
            }
        }

        spawnEssentialEntities()
    }

}
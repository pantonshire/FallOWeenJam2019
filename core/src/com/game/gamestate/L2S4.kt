package com.game.gamestate

import com.game.entity.*
import com.game.level.Modifiers
import com.game.maths.Vec

class L2S4(nextState: GameState, stageNo: Int): Stage(
        nextState,
        stageNo,
        "level2",
        "tileset1",
        720,
        "JUMPING ON SPRINGS",
        "REVERSES GRAVITY."
) {

    override val player = Player(this, arrayOf(), Vec(64f, 34f))
    override val door = Door(this, Vec(432f, 277f))
    override val bomb = Bomb(this, Vec(80f, 274f))

    init {
        spawn(Spring(this, Vec(228f, 31f), flip = true))
        spawn(Spring(this, Vec(348f, 31f), flip = true))
        spawn(Spring(this, Vec(372f, 31f), flip = true))
        spawn(Spring(this, Vec(588f, 79f), flip = true))
        spawn(SpringInverted(this, Vec(372f, 329f), flip = true))

        spawn(SpikeBlock(this, Vec(276f, 264f), maxWait = 60))
        spawn(SpikeBlock(this, Vec(180f, 288f), maxWait = 120))
        spawn(Spike(this, Vec(396f, 151f)))
        spawn(Spike(this, Vec(540f, 329f), inverted = true))

        for (i in 0..4) {
            spawn(Spike(this, Vec(468f + (24f * i), 31f)))
        }

        spawnEssentialEntities()
    }

}
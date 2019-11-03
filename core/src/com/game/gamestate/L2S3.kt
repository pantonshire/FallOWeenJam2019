package com.game.gamestate

import com.game.entity.*
import com.game.level.Modifiers
import com.game.maths.Vec
import com.game.tilemap.TileMap

class L2S3(nextState: GameState, stageNo: Int): Stage(
        nextState,
        stageNo,
        "level2",
        "tileset1",
        720,
        "THE BOMB",
        "IS YOUR EXIT."
) {

    override val player = Player(this, arrayOf(), Vec(64f, 34f))

    val multiPurposeBomb = Bomb(this, Vec(80f, 274f))

    override val door = multiPurposeBomb
    override val bomb = multiPurposeBomb

    init {
        spawn(Spring(this, Vec(228f, 31f)))
        spawn(Spring(this, Vec(348f, 31f)))
        spawn(Spring(this, Vec(372f, 31f)))
        spawn(Spring(this, Vec(588f, 79f)))
        spawn(SpringInverted(this, Vec(372f, 329f)))

        spawn(SpikeBlock(this, Vec(276f, 264f), maxWait = 60))
        spawn(SpikeBlock(this, Vec(180f, 288f), maxWait = 120))
        spawn(Spike(this, Vec(396f, 151f)))
        spawn(Spike(this, Vec(228f, 329f), inverted = true))
        spawn(Spike(this, Vec(540f, 329f), inverted = true))

        for (i in 0..4) {
            spawn(Spike(this, Vec(468f + (24f * i), 31f)))
        }

        spawn(Door(this, Vec(432f, 277f)))

        spawnEssentialEntities()
    }

    override fun spawnEssentialEntities() {
        spawn(multiPurposeBomb)
        spawn(player)
    }

}
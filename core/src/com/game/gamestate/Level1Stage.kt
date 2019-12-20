package com.game.gamestate

import com.game.entity.*
import com.game.level.Level
import com.game.level.Modifiers
import com.game.maths.Vec

class Level1Stage(level: Level, nextState: GameState, stageNo: Int, skipIntro: Boolean = false): Stage(
        level,
        nextState,
        stageNo,
        "level1",
        "tileset1",
        720,
        when (stageNo) {
            1 -> arrayOf("WHEN YOU JUMP", "GRAVITY REVERSES.")
            2 -> arrayOf("YOU PLAY", "AS THE DOOR.")
            3 -> arrayOf("THE FLOOR", "IS BOUNCY.")
            4 -> arrayOf("YOU INTERMITTENTLY", "BECOME INVISIBLE.")
            5 -> arrayOf("MORE CRUSHY BLOCKS", "AND THEY\'RE ANGRY.")
            6 -> arrayOf("YOU ARE VERY FAST", "AND THE CONTROLS ARE INVERTED.")
            else -> arrayOf("THE BOMB WILL DETONATE", "IN TWELVE SECONDS.")
        },
        skipIntro = skipIntro
) {

    override val player = if (stageNo == 2) {
        PlayableDoor(this, arrayOf(), Vec(40f, 205f))
    } else {
        Player(
                this,
                when (stageNo) {
                    1 -> arrayOf(Modifiers.JUMP_INV_GRAVITY)
                    3 -> arrayOf(Modifiers.POGO)
                    4 -> arrayOf(Modifiers.FADE)
                    6 -> arrayOf(Modifiers.HYPERSPEED, Modifiers.INV_CONTROLS)
                    else -> arrayOf()
                },
                Vec(64f, 34f)
        )
    }

    override val door = if (stageNo == 2) {
        StaticPlayer(this, Vec(40f, 33f))
    } else {
        Door(this, Vec(40f, 205f))
    }

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

        if (stageNo == 5) {
            spawn(SpikeBlock(this, Vec(168f, 264f), maxWait = 30))
            spawn(SpikeBlock(this, Vec(336f, 120f), maxWait = 30))
            spawn(SpikeBlock(this, Vec(504f, 240f), maxWait = 60))
        } else {
            spawn(SpikeBlock(this, Vec(168f, 264f)))
        }

        spawnEssentialEntities()
    }

}
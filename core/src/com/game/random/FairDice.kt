package com.game.random

import java.util.Random

class FairDice(private val random: Random = Random()): Dice() {

    constructor(seed: Long): this(Random(seed))

    override fun roll(range: IntRange) =
            range.first + random.nextInt(range.last - range.first + 1)

    override fun rollF(range: ClosedFloatingPointRange<Float>) =
            range.start + ((range.endInclusive - range.start) * random.nextFloat())

    override fun rollD(range: ClosedFloatingPointRange<Double>) =
            range.start + ((range.endInclusive - range.start) * random.nextDouble())

    override fun flip() =
            random.nextBoolean()

    override fun chance(p: Double) =
            random.nextDouble() < p

}
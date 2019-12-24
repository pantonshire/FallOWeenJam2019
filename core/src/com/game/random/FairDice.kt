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

package com.game.random

import java.util.*

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

    override fun <T> choose(array: Array<T>): T =
            array[roll(array.indices)]

}
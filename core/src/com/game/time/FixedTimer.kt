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

package com.game.time

import com.game.maths.Angle

class FixedTimer(val maxTicks: Int, initialTicks: Int = maxTicks) {

    var remainingTicks: Int = initialTicks
        private set

    fun elapsedTicks(): Int =
            maxTicks - remainingTicks

    fun angle() =
            Angle.fromPercent(elapsedTicks() / maxTicks.toFloat())

    fun tick(): Boolean {
        return if (remainingTicks > 0) {
            remainingTicks--
            remainingTicks == 0
        } else {
            false
        }
    }

    fun doBeforeTick(onTick: (remaining: Int, elapsed: Int) -> Unit): Boolean {
        onTick(remainingTicks, elapsedTicks())
        return tick()
    }

    fun doAfterTick(onTick: (remaining: Int, elapsed: Int) -> Unit): Boolean {
        val isDone = tick()
        onTick(remainingTicks, elapsedTicks())
        return isDone
    }

    fun reset() {
        remainingTicks = maxTicks
    }

}
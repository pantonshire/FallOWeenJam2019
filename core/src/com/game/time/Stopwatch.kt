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

class Stopwatch {

    var ticks: Int = 0
        private set

    fun angle(cycleTicks: Int) =
            Angle.fromPercent(ticks / cycleTicks.toFloat())

    fun tick() {
        ticks++
    }

    fun reset() {
        ticks = 0
    }

}
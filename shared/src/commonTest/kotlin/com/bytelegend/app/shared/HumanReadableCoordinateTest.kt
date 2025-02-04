/*
 * Copyright 2021 ByteLegend Technologies and the original author or authors.
 * 
 * Licensed under the GNU Affero General Public License v3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      https://github.com/ByteLegend/ByteLegend/blob/master/LICENSE
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bytelegend.app.shared

import kotlin.test.Test
import kotlin.test.assertEquals

class HumanReadableCoordinateTest {
    @Test
    fun test() {
        testOne(0, 0, "(A, 0)")
        testOne(0, 1, "(A, 1)")
        testOne(1, 1, "(B, 1)")
        testOne(3, 1, "(D, 1)")
        testOne(25, 1, "(Z, 1)")
        testOne(26, 1, "(AA, 1)")
        testOne(26, 26, "(AA, 26)")
        testOne(52, 26, "(BA, 26)")
        testOne(676, 676, "(AAA, 676)")
    }

    private fun testOne(x: Int, y: Int, humanReadableValue: String) {
        assertEquals(humanReadableValue, HumanReadableCoordinate(x, y).toString())
    }
}

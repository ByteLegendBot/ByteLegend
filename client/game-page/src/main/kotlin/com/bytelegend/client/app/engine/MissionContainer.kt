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
@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.bytelegend.client.app.engine

import com.bytelegend.app.client.api.EventBus
import com.bytelegend.app.shared.entities.MissionModalData
import com.bytelegend.client.utils.JSObjectBackedMap
import com.bytelegend.client.utils.jsObjectBackedSetOf
import com.bytelegend.client.app.web.WebSocketClient
import com.bytelegend.client.app.web.getMissionModalData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.instance

const val MISSION_DATA_LOAD_FINISH = "mission.data.load.finish"

class MissionContainer(
    private val di: DI,
    private val gameScene: DefaultGameScene
) {
    private val eventBus: EventBus by di.instance()
    private val webSocketClient: WebSocketClient by di.instance()
    private val loadingMissions: MutableSet<String> = jsObjectBackedSetOf()
    private val missionData: MutableMap<String, MissionModalData> = JSObjectBackedMap()

    fun isMissionModalDataLoading(missionId: String): Boolean = loadingMissions.contains(missionId)

    fun getMissionModalDataById(missionId: String): MissionModalData {
        return missionData.getValue(missionId)
    }

    /**
     * Refresh the mission modal data. Usually, we cache the data in browser session
     * so next time we don't need to load it again. Use force=true to forcibly refresh the data.
     */
    fun refresh(missionId: String, force: Boolean = false) {
        if (!loadingMissions.contains(missionId)) {
            if (missionData.containsKey(missionId)) {
                if (force) {
                    load(missionId)
                }
            } else {
                load(missionId)
            }
        }
    }

    private fun load(missionId: String) {
        loadingMissions.add(missionId)
        GlobalScope.launch {
            missionData[missionId] = getMissionModalData(missionId)
            loadingMissions.remove(missionId)
            eventBus.emit(MISSION_DATA_LOAD_FINISH, missionId)
        }
    }
}

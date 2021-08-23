package com.bytelegend.app.client.api.dsl

import com.bytelegend.app.shared.GridCoordinate
import com.bytelegend.app.shared.PixelCoordinate
import com.bytelegend.app.shared.objects.GameObject

typealias UnitFunction = () -> Unit
typealias SuspendUnitFunction = suspend () -> Unit

val EMPTY_FUNCTION: UnitFunction = {}

class BouncingTitleBuilder {
    var pixelCoordinate: PixelCoordinate? = null
    var textId: String? = null
    var color: String = "black"
    var onClickFunction: UnitFunction? = null
}

class MapEntranceBuilder {
    /**
     * Mandatory. The destination map id. When player touches this entrance,
     * the corresponding map will be loaded.
     */
    var destMapId: String? = null

    /**
     * Optional. The id of the entrance to be created. If null,
     * a value will be generated by convention.
     */
    var id: String? = null

    /**
     * Optional. The entrance point id on current map, which can be used to
     * fetch a {@link GameMapPoint} object. If null, a default id
     * will be generated by convention.
     */
    var coordinatePointId: String? = null

    /**
     * Optional. The map entrance point id on the destination map, which
     * can be used to fetch a GameMapPoint from destination map.
     */
    var backEntrancePointId: String? = null

    /**
     * Show a road-sign-like bouncing title for this entrance
     */
    var bouncingTitle: Boolean = true
}

class NpcBuilder {
    var id: String? = null

    /**
     * The dynamic sprite id, see GameMapDynamicSprite
     */
    var sprite: String? = null
    var onInit: UnitFunction = {}
    var onTouch: (GameObject) -> Unit = {}
    var onClick: UnitFunction = {}
}

class DynamicSpriteBuilder {
    var id: String? = null

    /**
     * The dynamic sprite id, see GameMapDynamicSprite.
     * If null, it will be an "empty" point but clickable.
     */
    var sprite: String? = null
    var gridCoordinate: GridCoordinate? = null
    var onInit: UnitFunction = {}
    var onTouch: (GameObject) -> Unit = {}
    var onClick: UnitFunction = {}
}

class ObjectBuilder {
    var id: String? = null
    var coordinatePointId: String? = null
    var onInit: UnitFunction = {}
    var onTouch: (GameObject) -> Unit = {}
    var onClick: UnitFunction = {}
}

class SpriteBuilder {
    var id: String? = null
    var spriteId: String? = null
    var onInit: UnitFunction = {}
    var onTouch: (GameObject) -> Unit = {}
    var onClick: UnitFunction = {}
        set(value) {
            field = value
            clickable = true
        }
    var clickable: Boolean = false
    var glow: Boolean = false
}

class NoticeboardBuilder {
    var id: String? = null
    var spriteId: String? = null
}

interface ObjectsBuilder {
    fun mapEntrance(action: MapEntranceBuilder.() -> Unit)
    fun bouncingTitle(action: BouncingTitleBuilder.() -> Unit)

    /*
    This shouldn't be in API module, but we have to workaround the following issue:

    react-dom.development.js?3169:11340 Uncaught Error: Invalid hook call. Hooks can only be called inside of the body of a function component. This could happen for one of the following reasons:
    1. You might have mismatching versions of React and the renderer (such as React DOM)
    2. You might be breaking the Rules of Hooks
    3. You might have more than one copy of React in the same app
    See https://reactjs.org/link/invalid-hook-call for tips about how to debug and fix this problem.
    at resolveDispatcher (react.development.js?ec8f:1476)
    at useContext (react.development.js?ec8f:1484)
    at useBootstrapPrefix (ThemeProvider.js?ebe6:19)
    at ModalBody (createWithBsPrefix.js?32e6:27)
    at renderWithHooks (react-dom.development.js?3169:14985)
    at updateForwardRef (react-dom.development.js?3169:17044)
    at beginWork (react-dom.development.js?3169:19098)
    at HTMLUnknownElement.callCallback (react-dom.development.js?3169:3945)
    at Object.invokeGuardedCallbackDev (react-dom.development.js?3169:3994)
    at invokeGuardedCallback (react-dom.development.js?3169:4056)

    The cause seems to be that react is bundled into game-page and game-JavaIsland,
    i.e. "more than one copy of React in the same app"
     */
    fun noticeboard(action: NoticeboardBuilder.() -> Unit)
    fun npc(action: NpcBuilder.() -> Unit)
    fun dynamicSprite(action: DynamicSpriteBuilder.() -> Unit)
}

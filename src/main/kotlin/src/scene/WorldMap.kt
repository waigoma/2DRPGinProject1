package src.scene

import processing.core.PApplet
import processing.core.PConstants
import src.Main
import src.map.MapTemplate
import src.player.PlayerMoveAnimation
import src.state.StateType

class WorldMap(private val plet: PApplet) {
    private val mapManager = Main.mapManager
    private val ppm = Main.playerPositionManager
    private val stateType: StateType = Main.stateType

    private val pma = PlayerMoveAnimation(plet)
    
    private var mapTmp: MapTemplate

    private var first = true
    
    private var next = ""
    private var previous = ""
    var nextX = 0
    var nextY = 0
    var previousX = 0
    var previousY = 0

    init {
        mapTmp = mapManager.get("dungeon1.tmx")
    }

    fun keyPressed() {
        pma.keyPressed()
    }

    fun keyReleased() {
        pma.keyReleased()
    }

    fun display() {
        if (first) {
            plet.rectMode(PConstants.CORNER)
            val width: Int = mapTmp.mapTileWidth * mapTmp.tileWidth
            val height: Int = mapTmp.mapTileHeight * mapTmp.tileHeight
            next = mapTmp.nextMap
            previous = mapTmp.previousMap
            nextX = mapTmp.nextX
            nextY = mapTmp.nextY
            previousX = mapTmp.previousX
            previousY = mapTmp.previousY
            plet.surface.setSize(width - 10, height - 10)
            plet.background(0)
//            pmove.setup()
//            Combat.mapName = mapTmp.getMapName()
            first = false
        }
        mapTmp.display(plet)
        pma.draw()
        mapTmp.topDisplay(plet)
        mapTmp.event(plet)

//        System.out.println(mapTmp.getMapName());
        if (mapTmp.isNext()) {
//          Main.state = StateType.COMBAT_STATE;
            mapTmp = mapManager.get("$next.tmx")
            ppm.setPlayerX(nextX.toFloat())
            ppm.setPlayerY(nextY.toFloat())
            first = true
        }

        if (mapTmp.isBoss()) {
//            Collision.Playerx = 312
//            Collision.Playery = 56
//            LocalMap.count = 0
//            Combat.m_name = "モンスターC"
//             stateType.setState(StateType.COMBAT_STATE)
        }

        if (mapTmp.isBack()) {
            mapTmp = mapManager.get("$previous.tmx")
            if (mapTmp.name.contains("1village")) {
                stateType.setState(StateType.LOCAL_STATE)
                next = mapTmp.nextMap
//                LocalMap.mapTmp = mapTmp
                mapTmp = mapManager.get("$next.tmx")
            }

            ppm.setPlayerX(previousX.toFloat())
            ppm.setPlayerY(previousY.toFloat())
//            LocalMap.count = 0
        }
    }

    fun setMapTmp(mapName: String) {
//        mapTmp = MapTemplate.maps.get("$mapName.tmx")
    }
}
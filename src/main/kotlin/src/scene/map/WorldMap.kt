package src.scene.map

import processing.core.PApplet
import processing.core.PConstants
import src.Main
import src.map.MapTemplate
import src.character.player.PlayerMoveAnimation
import src.state.StateType

class WorldMap(private val plet: PApplet) {
    private val mapManager = Main.mapManager
    private val ppm = Main.playerPositionManager
    private val stateType: StateType = Main.stateType

    private val pma = PlayerMoveAnimation(plet)
    
    private var mapTmp: MapTemplate

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
        if (mapManager.getChange()) {
            mapTmp = mapManager.getCurrentMap()
            plet.rectMode(PConstants.CORNER)
            val width = mapTmp.mapTileWidth * mapTmp.tileWidth
            val height = mapTmp.mapTileHeight * mapTmp.tileHeight

            next = mapTmp.nextMap
            previous = mapTmp.previousMap
            nextX = mapTmp.nextX
            nextY = mapTmp.nextY
            previousX = mapTmp.previousX
            previousY = mapTmp.previousY

            plet.surface.setSize(width - 10, height - 10)
            plet.background(0)

            ppm.setCanMove(true)
            mapManager.notChange()
        }


        mapTmp.display(plet)
        pma.draw()
        mapTmp.topDisplay(plet)
        mapTmp.event(plet)

        if (mapTmp.isNext()) {
            if (mapManager.exists("$next.tmx")){
                mapManager.setCurrentMap(mapManager.get("$next.tmx"))

                ppm.setPlayerX(nextX.toFloat())
                ppm.setPlayerY(nextY.toFloat())

//                if (mapTmp.name.contains("dungeon1")) stateType.setState(StateType.WORLD_STATE)
            }else{
                println("WorldMap.kt isNext 「$next.tmx」は存在しません。")
            }

            plet.delay(100)
        }

//        if (mapTmp.isBoss()) {
////            Collision.playerX = 312
////            Collision.playerY = 56
////            LocalMap.count = 0
////            Combat.m_name = "モンスターC"
////             stateType.setState(StateType.COMBAT_STATE)
//        }

        if (mapTmp.isBack()) {
            if (mapManager.exists("$previous.tmx")){
                mapManager.setCurrentMap(mapManager.get("$previous.tmx"))

                if (mapManager.getCurrentMap().name.contains("1village")) stateType.setState(StateType.LOCAL_STATE)

                ppm.setPlayerX(previousX.toFloat())
                ppm.setPlayerY(previousY.toFloat())
            }else{
                println("WorldMap.kt isBack 「$previous.tmx」は存在しません。")
            }

            plet.delay(100)
        }
    }
}
package src.scene.map

import processing.core.PApplet
import processing.core.PConstants
import src.Main
import src.map.MapManager
import src.map.MapTemplate
import src.player.PlayerMoveAnimation
import src.player.PlayerPositionManager
import src.state.StateType

class LocalMap(private val plet: PApplet) {
    private val mapManager: MapManager = Main.mapManager
    private val stateType: StateType = Main.stateType
    private val ppm: PlayerPositionManager = Main.playerPositionManager
    private val pma: PlayerMoveAnimation = PlayerMoveAnimation(plet)

    private var mapTmp: MapTemplate

    private lateinit var next: String
    private lateinit var previous: String

    private var nextX = 0
    private var nextY = 0
    private var previousX = 0
    private var previousY = 0

    init {
        mapTmp = mapManager.getCurrentMap()
        ppm.setPlayerX(162f)
        ppm.setPlayerY(142f)
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
        mapTmp.event(plet)
        mapTmp.topDisplay(plet)

        if (mapTmp.isNext()) {
            if (mapManager.exists("$next.tmx")){
                mapManager.setCurrentMap(mapManager.get("$next.tmx"))

                ppm.setPlayerX(nextX.toFloat())
                ppm.setPlayerY(nextY.toFloat())

                if (mapManager.getCurrentMap().name.contains("dungeon1")) stateType.setState(StateType.WORLD_STATE)
            }else{
                println("LocalMap.kt isNext 「$next.tmx」は存在しません。")
            }

            plet.delay(100)
        }

        if (mapTmp.isBack()) {
            stateType.setState(StateType.LOCAL_STATE)
        }
    }
}
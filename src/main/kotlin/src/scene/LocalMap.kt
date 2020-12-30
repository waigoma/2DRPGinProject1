package src.scene

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

    private lateinit var mapTmp: MapTemplate

    private lateinit var next: String
    private lateinit var previous: String

    private var nextX = 0
    private var nextY:Int = 0
    private var previousX:Int = 0
    private var previousY:Int = 0

    var first = true

    init {
        if (mapManager.exists("1village.tmx")) mapTmp = mapManager.get("1village.tmx")
        ppm.setPlayerX(162f)
        ppm.setPlayerY(142f)
        pma.setPosition()
    }

    fun keyPressed() {
//        pmove.keyPressed()
    }

    fun keyReleased() {
//        pmove.keyReleased()
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
            first = false
        }
        mapTmp.display(plet)
        pma.draw()
//        mapTmp.event()
        mapTmp.topDisplay(plet)

        if (mapTmp.isNext()) {
            mapTmp = mapManager.get("$next.tmx")
//            Collision.Playerx = nextX
//            Collision.Playery = nextY
            first = true
            if (mapTmp.name.contains("dungeon1")) stateType.setState(StateType.WORLD_STATE)
            first = true
            plet.delay(100)
        }

        if (mapTmp.isBack()) {
//            Main.state = StateType.LOCAL_STATE;
            first = true
        }
    }
}
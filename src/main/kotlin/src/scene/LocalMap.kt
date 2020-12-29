package src.scene

import processing.core.PApplet
import processing.core.PConstants
import src.Main
import src.map.MapManager
import src.map.MapTemplate
import src.player.PlayerMoveAnimation
import src.state.StateType

class LocalMap(val plet: PApplet) {
    private val mapManager: MapManager = Main.mapManager
    private val stateType: StateType = Main.stateType

    private lateinit var mapTmp: MapTemplate
//    lateinit var pma: PlayerMoveAnimation
    lateinit var next: String
    lateinit var previous: String
    var nextX = 0
    var nextY:Int = 0
    var previousX:Int = 0
    var previousY:Int = 0

    var count = 0

    init {
//        pma = PlayerMove(plet)
        if (mapManager.exists("1village.tmx")) mapTmp = mapManager.get("1village.tmx")
//        Collision.Playerx = 162
//        Collision.Playery = 142
    }

    fun keyPressed() {
//        pmove.keyPressed()
    }

    fun keyReleased() {
//        pmove.keyReleased()
    }

    fun display() {
        if (count == 0) {
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
            //            System.out.println("local");
            count++
        }
        mapTmp.display(plet)
//        pmove.draw()
//        mapTmp.event()
        mapTmp.topDisplay(plet)
        if (mapTmp.isNext()) {
            mapTmp = mapManager.get("$next.tmx")
//            Collision.Playerx = nextX
//            Collision.Playery = nextY
            count = 0
            if (mapTmp.name.contains("dungeon1")) stateType.setState(StateType.WORLD_STATE)
            count = 0
            plet.delay(100)
        }
        if (mapTmp.isBack()) {
//            Main.state = StateType.LOCAL_STATE;
            count = 0
        }
    }
}
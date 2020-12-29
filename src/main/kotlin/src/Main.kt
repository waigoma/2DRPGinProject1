package src

import processing.core.PApplet
import src.map.MapManager
import src.map.TmxLoader
import src.scene.LocalMap
import src.state.StateType
import src.title.Title

class Main: PApplet() {
    companion object{
        lateinit var stateType: StateType
        lateinit var mapManager: MapManager
    }
    private lateinit var title: Title
    private lateinit var localMap: LocalMap
//    private lateinit var worldMap: WorldMap

    override fun settings() {
        size(1280, 720)
    }

    override fun setup() {
        surface.setResizable(true)
        noStroke()

        stateType = StateType()
        mapManager = MapManager()

        TmxLoader()

        title = Title(this)
        localMap = LocalMap(this)
//        worldMap = WorldMap(this)

        val font = createFont("MS Gothic", 50f)
        textFont(font)
    }

    override fun keyPressed() { //キー入力受付
        when(stateType.getState()){
            StateType.LOCAL_STATE -> localMap.keyPressed()
//            StateType.WORLD_STATE -> worldMap.keyPressed()

        }
    }

    override fun keyReleased() {
    }

    override fun draw() {
        when(stateType.getState()){
            StateType.TITLE_STATE -> {
                title.run()
            }
            StateType.LOCAL_STATE -> {
                localMap.display()
            }
            StateType.WORLD_STATE -> {

            }
        }
    }

    fun run() {
        main("src.Main")
    }

}

fun main() {
    Main().run()
}
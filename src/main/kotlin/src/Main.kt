package src

import processing.core.PApplet
import src.map.MapManager
import src.map.TmxLoader
import src.player.PlayerPositionManager
import src.player.PlayerStatManager
import src.scene.map.LocalMap
import src.scene.map.WorldMap
import src.state.StateType
import src.scene.title.Title
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.jar.JarFile
import org.apache.commons.io.FileUtils
import src.scene.title.option.OptionManager

class Main: PApplet() {
    companion object{
        val pdFile = System.getProperty("user.dir") + File.separator + "ProcessingProject" + File.separator + "player_data.properties"
        val opFile = System.getProperty("user.dir") + File.separator + "ProcessingProject" + File.separator + "option.properties"

        lateinit var stateType: StateType
        lateinit var mapManager: MapManager
        lateinit var playerPositionManager: PlayerPositionManager
        lateinit var playerStatManager: PlayerStatManager
        lateinit var optionManager: OptionManager
    }
    private lateinit var title: Title
    private lateinit var localMap: LocalMap
    private lateinit var worldMap: WorldMap

    override fun settings() {
        size(1280, 720)
    }

    override fun setup() {
        surface.setResizable(true)
        noStroke()

        stateType = StateType()
        mapManager = MapManager()
        playerPositionManager = PlayerPositionManager()
        playerStatManager = PlayerStatManager()
        optionManager = OptionManager()

        playerPositionManager.setPlayerSpeed(3)

        writeResources()

        playerStatManager.loadPlayerData()
        TmxLoader()

        //セーブとかするならこれ活用でいけそう
        //今はとりあえず村として設定
        mapManager.setCurrentMap(mapManager.get("1village.tmx"))

        title = Title(this)
        localMap = LocalMap(this)
        worldMap = WorldMap(this)

        val font = createFont("MS Gothic", 50f)
        textFont(font)
    }

    override fun keyPressed() { //キー入力受付
        when(stateType.getState()){
            StateType.LOCAL_STATE -> localMap.keyPressed()
            StateType.WORLD_STATE -> worldMap.keyPressed()
        }
    }

    override fun keyReleased() { //キー解放
        when(stateType.getState()){
            StateType.LOCAL_STATE -> localMap.keyReleased()
            StateType.WORLD_STATE -> worldMap.keyReleased()
        }
    }

    //Combat作って、playerのステータス作れば基礎は終わり。
    //後は、セーブとかアイテムシステムとか村人との会話とか作ってみたい。

    override fun draw() {//ステートマシン
        when(stateType.getState()){
            StateType.TITLE_STATE -> {
                title.run()
            }
            StateType.LOCAL_STATE -> {
                localMap.display()
            }
            StateType.WORLD_STATE -> {
                worldMap.display()
            }
        }
    }

    fun run() {
        main("src.Main")
    }

    private fun writeResources(){
        val dirPath = System.getProperty("user.dir") + File.separator + "ProcessingProject"
        val file = File(dirPath)

        try {
            if (!file.exists()) {
                file.mkdir()
                saveResource("data/stat/player", file)
                saveResource("data/option", file)
            }
        }catch (e: IOException){
            e.printStackTrace()
        }
    }

    private fun saveResource(copyResource: String, directory: File){
        val jarFile = File(Main::class.java.protectionDomain.codeSource.location.path)
        if (jarFile.isFile) {
            // JARで実行する場合
            val jar = JarFile(jarFile)
            val entries = jar.entries()
            while (entries.hasMoreElements()) {
                val entry = entries.nextElement()
                if (entry.name.startsWith("$copyResource/") && !entry.isDirectory) {
                    val dest = File(directory, entry.name.substring(copyResource.length + 1))
                    val parent = dest.parentFile
                    parent?.mkdirs()
                    val out = FileOutputStream(dest)
                    val `in` = jar.getInputStream(entry)
                    try {
                        val buffer = ByteArray(8 * 1024)
                        var s = 0
                        while (`in`.read(buffer).also { s = it } > 0) {
                            out.write(buffer, 0, s)
                        }
                    } finally {
                        `in`.close()
                        out.close()
                    }
                }
            }
            jar.close()
        } else {
            // IDEで実行する場合
            val resource = File(Main::class.java.classLoader.getResource(copyResource)!!.toURI())
            FileUtils.copyDirectory(resource, directory)
        }
    }

}

fun main() {
    Main().run()
}
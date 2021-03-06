package src

import processing.core.PApplet
import src.map.MapManager
import src.map.TmxLoader
import src.character.player.PlayerPositionManager
import src.character.player.PlayerStatManager
import src.scene.map.LocalMap
import src.scene.map.WorldMap
import src.state.StateType
import src.scene.title.Title
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.jar.JarFile
import org.apache.commons.io.FileUtils
import src.character.mob.MobManager
import src.scene.combat.Combat
import src.option.OptionManager

class Main: PApplet() {
    companion object{
        val pdFile = System.getProperty("user.dir") + File.separator + "ProcessingProject" + File.separator + "player" + File.separator + "player_data.properties"
        val mdFolder = System.getProperty("user.dir") + File.separator + "ProcessingProject" + File.separator + "mob"
        val opFile = System.getProperty("user.dir") + File.separator + "ProcessingProject" + File.separator + "option.properties"

        lateinit var stateType: StateType
        lateinit var mapManager: MapManager
        lateinit var playerPositionManager: PlayerPositionManager
        lateinit var playerStatManager: PlayerStatManager
        lateinit var optionManager: OptionManager
        lateinit var mobManager: MobManager
    }
    private lateinit var title: Title
    private lateinit var localMap: LocalMap
    private lateinit var worldMap: WorldMap
    private lateinit var combat: Combat

    override fun settings() {
        size(1280, 720)
    }

    override fun setup() {
        surface.setResizable(true)
        noStroke()

        this.getSurface().setTitle("ProcessingProject")

        stateType = StateType()
        mapManager = MapManager()
        playerPositionManager = PlayerPositionManager()
        playerStatManager = PlayerStatManager()
        optionManager = OptionManager()
        mobManager = MobManager()

        playerPositionManager.setPlayerSpeed(3)

        writeResources()

        playerStatManager.loadPlayerData()
        mobManager.loadMobData()
        TmxLoader()

        //セーブとかするならこれ活用でいけそう
        //今はとりあえず村として設定
        mapManager.setCurrentMap(mapManager.get("1village.tmx"))

        title = Title(this)
        localMap = LocalMap(this)
        worldMap = WorldMap(this)
        combat = Combat(this)

        val font = createFont("MS Gothic", 50f)
        textFont(font)
//        stateType.setState(StateType.COMBAT_STATE)
    }

    override fun keyPressed() { //キー入力受付
        when(stateType.getState()){
            StateType.LOCAL_STATE -> localMap.keyPressed()
            StateType.WORLD_STATE -> worldMap.keyPressed()
            StateType.COMBAT_STATE -> combat.keyPressed()
        }
        if (key == ESC){
            key = 0.toChar()
        }
    }

    override fun keyReleased() { //キー解放
        when(stateType.getState()){
            StateType.LOCAL_STATE -> localMap.keyReleased()
            StateType.WORLD_STATE -> worldMap.keyReleased()
            StateType.COMBAT_STATE -> combat.keyReleased()
        }
        if (key == ESC){
            key = 0.toChar()
        }
    }

    //TODO
    //Combat作って、playerのステータス作れば基礎は終わり。 -> done
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
            StateType.COMBAT_STATE -> {
                combat.display()
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
                saveResource("data/stat", file)
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
                    val ins = jar.getInputStream(entry)
                    try {
                        val buffer = ByteArray(8 * 1024)
                        var s = 0
                        while (ins.read(buffer).also { s = it } > 0) {
                            out.write(buffer, 0, s)
                        }
                    } finally {
                        ins.close()
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
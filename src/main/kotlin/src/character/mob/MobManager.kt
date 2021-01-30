package src.character.mob

import src.Main
import java.io.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MobManager {
    private var mobTemplate = HashMap<String, MobTemplate>()
    private var currentMob: MobTemplate? = null
    //mobロード作ってコンバット実装で終了 -> done

    /**
     * register PlayerData
     *
     * @param mt MobTemplate
     */
    fun register(name:  String, mt: MobTemplate){
        mobTemplate[name] = mt
    }

    /**
     * load MobTemplate from mobName_data.properties
     */
    fun loadMobData(){
        val properties = Properties()
        val dir = File(Main.mdFolder)
        val list = dir.listFiles()

        if (list != null){
            for (file in list){
                val ins = FileInputStream(file)
                val isr = InputStreamReader(ins, "UTF-8")

                properties.load(isr)
                ins.close()

                register(properties.getProperty("name"),
                        MobTemplate(
                                properties.getProperty("name"),
                                file,
                                properties.getProperty("level").toInt(),
                                properties.getProperty("exp").toInt(),
                                properties.getProperty("reqExp").toInt(),
                                properties.getProperty("hp").toInt(),
                                properties.getProperty("maxHp").toInt(),
                                properties.getProperty("mp").toInt(),
                                properties.getProperty("maxMp").toInt(),
                                properties.getProperty("money").toInt()
                        ))

            }
        }
    }

    /**
     * get PlayerData
     *
     * @return MobTemplate mobData
     */
    fun getMobData(name: String): MobTemplate{
        return mobTemplate[name]!!.copy()
    }

    fun getMobNames(): Array<String> {
        return mobTemplate.keys.toTypedArray()
    }

    fun setCurrentMob(md: MobTemplate){
        currentMob = md
    }

    fun getCurrentMob(): MobTemplate{
        return currentMob!!
    }
}
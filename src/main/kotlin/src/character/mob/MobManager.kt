package src.character.mob

import src.Main
import java.io.*
import java.util.*

class MobManager {
    private var mobTemplate: MobTemplate? = null
    //mobロード作ってコンバット実装で終了

    /**
     * register PlayerData
     *
     * @param mt MobTemplate
     */
    fun register(mt: MobTemplate){
        mobTemplate = mt
    }

    /**
     * load MobTemplate from mobName_data.properties
     */
    fun loadMobData(){
        val properties = Properties()
        val file = Main.mdFolder

        try {
            val ins = FileInputStream(file)
            val isr = InputStreamReader(ins, "UTF-8")
            properties.load(isr)
            ins.close()
        }catch (e: IOException){
            e.printStackTrace()
        }

        register(MobTemplate(
                properties.getProperty("name"),
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

    /**
     * get PlayerData
     *
     * @return MobTemplate playerData
     */
    fun getMobData(): MobTemplate{
        return mobTemplate!!
    }
}
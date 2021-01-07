package src.character.player

import src.Main
import java.io.*
import java.util.*

class PlayerStatManager {
    private var playerData: PlayerStatTemplate? = null

    /**
     * register PlayerData
     *
     * @param pst PlayerStatTemplate
     */
    fun register(pst: PlayerStatTemplate){
        playerData = pst
    }

    /**
     * load PlayerData from player_data.properties
     */
    fun loadPlayerData(){
        val properties = Properties()
        val file = Main.pdFile

        try {
            val ins = FileInputStream(file)
            val isr = InputStreamReader(ins, "UTF-8")
            properties.load(isr)
            ins.close()
        }catch (e: IOException){
            e.printStackTrace()
        }

        register(PlayerStatTemplate(
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
     * @return PlayerStatTemplate playerData
     */
    fun getPlayerData(): PlayerStatTemplate{
        return playerData!!
    }
}
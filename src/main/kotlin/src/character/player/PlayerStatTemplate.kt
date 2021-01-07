package src.character.player

import src.Main
import java.io.*
import java.util.*

class PlayerStatTemplate(
        val name: String,
        var level: Int,
        var exp: Int,
        var reqExp: Int,
        var hp: Int,
        var maxHp: Int,
        var mp: Int,
        var maxMp: Int,
        var money: Int
) {

    /**
     * Add Exp
     */
    fun addExp(exp: Int){
        this.exp += exp
        if (exp >= reqExp){
            levelUp()
        }
    }

    /**
     * Level Up
     */
    fun levelUp(){
        changeLevel(++level, exp - reqExp)
        if (exp >= reqExp) levelUp()
    }

    /**
     * レベル変更に伴う経験値等の変更
     *
     * @param lvl Int
     * @param currentExp Int
     */
    private fun changeLevel(lvl: Int, currentExp: Int){
        level = lvl
        exp = currentExp
        reqExp = level * 10
    }

    fun save(){
        val properties = Properties()
        val file = Main.pdFile

        try {
            properties.setProperty("name", name)
            properties.setProperty("level", level.toString())
            properties.setProperty("exp", exp.toString())
            properties.setProperty("reqExp", reqExp.toString())
            properties.setProperty("hp", hp.toString())
            properties.setProperty("maxHp", maxHp.toString())
            properties.setProperty("mp", mp.toString())
            properties.setProperty("maxMp", maxMp.toString())
            properties.setProperty("money", money.toString())

            val os = FileOutputStream(file)
            val osw = OutputStreamWriter(os, "UTF-8")
            properties.store(osw, "Comments")

            osw.close()
        }catch (e: IOException){
            e.printStackTrace()
        }
        
    }
}
package src.character.mob

import src.Main
import java.io.*
import java.util.*

class MobTemplate(
        val name: String,
        private val file: File,
        var level: Int,
        var exp: Int,
        var reqExp: Int,
        var hp: Int,
        var maxHp: Int,
        var mp: Int,
        var maxMp: Int,
        var money: Int
) {

    fun addHp(changeHp: Int){
        hp += changeHp
    }

    //deep copy用
    fun copy() = MobTemplate(name, file, level, exp, reqExp, hp, maxHp, mp, maxMp, money)

    fun save(){
        val properties = Properties()

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
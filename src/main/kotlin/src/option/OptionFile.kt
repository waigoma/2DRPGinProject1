package src.option

import src.Main
import java.io.*
import java.nio.charset.StandardCharsets
import java.util.*

class OptionFile {
    private val opManager = Main.optionManager

    fun loadOption() {
        val properties = Properties()
        
        try {
            val ins = FileInputStream(Main.opFile)
            val isr = InputStreamReader(ins, StandardCharsets.UTF_8)
            properties.load(isr)
            ins.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        when(properties.getProperty("difficulty")){
            "easy" -> opManager.setDifficulty(OptionManager.EASY)
            "normal" -> opManager.setDifficulty(OptionManager.NORMAL)
            "hard" -> opManager.setDifficulty(OptionManager.HARD)
        }
    }

    fun saveOption() {
        val properties = Properties()
        var difficulty = ""

        when(opManager.getDifficulty()){
            OptionManager.EASY -> difficulty = "easy"
            OptionManager.NORMAL -> difficulty = "normal"
            OptionManager.HARD -> difficulty = "hard"
        }
        
        try {
            properties.setProperty("difficulty", difficulty)
            val ous: OutputStream = FileOutputStream(Main.opFile)
            val osw = OutputStreamWriter(ous, StandardCharsets.UTF_8)
            properties.store(osw, "Comments")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}
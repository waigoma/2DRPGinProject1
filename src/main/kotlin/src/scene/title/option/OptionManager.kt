package src.scene.title.option

class OptionManager {
    companion object{
        const val EASY = 1200
        const val NORMAL = 600
        const val HARD = 300
    }

    private var difficulty = EASY

    fun getDifficulty(): Int{
        return difficulty
    }

    fun setDifficulty(difficulty: Int){
        this.difficulty = difficulty
    }

    fun getStrDifficulty(): String{
        var strDifficulty = ""
        when(difficulty){
            EASY -> strDifficulty = "easy"
            NORMAL -> strDifficulty = "normal"
            HARD -> strDifficulty = "hard"
        }
        return strDifficulty
    }
}
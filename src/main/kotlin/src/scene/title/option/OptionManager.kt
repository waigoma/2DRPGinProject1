package src.scene.title.option

class OptionManager {
    companion object{
        const val EASY = 1200
        const val NORMAL = 600
        const val HARD = 300
    }

    private var difficulty = EASY

    /**
     * 難易度取得
     *
     * @return Int difficulty
     */
    fun getDifficulty(): Int{
        return difficulty
    }

    /**
     * 難易度設定
     *
     * @param difficulty Int
     */
    fun setDifficulty(difficulty: Int){
        this.difficulty = difficulty
    }

    /**
     * 難易度をStringで取得
     *
     * @return String difficulty
     */
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
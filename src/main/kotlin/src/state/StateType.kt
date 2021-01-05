package src.state

class StateType {
    //現在のステートを管理するクラス
    companion object{
        const val TITLE_STATE: Int = 100
        const val LOCAL_STATE: Int = 200
        const val WORLD_STATE: Int = 300
        const val COMBAT_STATE: Int = 400
    }

    //初期値はタイトル
    private var state: Int = 100

    /**
     * 現在のステートを渡す
     * @return state
     */
    fun getState(): Int{
        return state
    }

    /**
     * 現在のステートを変更する
     * @param state state値
     */
    fun setState(state: Int){
        this.state = state
    }
}
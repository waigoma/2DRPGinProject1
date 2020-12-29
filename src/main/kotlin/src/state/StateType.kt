package src.state

class StateType {
    companion object{
        const val TITLE_STATE: Int = 100
        const val LOCAL_STATE: Int = 200
        const val WORLD_STATE: Int = 300
    }

    private var state: Int = 100

    fun getState(): Int{
        return state
    }

    fun setState(state: Int){
        this.state = state
    }
}
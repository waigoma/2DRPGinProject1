package src.player

import kotlin.collections.ArrayList

class PlayerPositionManager {
    private var playerX: Float = 0f
    private var playerY: Float = 0f
    private var playerSpeed: Int = 0
    private var playerWidth: Int = 0
    private var playerHeight: Int = 0

    private var canMove: Boolean = false

    private var up: Boolean = false
    private var down: Boolean = false
    private var left: Boolean = false
    private var right: Boolean = false

    fun setPlayerX(px: Float){
        playerX = px
    }

    fun setPlayerY(py: Float){
        playerY = py
    }

    fun setPlayerSpeed(speed: Int){
        playerSpeed = speed
    }

    fun setPlayerWidth(width: Int){
        playerWidth = width
    }

    fun setPlayerHeight(height: Int){
        playerHeight = height
    }

    fun setUp(up: Boolean){
        this.up = up
    }

    fun setDown(down: Boolean){
        this.down = down
    }

    fun setLeft(left: Boolean){
        this.left = left
    }

    fun setRight(right: Boolean){
        this.right = right
    }

    fun setCanMove(cm: Boolean){
        this.canMove = cm
    }


    fun getPlayerXY(): ArrayList<Float>{
        return ArrayList(listOf(
                playerX,
                playerY
        ))
    }

    fun getPlayerWH(): ArrayList<Int>{
        return ArrayList(listOf(
                playerWidth,
                playerHeight
        ))
    }

    fun getPlayerSpeed(): Int{
        return playerSpeed
    }

    fun getUp(): Boolean{
        return up
    }

    fun getDown(): Boolean{
        return down
    }

    fun getLeft(): Boolean{
        return left
    }

    fun getRight(): Boolean{
        return right
    }

    fun getCanMove(): Boolean{
        return canMove
    }
}
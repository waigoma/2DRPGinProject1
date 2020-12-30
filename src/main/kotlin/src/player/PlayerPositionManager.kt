package src.player

import kotlin.collections.ArrayList

class PlayerPositionManager {
    private var playerX: Float = 0f
    private var playerY: Float = 0f
    private var playerSpeed: Int = 0
    private var playerWidth: Int = 0
    private var playerHeight: Int = 0

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
}
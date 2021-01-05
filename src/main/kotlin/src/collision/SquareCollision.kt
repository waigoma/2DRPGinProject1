package src.collision

import src.Main

class SquareCollision(
        private var x: Float,
        private var y: Float,
        private val width: Float,
        private val height: Float,
        private val sizeWidth: Int,
        private val sizeHeight: Int
) {
    private val ppm = Main.playerPositionManager

    private var playerX: Float
    private var playerY: Float
    private var playerWidth: Int
    private var playerHeight: Int

    private var first = true
    
    init {
        playerX = ppm.getPlayerXY()[0]
        playerY = ppm.getPlayerXY()[1]
        playerWidth = ppm.getPlayerWH()[0]
        playerHeight = ppm.getPlayerWH()[1]
    }

    fun setWH(){
        playerWidth = ppm.getPlayerWH()[0]
        playerHeight = ppm.getPlayerWH()[1]
    }

    fun fixError(fx: Float, fy: Float) {
        if (first) {
            x -= fx
            y -= fy
            first = false
        }
    }

    fun outside() {
        playerX = ppm.getPlayerXY()[0]
        playerY = ppm.getPlayerXY()[1]
        if (playerY > sizeHeight - playerHeight) {
            playerY = (sizeHeight - playerHeight).toFloat()

        }

        if (playerY < 0) {
            playerY = 0f
        }

        if (playerX < 0) {
            playerX = 0f
        }

        if (playerX > sizeWidth - playerWidth) {
            playerX = (sizeWidth - playerWidth).toFloat()
        }

        ppm.setPlayerX(playerX)
        ppm.setPlayerY(playerY)
    }

    fun objectCollision() {
        playerX = ppm.getPlayerXY()[0]
        playerY = ppm.getPlayerXY()[1]
        if (playerX < x + width && playerX + playerWidth > x && playerY < y + height && playerY + playerHeight > y) {
            when{
                playerX > x + width - 4 -> playerX = x + width
                playerX + playerWidth < x + 4 -> playerX = x - playerWidth
                playerY > y + height / 2 -> playerY = y + height
                playerY + playerHeight < y + height / 2 -> playerY = y - playerHeight
            }
        }
        ppm.setPlayerX(playerX)
        ppm.setPlayerY(playerY)
    }

    fun colTrigger(): Boolean {
        playerX = ppm.getPlayerXY()[0]
        playerY = ppm.getPlayerXY()[1]
        setWH()
        if (playerX < x + width && playerX + playerWidth > x && playerY < y + height && playerY + playerHeight > y) {
            when{
                playerX > x + width - 4 -> return true
                playerX + playerWidth < x + 4 -> return true
                playerY > y + height / 2 -> return true
                playerY + playerHeight < y + height / 2 -> return true
            }
        }
        return false
    }
}
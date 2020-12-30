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

    private var playerx = ppm.getPlayerXY()[0]
    private var playery = ppm.getPlayerXY()[1]
    private val playerWidth = ppm.getPlayerWH()[0]
    private val playerHeight = ppm.getPlayerWH()[1]

    private var first = true

    fun fixError(fx: Float, fy: Float) {
        if (first) {
            x -= fx
            y -= fy
            first = false
        }
    }

    fun outside(playerXY: ArrayList<Float>) {
        if (playerXY[1] > sizeHeight - playerHeight) {     //PlayerHeightはPlayerの大きさがわかり次第いれてください「定数」
            playery = (sizeHeight - playerHeight).toFloat()
        }

        if (playerXY[1] < 0) {
            playery = 0f
        }

        if (playerXY[0] < 0) {
            playerx = 0f
        }

        if (playerXY[0] > sizeWidth - playerWidth) {
            playerx = (sizeWidth - playerWidth).toFloat()
        }

        ppm.setPlayerX(playerx)
        ppm.setPlayerY(playery)
    }

    fun objectCollision(playerXY: ArrayList<Float>) {
        if (playerXY[0] < x + width && playerXY[0] + playerWidth > x && playerXY[1] < y + height && playerXY[1] + playerHeight > y) {
            when{
                playerXY[0] > x + width - 4 -> playerx = x + width
                playerXY[0] + playerWidth < x + 4 -> playerx = x - playerWidth
                playerXY[1] > y + height / 2 -> playery = y + height
                playerXY[1] + playerHeight < y + height / 2 -> playery = y - playerHeight
            }

            ppm.setPlayerX(playerx)
            ppm.setPlayerY(playery)
        }
    }
}
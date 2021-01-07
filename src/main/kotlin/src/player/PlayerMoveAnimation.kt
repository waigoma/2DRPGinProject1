package src.player

import processing.core.PApplet
import processing.core.PConstants
import processing.core.PImage
import src.Main
import src.api.InputStreamToPImage
import java.awt.image.BufferedImage
import java.io.InputStream
import javax.imageio.ImageIO

class PlayerMoveAnimation(private val plet: PApplet) {
    private var playerImg: PImage

    private var time = 0
    private var right = false
    private var left = false
    private var up = false
    private var down = false

    private var playerX: Float = 0.0f
    private var playerY: Float = 0.0f

    private val ppm = Main.playerPositionManager
    private val isTp = InputStreamToPImage()

    init {
        playerImg = isTp.isToPImage(this.javaClass.getResourceAsStream("/data/img/character/playerImg_S_stand.png"))

        ppm.setPlayerWidth(playerImg.width)
        ppm.setPlayerHeight(playerImg.height)
    }

    fun keyPressed() { // コード化されているキーが押された
        if (ppm.getCanMove()) {
            if (plet.keyCode == PConstants.RIGHT || plet.key == 'd' || plet.key == 'D') right = true
            if (plet.keyCode == PConstants.LEFT || plet.key == 'a' || plet.key == 'A') left = true
            if (plet.keyCode == PConstants.UP || plet.key == 'w' || plet.key == 'W') up = true
            if (plet.keyCode == PConstants.DOWN || plet.key == 's' || plet.key == 'S') down = true
        }
    }

    fun keyReleased() { //キーが離されたら
        if (plet.keyCode == PConstants.RIGHT || plet.key == 'd' || plet.key == 'D') {
            playerImg = isTp.isToPImage(this.javaClass.getResourceAsStream("/data/img/character/playerImg_D_stand.png"))
            right = false
        }

        if (plet.keyCode == PConstants.LEFT || plet.key == 'a' || plet.key == 'A') {
            playerImg = isTp.isToPImage(this.javaClass.getResourceAsStream("/data/img/character/playerImg_A_stand.png"))
            left = false
        }

        if (plet.keyCode == PConstants.UP || plet.key == 'w' || plet.key == 'W') {
            playerImg = isTp.isToPImage(this.javaClass.getResourceAsStream("/data/img/character/playerImg_W_stand.png"))
            up = false
        }

        if (plet.keyCode == PConstants.DOWN || plet.key == 's' || plet.key == 'S') {
            playerImg = isTp.isToPImage(this.javaClass.getResourceAsStream("/data/img/character/playerImg_S_stand.png"))
            down = false
        }
    }

    fun draw() {
        playerX = ppm.getPlayerXY()[0]
        playerY = ppm.getPlayerXY()[1]
        if (right) {
            time++
            playerX += ppm.getPlayerSpeed()
        }

        if (left) {
            time++
            playerX -= ppm.getPlayerSpeed()
        }

        if (up) {
            time++
            playerY -= ppm.getPlayerSpeed()
        }

        if (down) {
            time++
            playerY += ppm.getPlayerSpeed()
        }

        if (time > 60) time = 0

        ppm.setPlayerX(playerX)
        ppm.setPlayerY(playerY)

        ppm.setUp(up)
        ppm.setDown(down)
        ppm.setLeft(left)
        ppm.setRight(right)

        drawImg()
    }

    private fun drawImg() {
        playerX = ppm.getPlayerXY()[0]
        playerY = ppm.getPlayerXY()[1]

        if (up) {
            playerImg = isTp.isToPImage(this.javaClass.getResourceAsStream("/data/img/character/playerImg_W_walk.png"))
            if (time > 30) {
                playerImg = isTp.isToPImage(this.javaClass.getResourceAsStream("/data/img/character/playerImg_W_walk2.png"))
            }
        }

        if (left) {
            playerImg = isTp.isToPImage(this.javaClass.getResourceAsStream("/data/img/character/playerImg_A_walk.png"))
            if (time > 30) {
                playerImg = isTp.isToPImage(this.javaClass.getResourceAsStream("/data/img/character/playerImg_A_walk2.png"))
            }
        }

        if (down) {
            playerImg = isTp.isToPImage(this.javaClass.getResourceAsStream("/data/img/character/playerImg_S_walk.png"))
            if (time > 30) {
                playerImg = isTp.isToPImage(this.javaClass.getResourceAsStream("/data/img/character/playerImg_S_walk2.png"))
            }
        }

        if (right) {
            playerImg = isTp.isToPImage(this.javaClass.getResourceAsStream("/data/img/character/playerImg_D_walk.png"))
            if (time > 30) {
                playerImg = isTp.isToPImage(this.javaClass.getResourceAsStream("/data/img/character/playerImg_D_walk2.png"))
            }
        }
        plet.image(playerImg, playerX, playerY)
    }
    
}
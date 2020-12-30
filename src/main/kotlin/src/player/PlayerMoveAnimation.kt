package src.player

import processing.core.PApplet
import processing.core.PConstants
import processing.core.PImage
import src.Main
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

    init {
        playerImg = isToPImage(this.javaClass.getResourceAsStream("/data/img/character/playerImg_S_stand.png"))

        playerImg.save("E:\\waichi\\Desktop\\test\\test\\test.png")

        println(playerImg)

        ppm.setPlayerWidth(playerImg.width)
        ppm.setPlayerHeight(playerImg.height)
    }

    fun setPosition(){
        playerX = ppm.getPlayerXY()[0]
        playerY = ppm.getPlayerXY()[1]
        ppm.setPlayerSpeed(3)
    }

    fun keyPressed() { // コード化されているキーが押された
        if (plet.keyCode == PConstants.RIGHT || plet.key == 'd' || plet.key == 'D') right = true
        if (plet.keyCode == PConstants.LEFT || plet.key == 'a' || plet.key == 'A') left = true
        if (plet.keyCode == PConstants.UP || plet.key == 'w' || plet.key == 'W') up = true
        if (plet.keyCode == PConstants.DOWN || plet.key == 's' || plet.key == 'S') down = true
    }

    fun keyReleased() { //キーが離されたら
        if (plet.keyCode == PConstants.RIGHT || plet.key == 'd' || plet.key == 'D') {
            playerImg = isToPImage(this.javaClass.getResourceAsStream("/data/img/character/playerImg_D_stand.png"))
            right = false
        }
        if (plet.keyCode == PConstants.LEFT || plet.key == 'a' || plet.key == 'A') {
            playerImg = isToPImage(this.javaClass.getResourceAsStream("/data/img/character/playerImg_A_stand.png"))
            left = false
        }
        if (plet.keyCode == PConstants.UP || plet.key == 'w' || plet.key == 'W') {
            playerImg = isToPImage(this.javaClass.getResourceAsStream("/data/img/character/playerImg_W_stand.png"))
            up = false
        }
        if (plet.keyCode == PConstants.DOWN || plet.key == 's' || plet.key == 'S') {
            playerImg = isToPImage(this.javaClass.getResourceAsStream("/data/img/character/playerImg_S_stand.png"))
            down = false
        }
    }

    fun draw() {
        drawImg()
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
    }

    private fun drawImg() {
        if (up) {
            playerImg = isToPImage(this.javaClass.getResourceAsStream("/data/img/character/playerImg_W_walk.png"))
            if (time > 30) {
                playerImg = isToPImage(this.javaClass.getResourceAsStream("/data/img/character/playerImg_W_walk2.png"))
            }
        }

        if (left) {
            playerImg = isToPImage(this.javaClass.getResourceAsStream("/data/img/character/playerImg_A_walk.png"))
            if (time > 30) {
                playerImg = isToPImage(this.javaClass.getResourceAsStream("/data/img/character/playerImg_A_walk2.png"))
            }
        }

        if (down) {
            playerImg = isToPImage(this.javaClass.getResourceAsStream("/data/img/character/playerImg_S_walk.png"))
            if (time > 30) {
                playerImg = isToPImage(this.javaClass.getResourceAsStream("/data/img/character/playerImg_S_walk2.png"))
            }
        }

        if (right) {
            playerImg = isToPImage(this.javaClass.getResourceAsStream("/data/img/character/playerImg_D_walk.png"))
            if (time > 30) {
                playerImg = isToPImage(this.javaClass.getResourceAsStream("/data/img/character/playerImg_D_walk2.png"))
            }
        }
        plet.image(playerImg, playerX, playerY)
    }

    private fun isToPImage(inputStream: InputStream): PImage{
        val image = ImageIO.read(inputStream)
        val bfi = BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB)
        val gr = bfi.createGraphics()
        gr.drawImage(image, 0, 0, null)
        gr.dispose()
        return PImage(bfi)
    }
}
package src.scene.combat

import processing.core.PApplet
import processing.core.PConstants
import processing.core.PImage
import src.Main
import src.api.InputStreamToPImage
import src.api.TextFormat
import java.io.InputStream

class CombatAnimation(private val plet: PApplet) {
    private val psm = Main.playerStatManager
    private val isTp = InputStreamToPImage()
    private val tf = TextFormat(plet)

    private var playerData = psm.getPlayerData()
    private lateinit var background: PImage
    private lateinit var mobImage: PImage

    fun setup(bg: InputStream, mobImg: InputStream){
        background = isTp.isToPImage(bg)
        mobImage = isTp.isToPImage(mobImg)

        plet.imageMode(PConstants.CENTER)
        tf.setFont(this.javaClass.getResourceAsStream("/data/UDDigiKyokashoN-R.ttc"))

        background.resize(plet.width, plet.height)
    }

    fun draw(){
        plet.image(background, plet.width / 2f, plet.height / 2f)
        drawOutLine()
    }

    private fun drawOutLine(){
        plet.rectMode(PConstants.CORNER)
        plet.strokeWeight(2f)
        plet.stroke(0)
        plet.fill(255f, 255f, 255f, 100f)
        //narration
        plet.rect(10f, 580f, plet.width - 20f, plet.height - 590f)

        //player rect
        plet.rect(880f, 460f, 350f, 100f)
        plet.line(880f, 530f, 1230f, 530f)
        //player name
        tf.changeColorSize(0f, 32f, PConstants.LEFT)
        plet.text(playerData.name, 895f, 500f)
        //player level exp mp hp
        tf.changeColorSize(0f, 28f, PConstants.RIGHT)
        plet.text("Lv. ${playerData.level}", 1200f, 495f)
        tf.changeColorSize(0f, 16f, PConstants.RIGHT)
        plet.text("Exp: ${playerData.exp} / ${playerData.reqExp}", 1215f, 522f)
        plet.textAlign(PConstants.LEFT)
        plet.text("MP: ${playerData.mp} / ${playerData.maxMp}", 895f, 522f)
        tf.changeColorSize(0f, 20f, PConstants.RIGHT)
        plet.text("${playerData.hp} / ${playerData.maxHp}", 1210f, 552f)

        //mob rect
        plet.fill(255f, 255f, 255f, 100f)
        plet.rect(50f, 60f, 350f, 100f)
        plet.line(50f, 130f, 400f, 130f)
        //mob name
        tf.changeColorSize(0f, 32f, PConstants.LEFT)
        plet.text(playerData.name, 65f, 110f)
        //mob level hp
        tf.changeColorSize(0f, 28f, PConstants.RIGHT)
        plet.text("Lv. ${playerData.level}", 390f, 110f)
        tf.changeColorSize(0f, 20f, PConstants.RIGHT)
        plet.text("${playerData.hp} / ${playerData.maxHp}", 390f, 152f)
    }
}
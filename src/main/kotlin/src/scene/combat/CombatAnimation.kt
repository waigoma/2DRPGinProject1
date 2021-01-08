package src.scene.combat

import processing.core.PApplet
import processing.core.PConstants
import processing.core.PImage
import src.Main
import src.api.InputStreamToPImage
import src.api.TextFormat
import src.character.mob.MobTemplate
import java.io.InputStream

class CombatAnimation(private val plet: PApplet) {
    private val psm = Main.playerStatManager
    private val mm = Main.mobManager
    private val isTp = InputStreamToPImage()
    private val tf = TextFormat(plet)

    private var playerData = psm.getPlayerData()
    private lateinit var background: PImage
    private lateinit var mobImage: PImage
    private lateinit var mob: MobTemplate

    fun setup(bg: InputStream, mobImg: InputStream){
        background = isTp.isToPImage(bg)
        mobImage = isTp.isToPImage(mobImg)
        mob = mm.getCurrentMob()

        plet.imageMode(PConstants.CENTER)
        tf.setFont(this.javaClass.getResourceAsStream("/data/UDDigiKyokashoN-R.ttc"))

        background.resize(plet.width, plet.height)
    }

    fun draw(){
        playerData = psm.getPlayerData()
        mob = mm.getCurrentMob()
        plet.image(background, plet.width / 2f, plet.height / 2f)
        lifeGauge()
        drawOutLine()
    }

    private fun drawOutLine(){//正味ごり押し感半端ない。もっときれいにできる。
        plet.rectMode(PConstants.CORNER)
        plet.strokeWeight(2f)
        plet.stroke(0)
        plet.fill(255f, 255f, 255f, 100f)
        //narration
        plet.rect(300f, 580f, plet.width - 310f, plet.height - 590f)
        plet.rect(10f, 400f, 280f, plet.height - 410f)

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
        plet.text(mob.name, 65f, 110f)
        //mob level hp
        tf.changeColorSize(0f, 28f, PConstants.RIGHT)
        plet.text("Lv. ${mob.level}", 390f, 110f)
        tf.changeColorSize(0f, 20f, PConstants.RIGHT)
        plet.text("${mob.hp} / ${mob.maxHp}", 390f, 152f)
    }

    private fun lifeGauge() {
        val pWidth = playerData.hp / playerData.maxHp * 350
        val mWidth = mob.hp / mob.maxHp * 350
        //player
        when{
            playerData.hp < playerData.maxHp * 0.2 -> plet.fill(255f, 0f, 0f)
            playerData.hp < playerData.maxHp * 0.4 -> plet.fill(255f, 200f, 0f)
            else ->  plet.fill(0f, 255f, 0f)
        }
        plet.noStroke()
        plet.rect(880f, 530f, pWidth.toFloat(), 30f)

        //mob
        when{
            mob.hp < mob.maxHp * 0.2 -> plet.fill(255f, 0f, 0f)
            mob.hp < mob.maxHp * 0.4 -> plet.fill(255f, 200f, 0f)
            else ->  plet.fill(0f, 255f, 0f)
        }
        plet.noStroke()
        plet.rect(50f, 130f, 350f, 30f)
    }
}
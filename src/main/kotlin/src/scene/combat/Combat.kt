package src.scene.combat

import processing.core.PApplet
import src.Main
import src.character.mob.MobTemplate

class Combat(private val plet: PApplet) {
    private val psm = Main.playerStatManager
    private val mm = Main.mobManager
    private val ca = CombatAnimation(plet)

    private var playerData = psm.getPlayerData()
    private lateinit var mobData: MobTemplate

    private var first = true

    private var attack = false
    private var playerTurn = true
    private var mobTurn = false
    private var damageEvent = false

    fun keyPressed() {
        if (plet.key == 'a' || plet.key == 'A') attack = true
    }

    fun keyReleased() {
    }

    fun display(){
        if (first){
            val bg = this.javaClass.getResourceAsStream("/data/img/background/plains3.jpg")
            val mobImg = this.javaClass.getResourceAsStream("/data/img/mob/batta.png")
            plet.surface.setSize(1280, 720)

            val res = mm.getMobNames()[0]
            mm.setCurrentMob(mm.getMobData(res))
            mobData = mm.getCurrentMob()

            ca.setup(bg, mobImg)
            first = false
            plet.delay(100)
        }
        playerData = psm.getPlayerData()
        mobData = mm.getCurrentMob()

        if (damageEvent) {
            plet.delay(1000)
            damageEvent = false
        }

        ca.draw()

        if (playerTurn){
            if (attack){
                mobData.addHp(-10)

                attack = false
                playerTurn = false
                mobTurn = true
                damageEvent = true

                ca.draw()
                ca.narration("敵に10ダメージ与えた。")
                return
            }else{
                ca.narration("${mobData.name}が現れた。\n${playerData.name}はどうする？(TODO)")
            }
        }

        if (mobTurn){
            playerData.addHp(-10)

            playerTurn = true
            mobTurn = false
            damageEvent = true

            ca.draw()
            ca.narration("10ダメージ受けた。")
        }
    }
}
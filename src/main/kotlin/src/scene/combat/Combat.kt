package src.scene.combat

import processing.core.PApplet
import src.Main

class Combat(private val plet: PApplet) {
    private val psm = Main.playerStatManager
    private val ca = CombatAnimation(plet)

    private var playerData = psm.getPlayerData()
    private var first = true

    fun display(){
        playerData = psm.getPlayerData()
        if (first){
            val bg = this.javaClass.getResourceAsStream("/data/img/background/plains3.jpg")
            val mobImg = this.javaClass.getResourceAsStream("/data/img/mob/batta.png")
            plet.surface.setSize(1280, 720)
            ca.setup(bg, mobImg)
            first = false
        }
        ca.draw()

    }
}
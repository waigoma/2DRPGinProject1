package src.map

import processing.core.PApplet
import src.Main
import src.character.player.PlayerMoveAnimation
import src.state.StateType

class Encounter(private val plet: PApplet) {
    private val option = Main.optionManager
    private var distance = 0

    fun randomEncounter(walking: Boolean, pma: PlayerMoveAnimation){
        if (walking) distance += plet.random(0f, 15f).toInt()

        if (distance > option.getDifficulty() * 5){
            Main.stateType.setState(StateType.COMBAT_STATE)
            distance = 0
            pma.stopImg()
            Main.mapManager.setChange()
        }
    }
}
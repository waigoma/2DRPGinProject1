package src.scene.combat

import processing.core.PApplet
import processing.core.PImage
import src.api.InputStreamToPImage
import java.io.InputStream

class CombatAnimation(private val plet: PApplet) {
    private val isTp = InputStreamToPImage()
    private lateinit var background: PImage
    private lateinit var mobImage: PImage

    fun setup(bg: InputStream, mobImg: InputStream){
        background = isTp.isToPImage(bg)
        mobImage = isTp.isToPImage(mobImg)
    }


}
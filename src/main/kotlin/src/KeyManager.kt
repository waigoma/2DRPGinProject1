package src

import processing.core.PApplet
import processing.core.PConstants
import kotlin.collections.ArrayList

class KeyManager(private val plet: PApplet) {
    private var up = false
    private var right = false
    private var down = false
    private var left = false

    fun keyPressed(){
        if (plet.keyCode == PConstants.UP || plet.key == 'w' || plet.key == 'W') up = true
        if (plet.keyCode == PConstants.RIGHT || plet.key == 'd' || plet.key == 'D') right = true
        if (plet.keyCode == PConstants.DOWN || plet.key == 's' || plet.key == 'S') down = true
        if (plet.keyCode == PConstants.LEFT || plet.key == 'a' || plet.key == 'A') left = true
    }

    fun keyReleased(){
        if (plet.keyCode == PConstants.UP || plet.key == 'w' || plet.key == 'W') up = false
        if (plet.keyCode == PConstants.RIGHT || plet.key == 'd' || plet.key == 'D') right = false
        if (plet.keyCode == PConstants.DOWN || plet.key == 's' || plet.key == 'S') down = false
        if (plet.keyCode == PConstants.LEFT || plet.key == 'a' || plet.key == 'A') left = false
    }

    fun getKeys(): ArrayList<Boolean>{
        return ArrayList(listOf(
                up,
                right,
                down,
                left
        ))
    }
}
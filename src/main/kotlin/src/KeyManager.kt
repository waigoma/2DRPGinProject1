package src

import processing.core.PApplet
import processing.core.PConstants

class KeyManager(private val plet: PApplet) {
    companion object{
        var right: Boolean = false
        var left: Boolean = false
        var up: Boolean = false
        var down: Boolean = false
    }

    fun keyPressed(){
        if (plet.keyCode == PConstants.RIGHT || plet.key == 'd' || plet.key == 'D') right = true
        if (plet.keyCode == PConstants.LEFT || plet.key == 'a' || plet.key == 'A') left = true
        if (plet.keyCode == PConstants.UP || plet.key == 'w' || plet.key == 'W') up = true
        if (plet.keyCode == PConstants.DOWN || plet.key == 's' || plet.key == 'S') down = true
    }

    fun keyReleased(){
        if (plet.keyCode == PConstants.RIGHT || plet.key == 'd' || plet.key == 'D') right = false
        if (plet.keyCode == PConstants.LEFT || plet.key == 'a' || plet.key == 'A') left = false
        if (plet.keyCode == PConstants.UP || plet.key == 'w' || plet.key == 'W') up = false
        if (plet.keyCode == PConstants.DOWN || plet.key == 's' || plet.key == 'S') down = false
    }
}
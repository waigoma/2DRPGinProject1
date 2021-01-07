package src.scene.title.titlemenu

import processing.core.PApplet
import processing.core.PConstants

class Button(//ボタンの描写&動作処理
        private val plet: PApplet,
        private val x: Float,
        private val y: Float,
        private val sizeX: Int,
        private val sizeY: Int,
        private val str: String) {

    private var state = 0

    fun run() { //Titleクラスのdisplayで呼ばれる
        display()
        logic()
    }

    private fun display() { //ボタンの描写//runで呼び出し
        plet.fill(35)
        plet.stroke(40)
        plet.strokeWeight(10f)
        plet.strokeJoin(PConstants.BEVEL)
        plet.rect(x, y, sizeX.toFloat(), sizeY.toFloat())
        plet.fill(255)
        plet.textSize(36f)
        plet.text(str, x, y)
        changeColor()
    }

    private fun checkInMouse(): Boolean { //マウスがボタンの上に乗ってるか乗ってないか判別
        if (plet.mouseX > x - sizeX / 2 && plet.mouseX < x + sizeX / 2) {
            if (plet.mouseY > y - sizeY / 2 && plet.mouseY < y + sizeY / 2) {
                return true
            }
        }
        return false
    }

    private fun checkState(): Int { //判別した結果を数字で返す
        if (!checkInMouse()) return 0 //ボタンにマウスが乗ってないとき
        return if (!plet.mousePressed) 1 else 2 //ボタンにマウスが乗ってるとき
        //クリックしたとき
    }

    fun isPush(): Boolean {
        return checkState() == 2
    } //押されたか判別


    private fun logic() { //判別した結果の数字を変数に代入
        state = checkState()
    }

    private fun changeColor() { //判別結果でボタンの色変更
        when (state) {
            0 -> {
            }
            1 -> plet.text("> $str <", x, y)
            2 -> plet.fill(0)
        }
    }
}
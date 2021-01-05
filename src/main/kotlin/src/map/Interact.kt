package src.map

import processing.core.PApplet
import processing.core.PConstants
import src.Main

class Interact(
    private var x: Float,
    private var y: Float,
    private val width: Float,
    private val height: Float,
    private val sizeWidth: Int,
    private val sizeHeight: Int,
    private val eventId: Int,
    private val name: String,
    private val direction: String,
    private val message: String,
    private val px1: Float,
    private val py1: Float,
) {
    private val ppm = Main.playerPositionManager
    private val mapManager = Main.mapManager
    private lateinit var plet: PApplet
    
    private var playerX = 0f
    private var playerY = 0f
    private var playerW = 0
    private var playerH = 0

    private var first = true
    private var chatCount = 0
    private var time = 0

    private var px = 0f
    private var py = 0f

    var runDisplayEvent = false
    var runChatEvent = false
    var runOnceEvent = false
    var runMoveEvent = false

    fun fixError(fx: Float, fy: Float) {
        if (first) {
            x -= fx
            y -= fy
            first = false
        }
    }

    fun trigger(): Boolean {
        playerX = ppm.getPlayerXY()[0]
        playerY = ppm.getPlayerXY()[1]
        playerW = ppm.getPlayerWH()[0]
        playerH = ppm.getPlayerWH()[1]
        if (playerX < x + width && playerX + playerW > x && playerY < y + height && playerY + playerH > y) {
            when{
                playerX > x + width - 4 -> return true
                playerX + playerW < x + 4 -> return true
                playerY > y + height / 2 -> return true
                playerY + playerH < y + height / 2 -> return true
            }
        }
        return false
    }

    fun event(plet: PApplet) {
        this.plet = plet
        when (eventId) {
            1 -> {
                //                System.out.println("event:1");
                if (isDirectionClick()) runDisplayEvent = true
                runDisplayEvent()
            }
            
            2 -> {
                //                System.out.println("event:2");
                if (isDirectionClick()) runChatEvent = true
                runChatEvent()
            }
            
            3 -> {
                //                System.out.println("event:3");
                if (isDirectionClick()) runOnceEvent = true
                runOnceEvent()
            }
            
            4 -> {
                //                System.out.println("event:4");
                if (isDirectionClick()) runMoveEvent = true
                runMoveEvent()
            }
        }
    }

    fun runDisplayEvent() {
        if (chatCount == 0 && runDisplayEvent) {
            px = playerX
            py = playerY
            ppm.setCanMove(false)
            chatCount++
        }
        
        if (runDisplayEvent) {
            chatDisplay()
            if (plet.keyCode == PConstants.ENTER.toInt()) runDisplayEvent = false
            time++
        } else {
            ppm.setCanMove(true)
            chatCount = 0
        }
    }

    fun runChatEvent() { //会話を表示できるようにする、選択肢を用意する
        if (runChatEvent) {
            return
        }
    }

    fun runOnceEvent() { //やったかどうかをチェックして実行、やったかどうかは保存できるようにする
        if (runOnceEvent) {
            return
        }
    }

    fun runMoveEvent() { //mapTmpの値を変化させる、座標とかも合わせて取得できるようにする
        if (runMoveEvent) {
            if (mapManager.exists("$name.tmx")){
                val map = mapManager.get("$name.tmx")
                mapManager.setCurrentMap(map)
            }
            ppm.setPlayerX(px1)
            ppm.setPlayerY(py1)
            runMoveEvent = false
        }
    }

    fun chatDisplay() {
        plet.rectMode(PConstants.CENTER)
        plet.stroke(0)
        plet.strokeWeight(8f)
        plet.fill(0, 0f)
        plet.rect(plet.width / 2f, plet.height / 1.12f, 400f, 150f)
        plet.fill(0, 230f)
        plet.stroke(255)
        plet.strokeWeight(4f)
        plet.rect(plet.width / 2f, plet.height / 1.12f, 400f, 150f)
        plet.fill(255)
        plet.textAlign(PConstants.LEFT, PConstants.TOP)
        plet.textSize(24f)
        plet.text("『$name』", plet.width / 2f, plet.height / 1.115f, 400f, 150f)
        plet.textSize(18f)
        plet.text(message, plet.width / 2f, plet.height / 1.07f, 400f, 150f)
        plet.textAlign(PConstants.RIGHT, PConstants.BOTTOM)
        if (time < 30) plet.text("▶", plet.width / 2f, plet.height / 1.12f, 375f, 125f)
        if (time > 60) time = 0
        playerX = px
        playerY = py
    }

    fun isDirectionClick(): Boolean {
        when (direction) {
            "up" -> {
                return if (ppm.getUp()) {
                    plet.mousePressed
                } else false
            }

            "right" -> {
                return if (ppm.getRight()) {
                    plet.mousePressed
                } else false
            }

            "down" -> {
                return if (ppm.getDown()) {
                    plet.mousePressed
                } else false
            }

            "left" -> {
                return if (ppm.getLeft()) {
                    plet.mousePressed
                } else false
            }

            "none" -> return plet.mousePressed
        }
        return false
    }
}
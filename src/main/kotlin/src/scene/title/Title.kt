package src.scene.title

import processing.core.PApplet
import processing.core.PConstants
import processing.core.PFont
import src.scene.title.titlemenu.TitleMenu
import java.awt.Font
import java.awt.FontFormatException
import java.io.IOException
import java.io.InputStream


class Title(private val plet: PApplet) {
    private val fadeSpeed = 1.75f
    //getResourceを使う時はFile.separatorを使ってはいけない。"/"を使う。
    private val url: InputStream = this.javaClass.getResourceAsStream("/data/PixelMplus12-Regular.ttf")
    private lateinit var font: PFont
    private var titleMenu: TitleMenu

    var titleScene: Int = 0
    var count: Int = 0
    var fadeAlpha = 255f

    //fontの読み込み
    init {
        plet.textAlign(PConstants.CENTER, PConstants.CENTER)

        try {
            font = PFont(Font.createFont(Font.TRUETYPE_FONT, url), false)
        }catch (e: FontFormatException){
            println("形式がフォントではありません。")
        }catch (e: IOException) {
            println("入出力エラーでフォントを読み込むことができませんでした。\n>>> " + e.message)
        }
        titleMenu = TitleMenu(plet, font)
    }

    fun run(){
        if (count == 0){
            count++
        }
        when(titleScene){
            0 -> {
                display()
                fadeDisplay()
            }
            1 -> {
                plet.delay(500)
                titleScene = 2
            }
            2 -> {
                titleMenu.display()
            }
        }
    }

    private fun display(){
        plet.background(0)
        plet.fill(230)
        plet.textFont(font, 72f)
        plet.text("2DRPG in Project", plet.width / 2f, plet.height / 3.8f)

        if (plet.keyPressed || plet.mousePressed) titleScene = 1
    }

    private fun fadeDisplay(){
        plet.textFont(font, 36f)
        plet.fill(230, fadeAlpha)
        plet.text("Press any key...", plet.width / 2f, plet.height / 1.4f)

        fadeOutIn()
    }

    private fun fadeOutIn(){
        if (fadeAlpha <= 255 && fadeAlpha != 0f) fadeAlpha -= fadeSpeed
        if (fadeAlpha <= 0) fadeAlpha = 255f
    }
}
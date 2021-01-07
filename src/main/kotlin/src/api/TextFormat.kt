package src.api

import processing.core.PApplet
import processing.core.PFont
import java.awt.Font
import java.awt.FontFormatException
import java.io.IOException
import java.io.InputStream

class TextFormat(private val plet: PApplet) {

    fun changeColorSize(color: Float, size: Float, align: Int){
        plet.fill(color)
        plet.textSize(size)
        plet.textAlign(align)
    }

    fun setFont(ins: InputStream){
        var font: PFont? = null
        try {
            font = PFont(Font.createFont(Font.TRUETYPE_FONT, ins), false)
        }catch (e: FontFormatException){
            println("形式がフォントではありません。")
        }catch (e: IOException) {
            println("入出力エラーでフォントを読み込むことができませんでした。\n>>> " + e.message)
        }
        plet.textFont(font)
    }

    fun getFont(ins: InputStream): PFont{
        var font: PFont? = null
        try {
            font = PFont(Font.createFont(Font.TRUETYPE_FONT, ins), false)
        }catch (e: FontFormatException){
            println("形式がフォントではありません。")
        }catch (e: IOException) {
            println("入出力エラーでフォントを読み込むことができませんでした。\n>>> " + e.message)
        }
        return font!!
    }

}
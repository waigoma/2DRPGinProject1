package src.title.titlemenu

import processing.core.PApplet
import processing.core.PConstants
import processing.core.PFont
import src.Main
import src.state.StateType
import kotlin.system.exitProcess

class TitleMenu(private val plet: PApplet, private val font: PFont/*,thread: Testttt?*/) {
    //タイトルメニュー画面
    private var button: Button
    private var button1: Button
    private var button2: Button

//    var option: Option
//    var opFile: OptionFile
//    var test: Testttt

    init {
//        this.titleFrame = plet.loadImage("src/waigoma/img/photo_frame1.png");
//        opFile = OptionFile()
//        opFile.loadOption()
//        option = Option(opFile.getDifficulty())
//        option.properties()
        plet.rectMode(PConstants.CENTER)
        plet.textAlign(PConstants.CENTER, PConstants.CENTER)
        plet.imageMode(PConstants.CENTER)
        button = Button(plet, plet.width / 2f, plet.height / 2.027f, 450, 70, "Game Start") //Buttonクラスをインスタンス化
        button1 = Button(plet, plet.width / 2f, plet.height / 1.64f, 450, 70, "Option")
        button2 = Button(plet, plet.width / 2f, plet.height / 1.375f, 450, 70, "Exit")
    }


    fun display() { //描写内容
        plet.background(30)
        plet.fill(230)
        plet.textFont(font, 48f)
        plet.text("2DRPG in Project", plet.width / 4f, plet.height / 8f)
        plet.text("MENU", plet.width / 3.8f, plet.height / 4f)
        plet.textSize(24f)
        plet.fill(255)
//        plet.text(opFile.getDifficulty(), plet.width / 1.25f, plet.height / 5f)

//        plet.stroke(255);
//        plet.fill(30);
//        plet.rect(plet.width/1.9f, plet.height/1.6f, 600, 300);
//        plet.image(titleFrame, plet.width/2f, plet.height/1.8f);
        button.run() //Buttonクラス内のrun()呼び出し
        button1.run()
        button2.run()
        if (button.isPush()) {
//            test.close()
            Main.stateType.setState(StateType.LOCAL_STATE) //描写内のボタン(?)が押されたらMainクラス内のstateを1に変更
        }
//        if (button1.isPush()) {
//            when (opFile.getDifficulty()) {
//                "easy" -> {
//                    opFile.setDifficulty("normal")
//                    opFile.saveOption()
//                }
//                "normal" -> {
//                    opFile.setDifficulty("hard")
//                    opFile.saveOption()
//                }
//                "hard" -> {
//                    opFile.setDifficulty("easy")
//                    opFile.saveOption()
//                }
//            }
//            option = Option(opFile.getDifficulty())
//            option.properties()
//            plet.delay(100)
//        }
        if (button2.isPush()) { //exit押されたら終了
            plet.delay(100) //0.1秒の遅延(クリックバグ対策)
            plet.exit()
        }
    }
}
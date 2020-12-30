package src.map

import processing.core.PApplet
import processing.core.PImage
import src.Main
import src.collision.SquareCollision
import src.state.StateType

class MapTemplate(
        val name: String,
        val nextMap: String,
        val previousMap: String,
        val nextX: Int,
        val nextY: Int,
        val previousX: Int,
        val previousY: Int,
        val mapTileWidth: Int,
        val mapTileHeight: Int,
        val tileWidth: Int,
        val tileHeight: Int,
        val list: List<Array<Int>>,
        val sqColList: ArrayList<SquareCollision>,
        val imgs: Array<PImage>
) {
    fun display(plet: PApplet) { //マップの一番手前に出てこないところの描写
        var count = 0 //layerカウント

        for (sqCol in sqColList) {
            when(Main.stateType.getState()){
                StateType.LOCAL_STATE -> sqCol.fixError(-6f, -3f)
                StateType.WORLD_STATE -> sqCol.fixError(4f, 4f)
            }
            sqCol.setWH()
            sqCol.objectCollision()
            sqCol.outside()
        }

        for (mapNums in list) { //Listからlayerの情報を引き出す(下のlayerから順に)
            var x = 0 //x座標(何個目か)
            var y = 0 //y座標(↑)
            count++
            if (count != list.size) { //一番上のlayer以外なら
                for (num in mapNums) { //layer情報を1つずつ取り出す
                    displayImages(num, x, y, plet)
                    if (x < mapTileWidth) { //横幅が所定タイル数までなら
                        x++ //x座標を+1
                    }
                    if (x >= mapTileWidth) { //横幅が所定タイル数を超えたら
                        x = 0 //x座標リセット
                        y++ //y座標+1
                    }
                }
            }
        }
    }

    fun topDisplay(plet: PApplet) { //マップの1番手前に来るlayerの描写
        var count = 0
        for (mapNums in list) {
            var x = 0
            var y = 0
            count++
            if (count == list.size) { //一番上のlayerなら
                for (num in mapNums) {
                    displayImages(num, x, y, plet)
                    if (x < mapTileWidth) {
                        x++
                    }
                    if (x >= mapTileWidth) {
                        x = 0
                        y++
                    }
                }
            }
        }
    }

    private fun displayImages(index: Int, x: Int, y: Int, plet: PApplet) { //数字の情報から写真を描写する
        val imgX: Float = (x * tileWidth).toFloat() //x座標
        val imgY: Float = (y * tileHeight).toFloat() //y座標
        val pImgs: Array<PImage> = imgs //PImageの配列をget
        val img: PImage
        if (index == 0) { //もし何もない場所だったら(layer内画像なし)
            return  //処理終了
        } else { //画像あり
            img = pImgs[index - 1] //配列は0からスタートでlayer情報は1からスタートなので-1
        }
        plet.tint(255, 255f)
        plet.image(img, imgX, imgY) //実際に画像を描写
    }

    fun event() {
//        for (interact in interactList) {
//            if (Main.state === StateType.LOCAL_STATE) interact.fixError(-6, -3)
//            if (Main.state === StateType.WORLD_STATE) interact.fixError(4, 4)
//            if (interact.trigger()) {
//                interact.event()
//            }
//        }
    }

    fun isNext(): Boolean {
//        if (nextList != null) {
//            for (nmt in getNextList()) {
//                if (Main.state === StateType.LOCAL_STATE) nmt.fixError(-6, -3)
//                if (Main.state === StateType.WORLD_STATE) nmt.fixError(4, 4)
//                if (nmt.mapTrigger()) return true
//            }
//        }
        return false
    }

    fun isBack(): Boolean {
//        if (getBackList() != null) {
//            for (bmt in getBackList()) {
//                if (Main.state === StateType.LOCAL_STATE) bmt.fixError(-6, -3)
//                if (Main.state === StateType.WORLD_STATE) bmt.fixError(4, 4)
//                if (bmt.mapTrigger()) return true
//            }
//        }
        return false
    }

    fun isBoss(): Boolean {
//        if (getBackList() != null) {
//            for (boss in getBossList()) {
//                if (Main.state === StateType.LOCAL_STATE) boss.fixError(-6, -3)
//                if (Main.state === StateType.WORLD_STATE) boss.fixError(4, 4)
//                if (boss.mapTrigger()) return true
//            }
//        }
        return false
    }
}
package src.map

import org.w3c.dom.Element
import org.w3c.dom.Node
import processing.core.PImage
import src.Main
import src.collision.SquareCollision
import java.awt.image.BufferedImage
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.collections.ArrayList

class TmxLoader {
    private lateinit var tsx: TsxLoader

    init { //TMX(マップデータ)ファイルを取得
        val list = ArrayList<String>(listOf(
                "1village.tmx",
                "1vhouse1.tmx",
                "1vhouse2.tmx",
                "dungeon1.tmx"
        ))
        for (file in list) { //マップデータを1つずつ読み込む
            val path = this.javaClass.getResourceAsStream("/data/tmx/$file") //ファイルのPathを取得
            loadTmx(path, file)
        }
    }

    private fun loadTmx(inputStream: InputStream, mapName: String) { //TMXファイルを解析
        //きちんと毎回初期化しよう(教訓)
        val mapList: ArrayList<Array<Int>> = ArrayList() //1つのマップのlayer情報をすべて保存
        val listBufImg: ArrayList<BufferedImage> = ArrayList() //解析したBufferedImageを加える
        val pImgList: ArrayList<PImage> = ArrayList() //BufferedImageをPImgListに変換
        val sqColList: ArrayList<SquareCollision> = ArrayList()
        val nextList: ArrayList<SquareCollision> = ArrayList()
        val backList: ArrayList<SquareCollision> = ArrayList()
        val bossList: ArrayList<SquareCollision> = ArrayList()
        val interactList: ArrayList<Interact> = ArrayList()
        val imgs: Array<PImage> //PImageの配列
        val factory = DocumentBuilderFactory.newInstance() // 1. DocumentBuilderFactoryのインスタンスを取得する
        try {
            val builder = factory.newDocumentBuilder() // 2. DocumentBuilderのインスタンスを取得する
            val document = builder.parse(inputStream) // 3. DocumentBuilderにXMLを読み込ませ、Documentを作る
            val element = document.documentElement // 4. Documentから、ルート要素(1village.tmx)を取得する
            val nodeList = element.childNodes // 5. map配下にある、子要素を取得する
            val sWidth = element.getAttribute("width") //map横幅
            val sHeight = element.getAttribute("height") //map縦幅
            val sTilewidth = element.getAttribute("tilewidth") //mapタイル横幅
            val sTileheight = element.getAttribute("tileheight") //mapタイル縦幅
            val next = element.getAttribute("nextMap")
            val previous = element.getAttribute("previousMap")
            //
            val nextXs = element.getAttribute("nextX")
            val nextYs = element.getAttribute("nextY")
            val previousXs = element.getAttribute("previousX")
            val previousYs = element.getAttribute("previousY")
            var mapTileWidth = 0
            var mapTileHeight = 0
            var tileWidth = 0
            var tileHeight = 0
            //
            var nextX = 0
            var nextY = 0
            var previousX = 0
            var previousY = 0

            //cast to int
            if (sWidth.isNotEmpty()) mapTileWidth = sWidth.toInt()
            if (sHeight.isNotEmpty()) mapTileHeight = sHeight.toInt()
            if (sTilewidth.isNotEmpty()) tileWidth = sTilewidth.toInt()
            if (sTileheight.isNotEmpty()) tileHeight = sTileheight.toInt()
            //
            if (nextXs.isNotEmpty()) nextX = nextXs.toInt()
            if (nextYs.isNotEmpty()) nextY = nextYs.toInt()
            if (previousXs.isNotEmpty()) previousX = previousXs.toInt()
            if (previousYs.isNotEmpty()) previousY = previousYs.toInt()
            for (i in 0 until nodeList.length) {
                val node = nodeList.item(i) //子要素i番目取得
                if (node.nodeType == Node.ELEMENT_NODE) { //Nodeのタイプを選別
                    val name = node as Element //cast to element
                    when (name.nodeName) {
                        "tileset" -> {
                            val sFirstgid = name.getAttribute("firstgid") //タイルの初期値
                            val source = name.getAttribute("source") //タイル画像パス
                            //cast to int
                            val firstgid = sFirstgid.toInt()
                            tsx = TsxLoader(source) //TSXLoaderクラスでsourceのTSXファイルを解析
                            listBufImg.addAll(tsx.listImg) //解析してsplitが完了したらlistBufImgに画像を加える
                        }
                        "layer" -> {
                            var nd = name.firstChild //layerノード内の最初のNodeを取得
                            val list: MutableList<Int> = ArrayList() //配列にするための仮list
                            var ints: Array<Int> //int配列(MapTemplateのlistへ入れるため)
                            while (nd != null) { //layerの中身がnullになるまで処理
                                if (nd.nodeName == "data") { //Nodeがlayerの中のdataの場合
                                    val str = nd.textContent //dataの中の文字列取得
                                    val nums = str.split(",".toRegex()).toTypedArray() //","ごとに区切り、String配列に区切られた文字ごと入れる
                                    for (num in nums) { //nums配列の中身を1つ取り出し、Stringのnumにいれて下を実行(nums回)
                                        val numStr = num.replace("\\s+".toRegex(), "") //改行やスペースを無に置き換え
                                        val n = numStr.toInt() //cast to int
                                        list.add(n) //listにlayer情報(整数)を加える
                                    }
                                    ints = list.toTypedArray() //加え終わったら配列に変換
                                    mapList.add(ints) //layer情報を保存
                                }
                                nd = nd.nextSibling //次のnodeを読み込む
                            }
                        }
                        "objectgroup" -> {
                            val str = name.getAttribute("name")
                            if (str.contains("Collision")) {
                                var nd1 = name.firstChild //layerノード内の最初のNodeを取得
                                while (nd1 != null) { //objectの中身がnullになるまで処理
                                    if (nd1.nodeName == "object") { //Nodeがlayerの中のdataの場合
                                        val el = nd1 as Element
                                        val objXs = el.getAttribute("x")
                                        val objYs = el.getAttribute("y")
                                        val objWidths = el.getAttribute("width")
                                        val objHeights = el.getAttribute("height")
                                        var objX = 0f
                                        var objY = 0f
                                        var objWidth = 0f
                                        var objHeight = 0f
                                        if (objXs.isNotEmpty()) objX = objXs.toFloat()
                                        if (objYs.isNotEmpty()) objY = objYs.toFloat()
                                        if (objWidths.isNotEmpty()) objWidth = objWidths.toFloat()
                                        if (objHeights.isNotEmpty()) objHeight = objHeights.toFloat()
                                        sqColList.add(SquareCollision(objX, objY, objWidth, objHeight, mapTileWidth * tileWidth - 10, mapTileHeight * tileHeight - 10))
                                    }
                                    nd1 = nd1.nextSibling //次のnodeを読み込む
                                }
                            }
                            if (str.contains("Next")) {
                                var nd1 = name.firstChild //layerノード内の最初のNodeを取得
                                while (nd1 != null) { //objectの中身がnullになるまで処理
                                    if (nd1.nodeName == "object") { //Nodeがlayerの中のdataの場合
                                        val el = nd1 as Element
                                        val objXs = el.getAttribute("x")
                                        val objYs = el.getAttribute("y")
                                        val objWidths = el.getAttribute("width")
                                        val objHeights = el.getAttribute("height")
                                        var objX = 0f
                                        var objY = 0f
                                        var objWidth = 0f
                                        var objHeight = 0f
                                        if (objXs.isNotEmpty()) objX = objXs.toFloat()
                                        if (objYs.isNotEmpty()) objY = objYs.toFloat()
                                        if (objWidths.isNotEmpty()) objWidth = objWidths.toFloat()
                                        if (objHeights.isNotEmpty()) objHeight = objHeights.toFloat()
                                        nextList.add(SquareCollision(objX, objY, objWidth, objHeight, mapTileWidth * tileWidth - 10, mapTileHeight * tileHeight - 10))
                                    }
                                    nd1 = nd1.nextSibling //次のnodeを読み込む
                                }
                            }
                            if (str.contains("Back")) {
                                var nd1 = name.firstChild //layerノード内の最初のNodeを取得
                                while (nd1 != null) { //objectの中身がnullになるまで処理
                                    if (nd1.nodeName == "object") { //Nodeがlayerの中のdataの場合
                                        val el = nd1 as Element
                                        val objXs = el.getAttribute("x")
                                        val objYs = el.getAttribute("y")
                                        val objWidths = el.getAttribute("width")
                                        val objHeights = el.getAttribute("height")
                                        var objX = 0f
                                        var objY = 0f
                                        var objWidth = 0f
                                        var objHeight = 0f
                                        if (objXs.isNotEmpty()) objX = objXs.toFloat()
                                        if (objYs.isNotEmpty()) objY = objYs.toFloat()
                                        if (objWidths.isNotEmpty()) objWidth = objWidths.toFloat()
                                        if (objHeights.isNotEmpty()) objHeight = objHeights.toFloat()
                                        backList.add(SquareCollision(objX, objY, objWidth, objHeight, mapTileWidth * tileWidth - 10, mapTileHeight * tileHeight - 10))
                                    }
                                    nd1 = nd1.nextSibling //次のnodeを読み込む
                                }
                            }
                            if (str.contains("Boss")) {
                                var nd1 = name.firstChild //layerノード内の最初のNodeを取得
                                while (nd1 != null) { //objectの中身がnullになるまで処理
                                    if (nd1.nodeName == "object") { //Nodeがlayerの中のdataの場合
                                        val el = nd1 as Element
                                        val objXs = el.getAttribute("x")
                                        val objYs = el.getAttribute("y")
                                        val objWidths = el.getAttribute("width")
                                        val objHeights = el.getAttribute("height")
                                        var objX = 0f
                                        var objY = 0f
                                        var objWidth = 0f
                                        var objHeight = 0f
                                        if (objXs.isNotEmpty()) objX = objXs.toFloat()
                                        if (objYs.isNotEmpty()) objY = objYs.toFloat()
                                        if (objWidths.isNotEmpty()) objWidth = objWidths.toFloat()
                                        if (objHeights.isNotEmpty()) objHeight = objHeights.toFloat()
//                                        bossList.add(MapTrigger(objX, objY, objWidth, objHeight, mapTileWidth * tileWidth - 10, mapTileHeight * tileHeight - 10))
                                    }
                                    nd1 = nd1.nextSibling //次のnodeを読み込む
                                }
                            }
                            if (str.contains("Interact")) {
                                var nd1 = name.firstChild //layerノード内の最初のNodeを取得
                                while (nd1 != null) { //objectの中身がnullになるまで処理
                                    if (nd1.nodeName == "object") { //Nodeがlayerの中のdataの場合
                                        val el = nd1 as Element
                                        val message = el.textContent
                                        val objXs = el.getAttribute("x")
                                        val objYs = el.getAttribute("y")
                                        val objWidths = el.getAttribute("width")
                                        val objHeights = el.getAttribute("height")
                                        val eventIds = el.getAttribute("eventId")
                                        val direction = el.getAttribute("direction")
                                        val interactName = el.getAttribute("name")
                                        val pxs = el.getAttribute("px")
                                        val pys = el.getAttribute("py")
                                        var objX = 0f
                                        var objY = 0f
                                        var objWidth = 0f
                                        var objHeight = 0f
                                        var px = 0f
                                        var py = 0f
                                        var eventId = 0
                                        if (objXs.isNotEmpty()) objX = objXs.toFloat()
                                        if (objYs.isNotEmpty()) objY = objYs.toFloat()
                                        if (objWidths.isNotEmpty()) objWidth = objWidths.toFloat()
                                        if (objHeights.isNotEmpty()) objHeight = objHeights.toFloat()
                                        if (pxs.isNotEmpty()) px = pxs.toFloat()
                                        if (pys.isNotEmpty()) py = pys.toFloat()
                                        if (eventIds.isNotEmpty()) eventId = eventIds.toInt()
                                        interactList.add(Interact(objX, objY, objWidth, objHeight, mapTileWidth * tileWidth - 10, mapTileHeight * tileHeight - 10, eventId, interactName, direction, message, px, py))
                                    }
                                    nd1 = nd1.nextSibling //次のnodeを読み込む
                                }
                            }
                        }
                    }
                }
            }
            for (bfi in listBufImg) { //BufferedImageをPImageに変換
                pImgList.add(PImage(bfi))
            }
            imgs = pImgList.toTypedArray() //PImageのlistを配列に変換
            val mapManager = Main.mapManager
            mapManager.register(mapName, MapTemplate(mapName, next, previous, nextX, nextY, previousX, previousY, mapTileWidth, mapTileHeight, tileWidth, tileHeight, mapList, sqColList, nextList, backList, /*bossList,*/ interactList, imgs)) //map情報を保存
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
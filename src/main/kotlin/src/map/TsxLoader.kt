package src.map

import java.awt.image.BufferedImage
import java.io.InputStream
import javax.imageio.ImageIO
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.collections.ArrayList

class TsxLoader(source: String) {
    var listImg: ArrayList<BufferedImage> = ArrayList() //splitした画像を一時的に保存しておく場所


    init{ //mapで用いる画像読み込み
        val factory = DocumentBuilderFactory.newInstance() //loadTmxと同様
        try {
            val path = this.javaClass.getResourceAsStream("/data/tsx/$source")
            println("path: $source, $path")
            val builder = factory.newDocumentBuilder()
            val doc = builder.parse(path)
            val element = doc.documentElement
            val name = element.getAttribute("name") //画像名入手
            val sTileWidth = element.getAttribute("tilewidth") //タイル画像の横幅
            val sTileHeight = element.getAttribute("tileheight") //タイル画像の縦幅

            //cast to int
            val tileWidth = sTileWidth.toInt()
            val tileHeight = sTileHeight.toInt()
            imageSplit(this.javaClass.getResourceAsStream("/data/img/mapChip/$name.png"), tileWidth, tileHeight)

//            NodeList nodeList = element.getElementsByTagName("image");
//            for (int i = 0; i < nodeList.getLength(); i++) {
//                Node node = nodeList.item(i);
//                if (node.getNodeType() == Node.ELEMENT_NODE) {
//                    Element element1 = (Element) node;
//                    String sWidth = element1.getAttribute("width");
//                    String sHeight = element1.getAttribute("height");
//                    //cast to int
//                    int width = Integer.parseInt(sWidth);
//                    int height = Integer.parseInt(sHeight);
//                }
//            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun imageSplit(inputStream: InputStream, chunkWidth: Int, chunkHeight: Int) { //画像を指定px*pxで小分けする
        try {
            val image = ImageIO.read(inputStream) //↑ファイルをBufferedImageとして読み込み
            val cols = image.width / chunkWidth //横幅
            val rows = image.height / chunkHeight //縦幅
            val chunks = cols * rows //総数
            var count = 0
            var a = 0
            val imgs = arrayOfNulls<BufferedImage>(chunks)
            for (x in 0 until rows) {
                for (y in 0 until cols) {
                    //Initialize the image array with image chunks
                    imgs[count] = BufferedImage(chunkWidth, chunkHeight, image.type)

                    // draws the image chunk//TODO あまり理解できていない部分
                    val gr = imgs[count++]!!.createGraphics()
                    gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null)
                    gr.dispose()
                    listImg.add(imgs[a]!!)
                    a++
                }
            }
            //            System.out.println("Splitting done");
//            for (int i = 0; i < MapTemplate.listImg.size(); i++) {
//                ImageIO.write(MapTemplate.listImg.get(i), "png", new File("E:\\waichi\\Desktop\\test\\test\\" + i + ".png"));
//            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
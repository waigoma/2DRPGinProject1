package src.api

import processing.core.PImage
import java.awt.image.BufferedImage
import java.io.InputStream
import javax.imageio.ImageIO

class InputStreamToPImage {
    fun isToPImage(inputStream: InputStream): PImage {
        val image = ImageIO.read(inputStream)
        val bfi = BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB)
        val gr = bfi.createGraphics()
        gr.drawImage(image, 0, 0, null)
        gr.dispose()
        return PImage(bfi)
    }
}
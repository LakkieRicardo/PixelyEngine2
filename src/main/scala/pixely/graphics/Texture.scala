package pixely.graphics

import java.io.File
import java.io.InputStream
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import pixely.math.Vector2i

class Texture(var width: Int, var height: Int, var pixels: Array[Int]) {
    
    def getSizeAsVector2i(): Vector2i = new Vector2i(width, height)

    def clear(argbColor: Int) {
        pixels = Array.fill[Int](width * height)(argbColor)
    }

    def sample(x: Int, y: Int): Int = pixels(x + y * width)

}

object Texture {

    def fromImage(image: BufferedImage): Texture = {
        return new Texture(image.getWidth(), image.getHeight(), image.getRGB(0, 0, image.getWidth(), image.getHeight(), new Array[Int](image.getWidth() * image.getHeight()), 0, image.getWidth()))
    }

    def fromFile(file: File): Texture = fromImage(ImageIO.read(file))

    def fromFileLocation(location: String): Texture = fromFile(new File(location))

    def fromInputStream(input: InputStream): Texture = fromImage(ImageIO.read(input))

    def solid(argbColor: Int, width: Int, height: Int): Texture = {
        val pixels = Array.fill(width * height){0}
        for (index <- 0 until width * height) pixels(index) = argbColor
        return new Texture(width, height, pixels)
    }

    def extract(src: Texture, srcPos: Vector2i, size: Vector2i): Texture = {
        if (srcPos.x < 0 || srcPos.y < 0 || srcPos.x + size.x >= src.width || srcPos.y + size.y >= src.height) {
            return null
        } else {
            val target = new Texture(size.x, size.y, Array.fill(size.x * size.y){0})
            var targetY = 0
            while (targetY < size.y) {
                var targetX = 0
                while (targetX < size.x) {
                    target.pixels(targetX + targetY * target.width) = src.pixels((targetX + srcPos.x) + (targetY + srcPos.y) * src.width)
                    targetX += 1
                }
                targetY += 1
            }
            return target
        }
    }

}
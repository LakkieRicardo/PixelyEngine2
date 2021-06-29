package pixely.graphics

import java.awt.Color

// This is the object where I store away all the features that I regret using Scala to write
object Renderer {

    def checkOutOfBounds(target: Texture, x: Int, y: Int): Boolean = {
        return x < 0 || y < 0 || x >= target.width || y >= target.height
    }

    def renderRectangle(target: Texture, x: Int, y: Int, width: Int, height: Int, argbColor: Int) {
        var yPos = 0
        while (yPos < height) {
            var xPos = 0
            while (xPos < width) {
                val targetX = xPos + x
                val targetY = yPos + y
                if (!checkOutOfBounds(target, targetX, targetY)) target.pixels(targetX + targetY * target.width) = argbColor;
                xPos += 1
            }
            yPos += 1
        }
    }

    def renderTexture(target: Texture, xOffset: Int, yOffset: Int, src: Texture) {
        var yPos = 0
        while (yPos < src.height) {
            var xPos = 0
            while (xPos < src.width) {
                val x = xPos + xOffset
                val y = yPos + yOffset
                if (!checkOutOfBounds(target, x, y)) target.pixels(x + y * target.width) = src.sample(xPos, yPos)
                xPos += 1
            }
            yPos += 1
        }
    }

    def renderTextureDepthLighting(target: Texture, targetDepthMap: Texture, lightingMap: Texture, xOffset: Int, yOffset: Int, zOffset: Int, src: Texture) {
        var yPos = 0
        while (yPos < src.height) {
            var xPos = 0
            while (xPos < src.width) {
                val x = xPos + xOffset
                val y = yPos + yOffset
                if (!checkOutOfBounds(target, x, y) && targetDepthMap.sample(x, y) < zOffset) {
                    targetDepthMap.pixels(x + y * target.width) = zOffset;
                    target.pixels(x + y * target.width) = src.pixels(xPos + yPos * src.width)
                }
                xPos += 1
            }
            yPos += 1
        }
    }

    /**
      * Converts a graphics map into something that can be rendered in place of the screen. Calling this function will not modify the
      * original texture.
      * 
      * The furthest back on the Z index will be rendered as black, and the furthest forward will be rendered as white.
      * However, this can be masked using the `colorMask` argument, for example, using 0xffff0000 will produce a black to red mask.
      * 
      * @param graphicsMap Normal graphics map
      * @param colorMask The color to draw the graphics map in. For example, to draw a red graphics map use: 0xffff0000 (ARGB) 
      * @param minColor Minimum color to distinguish between pixels furthest back and empty pixels
      * @return Renderable graphics map
      */
    def translateGraphicsMapToScreen(graphicsMap: Texture, colorMask: Int = 0xffffffff, minColor: Int = 0x10): Texture = { 
        val target = new Texture(graphicsMap.width, graphicsMap.height, new Array[Int](graphicsMap.pixels.length))
        
        // First pass, find max and min values excluding untouched areas
        var minValue = Int.MaxValue
        var maxValue = Int.MinValue
        var counter = 0
        while (counter < graphicsMap.pixels.length) {
            val sample = graphicsMap.pixels(counter)
            if (sample != Int.MinValue) {
                if (sample < minValue) minValue = sample
                if (sample > maxValue) maxValue = sample
            }
            counter += 1
        }
        val minValueF = minValue.asInstanceOf[Float]
        val maxValueF = maxValue.asInstanceOf[Float]

        // Second pass, map each pixel between 0-255 between min and max values
        var yPos = 0
        while (yPos < graphicsMap.height) {
            var xPos = 0
            while (xPos < graphicsMap.width) {
                val depthMapSample = graphicsMap.sample(xPos, yPos).asInstanceOf[Float]
                // Areas of the screen the renderer did not touch are equal to Int.MinValue
                if (depthMapSample != Int.MinValue) {
                    if (minValue == maxValue) {
                        target.pixels(xPos + yPos * target.width) = colorMask
                    } else {
                        val percentProgress = (depthMapSample - minValueF) / (maxValueF - minValueF)
                        // Make the pixels furthest back able to be seen
                        val scaledProgress = (percentProgress * ((0xff - minColor).asInstanceOf[Float])).asInstanceOf[Int] + (minColor)
                        val argbColor = (0xff000000 | scaledProgress << 16 | scaledProgress << 8 | scaledProgress) & colorMask
                        target.pixels(xPos + yPos * target.width) = argbColor
                    }
                } else {
                    target.pixels(xPos + yPos * target.width) = 0xff000000
                }
                xPos += 1
            }
            yPos += 1
        }

        return target
    }

    def translateLightingMapToScreen(lightingMap: Texture, colorMask: Int = 0xffffffff): Texture = {
        val result = new Texture(lightingMap.width, lightingMap.height, new Array[Int](lightingMap.pixels.length))
        var yPos = 0
        while (yPos < lightingMap.height) {
            var xPos = 0
            while (xPos < lightingMap.width) {
                val pixel = lightingMap.sample(xPos, yPos)
                result.pixels(xPos + yPos * result.width) = (0xff000000 | pixel << 16 | pixel << 8 | pixel) & colorMask
                xPos += 1
            }
            yPos += 1
        }
        return result
    }

}
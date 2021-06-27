package pixely.graphics

import pixely.math.Vector2i
import spray.json._
import DefaultJsonProtocol._
import scala.collection.mutable.ArrayBuffer

class TextureSheet(textureSheet: Texture, gridWidth: Int, gridHeight: Int) {

    val sheetWidth = textureSheet.width / gridWidth
    val sheetHeight = textureSheet.height / gridHeight
    val loadedTextures = Array.fill[Texture](sheetWidth * sheetHeight){null}

    {
        var posY = 0
        while (posY < sheetHeight) {
            var posX = 0
            while (posX < sheetWidth) {
                loadedTextures(posX + posY * sheetWidth) = Texture.extract(textureSheet, new Vector2i(posX * gridWidth, posY * gridHeight), new Vector2i(gridWidth, gridHeight))
                posX += 1
            }
            posY += 1
        }
    }

    def access(x: Int, y: Int): Texture = {
        return loadedTextures(x + y * sheetWidth)
    }

}

object TextureSheet {

    /**
      * Assembles a tile-based world represented as an array buffer of sprites using a mapData JsObject
      *
      * @param mapData The information about the map. This should include `mapKey` which is the file location of the map, `mapTextures` which is the file
      * location of the `TextureSheet` of all of the map's textures, and `colorMap` which defines which ARGB hex color represents which sprite, which is specified
      * with a 2-element integer array, the first being X and the second being Y(starting from 0)
      * @param zIndex The default zIndex to instantiate the sprites to
      * @param mapStartPosition The starting position the map sprites will be created in
      * @return The sprites making up the map
      */
    def assembleMap(mapData: JsObject, zIndex: Int = -10, mapStartPosition: Vector2i = new Vector2i(0)): ArrayBuffer[Sprite] = {
        val mapKey = Texture.fromFileLocation(mapData.fields("mapKey").convertTo[String])
        val mapTextures = Texture.fromFileLocation(mapData.fields("mapTextures").convertTo[String])
        val mapTextureSizes = mapData.fields("mapTextureSize").convertTo[List[Int]]
        val mapTextureSheet = new TextureSheet(mapTextures, mapTextureSizes(0), mapTextureSizes(1))
        val colorMap = mapData.fields("colorMap").asJsObject

        val result = ArrayBuffer[Sprite]()
        var yPos = 0
        while (yPos < mapKey.height) {
            var xPos = 0
            while (xPos < mapKey.width) {
                val currentKeyPixel = mapKey.pixels(xPos + yPos * mapKey.width)
                // Pixel must be visible
                if (0xff000000.&(currentKeyPixel) == 0xff000000) {
                    val textureCoords = colorMap.fields("%x".format(currentKeyPixel)).convertTo[Array[Int]]
                    val texture = mapTextureSheet.access(textureCoords(0), textureCoords(1))
                    val sprite = new Sprite(mapStartPosition + new Vector2i(xPos * mapTextureSizes(0), yPos * mapTextureSizes(1)), zIndex, texture)
                    result.addOne(sprite)
                }
                xPos += 1
            }
            yPos += 1
        }

        return result
    }

}
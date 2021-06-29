package pixely.graphics.lighting

import scala.collection.mutable.ArrayBuffer
import pixely.graphics.Texture
import pixely.PEApp
import pixely.math.Vector2i

object Lighting {

    def calculateLighting(lightSources: ArrayBuffer[LightSource], lightingTexture: Texture, app: PEApp, offset: Vector2i = new Vector2i(0)) {
        var yPos = 0
        while (yPos < lightingTexture.height) {
            var xPos = 0
            while (xPos < lightingTexture.width) {
                var lightSourceIndex = 0
                var lightIntensity = 0
                while (lightSourceIndex < lightSources.length) {
                    lightIntensity += lightSources(lightSourceIndex).getLightIntensity(new Vector2i(xPos, yPos) + offset)
                    lightSourceIndex += 1
                }
                lightIntensity = Math.min(lightIntensity, 255)
                lightIntensity = Math.max(lightIntensity, 0)
                lightingTexture.pixels(xPos + yPos * lightingTexture.width) = lightIntensity
                xPos += 1
            }
            yPos += 1
        }
    }

}
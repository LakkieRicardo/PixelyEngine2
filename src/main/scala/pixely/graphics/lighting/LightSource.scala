package pixely.graphics.lighting

import pixely.math.Vector2i

abstract class LightSource {

    def getLightIntensity(point: Vector2i): Int
    
}
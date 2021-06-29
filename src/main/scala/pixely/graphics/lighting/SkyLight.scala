package pixely.graphics.lighting

import pixely.math.Vector2i

class SkyLight(lightLevel: Double = 0.2D) extends LightSource {

  override def getLightIntensity(point: Vector2i): Int = {
    return (lightLevel * 255.0D).asInstanceOf[Int]
  }
    
}
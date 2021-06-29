package pixely.graphics.lighting

import pixely.math.Vector2i
import pixely.math.Vector2f

class PointLight(var position: Vector2i, intensityMultiplier: Double = 0.1D) extends LightSource {

  override def getLightIntensity(point: Vector2i): Int = {
    val distance = Vector2f.distance(position.toVector2f(), point.toVector2f()).asInstanceOf[Double]
    val brightness = Math.pow(2, -distance * intensityMultiplier) // double from 0 to 1
    return (brightness * 255.0D).asInstanceOf[Int]
  }

}
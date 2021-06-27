package pixely.graphics

import pixely.math.Vector2i

class Camera(var centerX: Int, var centerY: Int) {

    def this() {
        this(0, 0)
    }

    def translate(offset: Vector2i) {
        centerX += offset.x;
        centerY += offset.y;
    }

    def getPosition(): Vector2i = new Vector2i(this.centerX, this.centerY)

    def transformWorldToScreen(screen: Texture, worldPos: Vector2i): Vector2i = {
        val screenCenterPos = new Vector2i(screen.width / 2, screen.height / 2)
        return screenCenterPos + worldPos - getPosition()
    }

}
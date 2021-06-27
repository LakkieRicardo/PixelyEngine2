package pixely.game

import scala.collection.mutable.ArrayBuffer
import pixely.graphics.Sprite
import pixely.graphics.Camera
import pixely.PEApp

class GameScene(app: PEApp) {

    val sprites = new ArrayBuffer[Sprite]
    val camera = new Camera

    def update() {
        sprites.foreach((sprite) => sprite.update(app.deltaTimeNano.asInstanceOf[Float] / 1000000000f, app))
    }

    /**
     * NOTE: This does not request a repaint on the AppFrame, and does not clear the screen. This only draws all the sprites
      */
    final def renderSprites() {
        sprites.foreach((sprite) => {
            sprite.render(app.screen, app.depthMap, camera)
        })
    }

    final def requestRepaint() = app.frame.repaint()

}
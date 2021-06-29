package pixely.test

import pixely.PEApp
import pixely.graphics.Sprite
import java.awt.Color
import java.awt.event.KeyEvent
import pixely.math.Vector2i
import pixely.graphics.Texture
import pixely.graphics.Camera
import pixely.game.GameScene
import pixely.graphics.TextureSheet
import pixely.util.FileUtil

import spray.json._
import DefaultJsonProtocol._

class TestScene(app: PEApp) extends GameScene(app) {

    val mapDataText = FileUtil.readFileText("GameRes/MapData.json")
    val mapData = mapDataText.parseJson.asJsObject
    val map = TextureSheet.assembleMap(mapData)
    sprites.addAll(map)

    override def update() {
        super.update()

        // Add camera movement
        val speed = 4
        var change = new Vector2i(0)
        if (app.activeKeys(KeyEvent.VK_W)) change += new Vector2i(0, -1)
        if (app.activeKeys(KeyEvent.VK_S)) change += new Vector2i(0, 1)
        if (app.activeKeys(KeyEvent.VK_A)) change += new Vector2i(-1, 0)
        if (app.activeKeys(KeyEvent.VK_D)) change += new Vector2i(1, 0)
        app.camera.translate(change * speed)
    }

}

object TestMain {

    def main(args: Array[String]) {
        val app = new PEApp("Test Game")
        java.awt.EventQueue.invokeLater(() => {
            app.activeScene = new TestScene(app)
            app.start()
        })
    }

}
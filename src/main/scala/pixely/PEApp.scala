package pixely

import java.awt.Color
import java.awt.image.BufferedImage
import pixely.graphics.Texture
import pixely.graphics.Renderer
import java.io.File
import pixely.game.GameScene
import pixely.graphics.Sprite
import pixely.math.Vector2i
import scala.collection.mutable.ArrayBuffer
import java.awt.event.KeyListener
import java.awt.event.KeyEvent
import pixely.debug.DebugFrame
import pixely.graphics.DisplayLayer

class PEApp(title: String, screenWidth: Int = 400, screenHeight: Int = 200, clearColor: Int = 0xff000000) extends Thread with KeyListener {

    val frame = new AppFrame(title, 1280, 720)
    val screen = new Texture(screenWidth, screenHeight, new Array[Int](screenWidth * screenHeight))
    val image = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB)
    val depthMap = Texture.solid(Int.MinValue, screenWidth, screenHeight)
    var updatesPerSecond: Long = 60
    var gameRunning = true
    var deltaTimeNano = 0L
    var gameLoopThread = new Thread(this, "Game loop")

    var activeScene = new GameScene(this)
    var activeLayer = DisplayLayer.Display

    /**
      * Map of all currently active keys, index by VK codes specified in `java.awt.event.KeyEvent`
      */
    val activeKeys = new Array[Boolean](65536)
    frame.addKeyListener(this)
    frame.canvas.addKeyListener(this)

    def getScreenImage(): BufferedImage = {
        // Clear depth map
        depthMap.clear(Int.MinValue)
        screen.clear(clearColor)
        activeScene.update()
        activeScene.renderSprites()
        if (activeLayer == DisplayLayer.DepthMap) {
            val renderableDepthMap = Renderer.translateDepthMapToScreen(depthMap)
            image.setRGB(0, 0, screenWidth, screenHeight, renderableDepthMap.pixels, 0, screenWidth)
        }
        if (activeLayer == DisplayLayer.Display) {
            image.setRGB(0, 0, screenWidth, screenHeight, screen.pixels, 0, screenWidth)
        }
        return image
    }

    override def run() {
        frame.setVisible(true)
        var lastTime = System.nanoTime()
        val secondNano = 1000000000L
        var framesPerSecond = 0
        var lastFPSUpdate = System.nanoTime()
        while (gameRunning) {
            val currentTime = System.nanoTime()
            if (currentTime - lastTime > secondNano / updatesPerSecond) {
                deltaTimeNano = currentTime - lastTime
                lastTime = currentTime
                frame.drawScreen(getScreenImage())
                framesPerSecond += 1
                if (currentTime - lastFPSUpdate > secondNano) {
                    lastFPSUpdate = currentTime
                    frame.setTitle("%s, FPS: %s".format(title, framesPerSecond))
                    framesPerSecond = 0
                }
            }
        }
    }

    override def keyTyped(keyEvent: KeyEvent) { }

    override def keyPressed(keyEvent: KeyEvent) {
        activeKeys(keyEvent.getKeyCode()) = true;
        if (keyEvent.getKeyCode() == KeyEvent.VK_F9) DebugFrame.createInstance(this)
    }

    override def keyReleased(keyEvent: KeyEvent) {
        activeKeys(keyEvent.getKeyCode()) = false;
    }

}
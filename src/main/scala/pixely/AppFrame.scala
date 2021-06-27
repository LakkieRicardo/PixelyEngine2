package pixely

import javax.swing.JFrame
import java.awt.Graphics
import java.awt.image.BufferStrategy
import java.awt.Color
import java.awt.image.BufferedImage
import java.awt.Canvas
import pixely.graphics.Texture

class AppFrame(title: String, width: Int, height: Int) extends JFrame (title) {

    setSize(width, height)
    setLocationRelativeTo(null)
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    val canvas = new Canvas
    add(canvas)
    var bs = canvas.getBufferStrategy()

    def drawScreen(screen: BufferedImage) {
        if (bs == null) {
            canvas.createBufferStrategy(3)
            bs = canvas.getBufferStrategy()
        }
        val g = bs.getDrawGraphics()

        g.drawImage(screen, 0, 0, canvas.getWidth(), canvas.getHeight(), null)

        g.dispose()
        bs.show()
    }

}

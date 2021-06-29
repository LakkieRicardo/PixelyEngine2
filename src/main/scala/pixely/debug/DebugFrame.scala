package pixely.debug

import javax.swing.JFrame
import java.awt.GridBagLayout
import java.awt.GridBagConstraints
import javax.swing.JTextPane
import javax.swing.JLabel
import java.awt.Insets
import javax.swing.WindowConstants
import java.awt.event.WindowListener
import java.awt.event.WindowEvent
import javax.swing.UIManager
import java.awt.Dimension
import javax.swing.JRadioButton
import javax.swing.ButtonGroup
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import pixely.PEApp
import pixely.graphics.DisplayLayer

class DebugFrame(app: PEApp) extends JFrame("Debug") with WindowListener with ActionListener {

    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    setLayout(new GridBagLayout)
    val c = new GridBagConstraints
    val titleText = new JLabel("Active Texture")
    c.gridx = 0
    c.gridy = 0
    c.fill = GridBagConstraints.BOTH
    c.insets = new Insets(15, 15, 15, 15)
    c.anchor = GridBagConstraints.CENTER
    add(titleText, c)
    var layerButtonGroup = new ButtonGroup
    var layerButtonDisplay = new JRadioButton("Display")
    layerButtonDisplay.setActionCommand("layer.display")
    layerButtonGroup.add(layerButtonDisplay)
    c.gridx = 0
    c.gridy = 1
    add(layerButtonDisplay, c)

    var layerButtonDepthMap = new JRadioButton("Depth Map")
    layerButtonDepthMap.setActionCommand("layer.depthmap")
    layerButtonGroup.add(layerButtonDepthMap)
    c.gridx = 0
    c.gridy = 2
    add(layerButtonDepthMap, c)

    var layerButtonLighting = new JRadioButton("Lighting")
    layerButtonLighting.setActionCommand("layer.lighting")
    layerButtonGroup.add(layerButtonLighting)
    c.gridx = 0
    c.gridy = 3
    add(layerButtonLighting, c)

    setSize(new Dimension(300, 300))
    setLocationRelativeTo(null)
    addWindowListener(this)
    layerButtonDisplay.addActionListener(this)
    layerButtonDepthMap.addActionListener(this)
    layerButtonLighting.addActionListener(this)
    setVisible(true)
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
    layerButtonGroup.setSelected(layerButtonDisplay.getModel(), true)

    override def windowOpened(x$1: WindowEvent) { }

    override def windowClosing(x$1: WindowEvent) {
        DebugFrame.instance = null
    }

    override def windowClosed(e: WindowEvent) { }

    override def windowIconified(e: WindowEvent) { }

    override def windowDeiconified(e: WindowEvent) { }

    override def windowActivated(e: WindowEvent) { }

    override def windowDeactivated(e: WindowEvent) { }

    override def actionPerformed(e: ActionEvent) {
        if (e.getActionCommand().equals("layer.display")) {
            app.activeLayer = DisplayLayer.Display
        }
        if (e.getActionCommand().equals("layer.depthmap")) {
            app.activeLayer = DisplayLayer.DepthMap
        }
        if (e.getActionCommand().equals("layer.lighting")) {
            app.activeLayer = DisplayLayer.Lighting
        }
    }

}

object DebugFrame {

    var instance: DebugFrame = null

    def createInstance(app: PEApp): Boolean = {
        if (instance == null) {
            instance = new DebugFrame(app)
            return true
        } else {
            return false
        }
    }

    /**
      * If not null, modifies the debug frame to select the active layer
      *
      * @param app
      */
    def updateActiveLayer(app: PEApp) {
        if (instance == null) return
        app.activeLayer match {
            case DisplayLayer.Display => instance.layerButtonGroup.setSelected(instance.layerButtonDisplay.getModel(), true)
            case DisplayLayer.DepthMap => instance.layerButtonGroup.setSelected(instance.layerButtonDepthMap.getModel(), true)
            case DisplayLayer.Lighting => instance.layerButtonGroup.setSelected(instance.layerButtonLighting.getModel(), true)
        }
    }

}
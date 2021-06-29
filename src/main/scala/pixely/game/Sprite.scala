package pixely.graphics

import pixely.math.Vector2i
import pixely.PEApp

class Sprite(var position: Vector2i, var zIndex: Int = 0, var texture: Texture) {

    def update(deltaTimeSeconds: Float, app: PEApp) { }

    def move(offset: Vector2i) {
        position += offset;
    }

    def render(screen: Texture, depthMap: Texture, lightingMap: Texture, camera: Camera) {
        val screenPos = camera.transformWorldToScreen(screen, position)
        // Don't render if not in screen
        if (screenPos.x + texture.width < 0 || screenPos.y + texture.height < 0 || screenPos.x >= screen.width || screenPos.y >= screen.height) return
        Renderer.renderTextureDepthLighting(screen, depthMap, lightingMap, screenPos.x, screenPos.y, zIndex, texture)
    }

}
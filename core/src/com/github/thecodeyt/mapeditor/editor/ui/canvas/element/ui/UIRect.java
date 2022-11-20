package com.github.thecodeyt.mapeditor.editor.ui.canvas.element.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.ui.camera.CanvasCamera;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.UICanvas;
import com.github.thecodeyt.mapeditor.math.geometry.MultiRectHitbox;
import com.github.thecodeyt.mapeditor.math.render.Drawf;
import lombok.Getter;
import lombok.Setter;

public class UIRect extends UIElement {
    @Getter @Setter private Color color;

    public UIRect(UICanvas canvas, Vector2 position, Vector2 size, Color color) {
        super(canvas, position, size);
        this.color = color;
    }

    @Override
    public MultiRectHitbox getHitBox() {
        return super.getHitBox();
    }

    @Override
    public void draw() {
        draw(color);
    }
    public void draw(Color color) {
        CanvasCamera<UICanvas> camera = getCanvas().getCamera();
        if(!Drawf.isPresent(camera.shapeRenderer)) {
            return;
        }

        camera.shapeRenderer.setColor(color);
        camera.shapeRenderer.rect(position.x, position.y, size.x, size.y);
        camera.shapeRenderer.flush();
    }
}

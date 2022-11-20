package com.github.thecodeyt.mapeditor.editor.ui.canvas.element.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.ui.camera.CanvasCamera;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.UICanvas;
import com.github.thecodeyt.mapeditor.math.render.Drawf;
import com.github.thecodeyt.mapeditor.math.render.Mask;
import lombok.Getter;

public class UIPanel extends UIRect {
    @Getter private UICanvas panelCanvas;

    public UIPanel(UICanvas canvas, Vector2 position, Vector2 size, Color color) {
        super(canvas, position, size, color);
        this.panelCanvas = new UICanvas(canvas.getCamera());
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        getPanelCanvas().update(delta);
    }

    @Override
    public void draw() {
        super.draw();
        CanvasCamera<UICanvas> camera = getCanvas().getCamera();

        for(UIElement element : getPanelCanvas().getElements()) {
            Mask finalMask = getMask();
            Drawf.addMaskLayer(camera, finalMask);

            element.draw();

            Drawf.removeLastMaskLayer(camera);
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        for(UIElement element : getPanelCanvas().getElements()) {
            element.resize(width, height);
        }
    }

    public Mask getMask() {
        Mask mask = new Mask();
        mask.setP0(position.cpy());
        mask.setP1(position.cpy().add(size));

        return mask;
    }
}

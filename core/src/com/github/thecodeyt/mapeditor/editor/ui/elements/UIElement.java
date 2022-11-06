package com.github.thecodeyt.mapeditor.editor.ui.elements;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.thecodeyt.mapeditor.editor.ui.UI;

public abstract class UIElement {
    public UI ui;
    public UIElement(UI ui) {
        this.ui = ui;
    }

    protected boolean isPresent(SpriteBatch spriteBatch) {
        return spriteBatch.isDrawing();
    }
    protected boolean isPresent(ShapeRenderer shapeRenderer) {
        return shapeRenderer.isDrawing();
    }

    public abstract void draw();
    public void update(float delta) {

    }
}

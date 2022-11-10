package com.github.thecodeyt.mapeditor.editor.ui.elements;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.ui.UI;
import com.github.thecodeyt.mapeditor.math.HitBox;

public abstract class UIElement {
    public UIElement parent;
    public UI ui;
    public Vector2 position;
    public Vector2 size;
    public UIElement(UI ui, Vector2 position, Vector2 size) {
        this.ui = ui;
        this.position = position;
        this.size = size;
    }

    protected boolean isPresent(SpriteBatch spriteBatch) {
        return spriteBatch.isDrawing();
    }
    protected boolean isPresent(ShapeRenderer shapeRenderer) {
        return shapeRenderer.isDrawing();
    }

    public HitBox getHitBox() {
        return null;
    }

    public abstract void draw();
    public void update(float delta) {

    }
}

package com.github.thecodeyt.mapeditor.editor.ui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.ui.UI;
import com.github.thecodeyt.mapeditor.math.HitBox;

public class UIRect extends UIElement {
    public Color color;

    public UIRect(UI ui, Vector2 position, Vector2 size, Color color) {
        super(ui, position, size);
        this.color = color;
    }

    @Override
    public HitBox getHitBox() {
        return new HitBox(position, size);
    }

    @Override
    public void draw() {
        draw(color);
    }
    public void draw(Color color) {
        if(!isPresent(this.ui.camera.shapeRenderer)) {
            return;
        }

        this.ui.camera.shapeRenderer.setColor(color);
        this.ui.camera.shapeRenderer.rect(position.x, position.y, size.x, size.y);
    }
}

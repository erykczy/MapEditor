package com.github.thecodeyt.mapeditor.editor.ui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.ui.UI;

import java.util.ArrayList;
import java.util.List;

public class Panel extends Rect {
    public List<UIElement> elements = new ArrayList<>();

    public Panel(UI ui, Vector2 position, Vector2 size, Color color) {
        super(ui, position, size, color);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        for(UIElement element : elements) {
            element.update(delta);
        }
    }

    @Override
    public void draw() {
        super.draw();
        for(UIElement element : elements) {
            element.draw();
        }
    }
}

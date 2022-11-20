package com.github.thecodeyt.mapeditor.editor.ui.canvas.element.ui;

import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.UICanvas;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.element.CanvasElement;
import com.github.thecodeyt.mapeditor.math.geometry.MultiRectHitbox;
import com.github.thecodeyt.mapeditor.math.geometry.RectHitBox;
import lombok.Getter;
import lombok.Setter;

public abstract class UIElement extends CanvasElement<UICanvas> {
    @Getter @Setter protected Vector2 position;
    @Getter @Setter protected Vector2 size;
    private boolean created = false;
    public UIElement(UICanvas canvas, Vector2 position, Vector2 size) {
        super(canvas);
        this.position = position;
        this.size = size;
    }
    protected void create() {

    }

    public MultiRectHitbox getHitBox() {
        return new MultiRectHitbox(new RectHitBox(position, size));
    }

    public void update(float delta) {
        if(!created) {
            created = true;
            create();
        }
    }
}

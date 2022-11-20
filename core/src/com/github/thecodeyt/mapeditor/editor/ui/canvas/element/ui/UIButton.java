package com.github.thecodeyt.mapeditor.editor.ui.canvas.element.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.UICanvas;
import com.github.thecodeyt.mapeditor.math.input.Inputf;
import lombok.Getter;

public class UIButton extends UIPanel {

    @Getter private Color defaultColor;
    @Getter private Color hoverColor;
    @Getter private boolean hover = false;
    @Getter private Runnable onClick;
    @Getter private UIText text;

    public UIButton(UICanvas canvas, Vector2 position, Vector2 size, Color color, Color hoverColor, Color textColor, String text, Vector2 textMargin, Runnable onClick) {
        super(canvas, position, size, color);
        this.defaultColor = color;
        this.hoverColor = hoverColor;

        this.text = new UIText(getCanvas(), position.cpy().add(textMargin.x, -textMargin.y), (int)size.y, textColor, text);
        this.onClick = onClick;

        getCanvas().addElement(this.text);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        Vector2 pointerPosition = Inputf.getPointerPosition(getCanvas().getCamera().viewport);

        if(getHitBox().isPointColliding(pointerPosition)) {
            hover = true;
            if(Gdx.input.isButtonJustPressed(0)) {
                onClick.run();
            }
        }
        else {
            hover = false;
        }
    }

    @Override
    public void draw() {
        setColor(hover ? hoverColor : this.defaultColor);

        super.draw();
    }
}

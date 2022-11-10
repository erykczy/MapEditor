package com.github.thecodeyt.mapeditor.editor.ui.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.ui.UI;
import com.github.thecodeyt.mapeditor.math.HitBox;
import com.github.thecodeyt.mapeditor.math.input.Inputf;

public class UIButton extends UIPanel {
    public Color defaultColor;
    public Color hoverColor;
    public boolean hover = false;
    public Runnable onClick;
    public UIText text;

    public UIButton(UI ui, Vector2 position, Vector2 size, Color color, Color hoverColor, Color textColor, String text, Runnable onClick) {
        super(ui, position, size, color);
        this.defaultColor = color;
        this.hoverColor = hoverColor;

        this.text = new UIText(ui, position, (int)size.y, textColor, text);
        this.onClick = onClick;

        addElement(this.text);
    }
    public UIButton(UI ui, Vector2 position, Vector2 size, Color color, Color hoverColor, Color textColor, String text, int textSize, Runnable onClick) {
        super(ui, position, size, color);
        this.hoverColor = hoverColor;

        Vector2 textPos = position.cpy();
        textPos.y -= (size.y - textSize)/2F;
        this.text = new UIText(ui, textPos, textSize, textColor, text);

        this.onClick = onClick;

        addElement(this.text);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        Vector2 pointerPosition = Inputf.getPointerPosition(this.ui.camera.viewport);

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

    public HitBox getHitBox() {
        return new HitBox(this.position, this.size);
    }

    @Override
    public void draw() {
        this.color = this.hover ? this.hoverColor : this.defaultColor;

        super.draw();
    }
}

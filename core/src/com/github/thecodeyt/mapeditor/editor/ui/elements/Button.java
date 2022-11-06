package com.github.thecodeyt.mapeditor.editor.ui.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.font.Fonts;
import com.github.thecodeyt.mapeditor.editor.ui.UI;
import com.github.thecodeyt.mapeditor.math.HitBox;
import com.github.thecodeyt.mapeditor.math.Inputf;

public class Button extends Rect {
    public Color hoverColor;
    public Color textColor;
    public boolean hover = false;
    public String text;
    public BitmapFont font;
    public Runnable onClick;

    public Button(UI ui, Vector2 position, Vector2 size, Color color, Color hoverColor, Color textColor, String text, Runnable onClick) {
        super(ui, position, size, color);
        this.hoverColor = hoverColor;
        this.text = text;
        this.textColor = textColor;
        this.onClick = onClick;

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)size.y;
        parameter.color = this.textColor;
        this.font = Fonts.PIXEL.generateFont(parameter);
    }
    public Button(UI ui, Vector2 position, Vector2 size, Color color, Color hoverColor, Color textColor, String text, int textSize, Runnable onClick) {
        super(ui, position, size, color);
        this.hoverColor = hoverColor;
        this.text = text;
        this.textColor = textColor;
        this.onClick = onClick;

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = textSize;
        parameter.color = this.textColor;
        this.font = Fonts.PIXEL.generateFont(parameter);
    }

    @Override
    public void update(float delta) {
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
        if(this.hover) {
            super.draw(this.color.cpy().add(hoverColor));
        }
        else {
            super.draw(this.color);
        }
        if(isPresent(this.ui.camera.spriteBatch)) {
            font.draw(this.ui.camera.spriteBatch, text, position.x, position.y+size.y);
        }
    }
}

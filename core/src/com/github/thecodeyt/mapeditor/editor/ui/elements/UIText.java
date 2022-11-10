package com.github.thecodeyt.mapeditor.editor.ui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.font.Fonts;
import com.github.thecodeyt.mapeditor.editor.ui.UI;

public class UIText extends UIElement {
    public Color color;
    public String text;
    public BitmapFont font;

    public UIText(UI ui, Vector2 position, int size, Color color, String text) {
        super(ui, position, new Vector2(0, size));
        this.color = color;
        this.text = text;

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = this.color;
        this.font = Fonts.PIXEL.generateFont(parameter);
    }

    @Override
    public void draw() {
        if(!isPresent(this.ui.camera.spriteBatch)) {
            return;
        }

        font.draw(this.ui.camera.spriteBatch, text, position.x, position.y+size.y);
    }
}

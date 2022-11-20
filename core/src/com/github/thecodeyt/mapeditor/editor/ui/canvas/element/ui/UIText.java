package com.github.thecodeyt.mapeditor.editor.ui.canvas.element.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.font.Fonts;
import com.github.thecodeyt.mapeditor.editor.ui.camera.CanvasCamera;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.UICanvas;
import com.github.thecodeyt.mapeditor.math.render.Drawf;

public class UIText extends UIElement {
    public Color color;
    public String text;
    public BitmapFont font;

    public UIText(UICanvas canvas, Vector2 position, int size, Color color, String text) {
        super(canvas, position, new Vector2(0, size));
        this.color = color;
        this.text = text;

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = this.color;
        this.font = Fonts.PIXEL.generateFont(parameter);
    }

    @Override
    public void draw() {
        CanvasCamera<UICanvas> camera = getCanvas().getCamera();
        if(!Drawf.isPresent(camera.spriteBatch)) {
            return;
        }

        font.draw(camera.spriteBatch, text, position.x, position.y+size.y);
        camera.spriteBatch.flush();
    }
}

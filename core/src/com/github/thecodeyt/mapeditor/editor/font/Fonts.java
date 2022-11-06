package com.github.thecodeyt.mapeditor.editor.font;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Fonts {
    public static FreeTypeFontGenerator PIXEL;

    public static void initialize() {
        PIXEL = new FreeTypeFontGenerator(Gdx.files.internal("font/rainyhearts.ttf"));
    }
    public static void dispose() {
        PIXEL.dispose();
    }
}

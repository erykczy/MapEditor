package com.github.thecodeyt.mapeditor.math;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Shapf {
    public static void drawBorder(ShapeRenderer shapeRenderer, Vector2 position, Vector2 size, Color color) {
        shapeRenderer.setColor(color);

        shapeRenderer.line(position, new Vector2(position.x+size.x, position.y));
        shapeRenderer.line(position, new Vector2(position.x, position.y+size.y));
        shapeRenderer.line(new Vector2(position.x+size.x, position.y), new Vector2(position.x+size.x, position.y+size.y));
        shapeRenderer.line(new Vector2(position.x, position.y+size.y), new Vector2(position.x+size.x, position.y+size.y));
    }
}

package com.github.thecodeyt.mapeditor.math.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.thecodeyt.mapeditor.math.Mathf;
import com.github.thecodeyt.mapeditor.math.Screenf;

public class GridRenderer {
    public void draw(ShapeRenderer shapeRenderer, Viewport viewport, float spacing, Color color) {
        Vector2 screenSize = Screenf.getScreenSize(viewport);
        Vector2 positionCenter = Mathf.vector(viewport.getCamera().position);
        Vector2 positionCorner = Screenf.getBottomLeftCorner(viewport);

        float offset = spacing;
        Vector2 drawingStartPoint = new Vector2(Math.round(positionCorner.x/spacing)*spacing-offset, Math.round(positionCorner.y/spacing)*spacing-offset);

        for(int x = (int) drawingStartPoint.x; x < positionCorner.x+screenSize.x; x += spacing) {
            shapeRenderer.setColor(x == 0 ? color.cpy().add(0, 0, 0, 0.05F) : color);
            shapeRenderer.line(x, drawingStartPoint.y, x, (positionCenter.y+screenSize.y));
        }
        for(int y = (int) drawingStartPoint.y; y < positionCorner.y+screenSize.y; y += spacing) {
            shapeRenderer.setColor(y == 0 ? color.cpy().add(0, 0, 0, 0.05F) : color);
            shapeRenderer.line(drawingStartPoint.x, y, (positionCenter.x+screenSize.x), y);
        }
    }
}

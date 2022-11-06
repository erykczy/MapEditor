package com.github.thecodeyt.mapeditor.math.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.thecodeyt.mapeditor.math.Mathf;
import com.github.thecodeyt.mapeditor.math.Screenf;

public class GridRenderer {
    public void draw(ShapeRenderer shapeRenderer, Viewport viewport, float spacing, Color color) {
        //OrthographicCamera camera = (OrthographicCamera) viewport.getCamera();
        //float zoom = camera.zoom;
        //Vector2 screenSize = new Vector2(viewport.getWorldWidth(), viewport.getWorldHeight()).scl(zoom);
        //Vector2 positionCenter = new Vector2(viewport.getCamera().position.x, viewport.getCamera().position.y);
        //Vector2 positionCorner = positionCenter.cpy().sub(screenSize.cpy().scl(0.5F));
        Vector2 screenSize = Screenf.getScreenSize(viewport);
        Vector2 positionCenter = Mathf.vector(viewport.getCamera().position);
        Vector2 positionCorner = Screenf.getBottomLeftCorner(viewport);

        float offset = spacing;
        Vector2 drawingStartPoint = new Vector2(Math.round(positionCorner.x/spacing)*spacing-offset, Math.round(positionCorner.y/spacing)*spacing-offset);

        /* DEBUG
        shapeRenderer.setColor(new Color(0, 0, 1, 1));
        shapeRenderer.circle(positionCenter.x, positionCenter.y, 2);
        shapeRenderer.setColor(new Color(1, 0, 0, 1));
        shapeRenderer.circle(positionCorner.x, positionCorner.y, 2);
        shapeRenderer.setColor(new Color(0, 1, 0, 1));
        shapeRenderer.circle(screenSize.x, screenSize.y, 2);
         */

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

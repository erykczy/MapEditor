package com.github.thecodeyt.mapeditor.editor.ui.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.Canvas;
import lombok.Getter;

import java.util.List;

public class CanvasCamera<T extends Canvas> {
    @Getter private List<T> canvases;
    public final Viewport viewport;
    public final ShapeRenderer shapeRenderer;
    public final SpriteBatch spriteBatch;

    public CanvasCamera(T... canvases) {
        this.canvases = List.of(canvases);
        this.viewport = new ExtendViewport(400, 400);
        this.shapeRenderer = new ShapeRenderer();
        this.spriteBatch = new SpriteBatch();
    }

    public void draw() {
        viewport.apply();

        OrthographicCamera camera = (OrthographicCamera) viewport.getCamera();
        shapeRenderer.setProjectionMatrix(camera.combined);
        spriteBatch.setProjectionMatrix(camera.combined);

        // DRAWING
        // Shapes
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (T canvas : canvases) {
            canvas.draw();
        }

        shapeRenderer.end();

        // Sprites
        spriteBatch.begin();

        for (T canvas : canvases) {
            canvas.draw();
        }

        spriteBatch.end();

        for (T canvas : canvases) {
            canvas.drawOutOfDrawingContext();
        }
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
        for (T canvas : canvases) {
            canvas.resize(width, height);
        }
    }
}

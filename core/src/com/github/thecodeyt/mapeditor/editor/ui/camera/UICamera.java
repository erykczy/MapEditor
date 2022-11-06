package com.github.thecodeyt.mapeditor.editor.ui.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.thecodeyt.mapeditor.editor.ui.UI;
import com.github.thecodeyt.mapeditor.editor.ui.elements.UIElement;

public class UICamera {
    public UI ui;
    public final Viewport viewport;
    public final ShapeRenderer shapeRenderer;
    public final SpriteBatch spriteBatch;

    public UICamera(UI ui) {
        this.ui = ui;
        this.viewport = new ExtendViewport(400, 400, new OrthographicCamera());
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

        for (UIElement element : ui.elements) {
            element.draw();
        }

        shapeRenderer.end();

        // Sprites
        spriteBatch.begin();

        for (UIElement element : ui.elements) {
            element.draw();
        }

        spriteBatch.end();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}

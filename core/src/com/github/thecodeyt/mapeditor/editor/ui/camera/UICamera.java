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
    public final Viewport viewport = new ExtendViewport(400, 400, new OrthographicCamera());
    public final ShapeRenderer shapeRenderer = new ShapeRenderer();
    public final SpriteBatch spriteBatch = new SpriteBatch();

    public UICamera(UI ui) {
        this.ui = ui;
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

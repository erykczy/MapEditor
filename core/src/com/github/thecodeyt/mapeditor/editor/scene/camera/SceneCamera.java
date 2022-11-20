package com.github.thecodeyt.mapeditor.editor.scene.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.thecodeyt.mapeditor.editor.Constants;
import com.github.thecodeyt.mapeditor.editor.scene.Scene;
import com.github.thecodeyt.mapeditor.editor.scene.gameobject.GameObject;
import com.github.thecodeyt.mapeditor.math.render.GridRenderer;
import lombok.Getter;

public class SceneCamera {
    @Getter private Scene scene;
    public final Viewport viewport = new ExtendViewport(20, 20, new OrthographicCamera());
    public final ShapeRenderer shapeRenderer = new ShapeRenderer();
    public final GridRenderer gridRenderer = new GridRenderer();

    public SceneCamera(Scene scene) {
        this.scene = scene;
        ((OrthographicCamera) viewport.getCamera()).zoom = 5;
    }

    private void handleCameraMovement(float delta) {
        OrthographicCamera sceneCamera = (OrthographicCamera) viewport.getCamera();
        float speed = sceneCamera.zoom*delta*20;
        if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            speed /= 2;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            speed *= 2;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            sceneCamera.translate(0, speed, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            sceneCamera.translate(0, -speed, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            sceneCamera.translate(speed, 0, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            sceneCamera.translate(-speed, 0, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
            if(sceneCamera.zoom <= 40F) {
                sceneCamera.zoom += 0.1*sceneCamera.zoom;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.E)) {
            if(sceneCamera.zoom >= 0.8F) {
                sceneCamera.zoom -= 0.1*sceneCamera.zoom;
            }
        }
    }

    public void update(float delta) {
        OrthographicCamera sceneCamera = (OrthographicCamera) viewport.getCamera();

        handleCameraMovement(delta);
        sceneCamera.update();
    }
    public void draw() {
        ScreenUtils.clear(0.15F, 0.15F, 0.15F, 1);
        viewport.apply();

        OrthographicCamera sceneCamera = (OrthographicCamera) viewport.getCamera();
        shapeRenderer.setProjectionMatrix(sceneCamera.combined);

        // DRAWING SHAPES
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Grid
        gridRenderer.draw(shapeRenderer, viewport, Constants.GRID_SPACING, Constants.GRID_COLOR);  // grid
        shapeRenderer.circle(0, 0, 5, 50);  // grid center

        // Game objects
        shapeRenderer.setColor(Constants.GAME_OBJECT_COLOR);
        for (GameObject gameObject : scene.getGameObjects()) {
            gameObject.draw();
        }

        // Current selection
        if(scene.getCurrentSelection() != null)
            scene.getCurrentSelection().draw();

        shapeRenderer.end();
    }
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}

package com.github.thecodeyt.mapeditor.editor.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.scene.camera.SceneCamera;
import com.github.thecodeyt.mapeditor.editor.scene.gameobject.CircleObject;
import com.github.thecodeyt.mapeditor.editor.scene.gameobject.GameObject;
import com.github.thecodeyt.mapeditor.editor.scene.gameobject.selection.Selection;
import com.github.thecodeyt.mapeditor.math.Inputf;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    public SceneCamera camera;
    public List<GameObject> gameObjects = new ArrayList<>();
    public Selection currentSelection = null;

    public Scene() {
        camera = new SceneCamera(this);
    }

    public void createNewCircleObject(Vector2 position, float radius) {
        CircleObject circleObject = new CircleObject(this, position, radius);
        gameObjects.add(circleObject);
        currentSelection = new Selection(this, circleObject);
    }
    private void handleSelection() {
        if(!Gdx.input.isButtonJustPressed(0)) {
            return;
        } // Is there any action
        Vector2 pointerPosition = Inputf.getPointerPosition(camera.viewport);

        boolean noGameObjectSelected = true;
        for (GameObject gameObject : gameObjects) {
            if(!gameObject.getHitBox().isPointColliding(pointerPosition)) {
                continue;
            } // did pointer click
            if(currentSelection != null && currentSelection.gameObject.equals(gameObject)) {
                noGameObjectSelected = false;
                break;
            } // don't want to select the same gameObject again

            // creating new selection
            currentSelection = new Selection(this, gameObject);
            noGameObjectSelected = false;
        } // Creating selection

        if(noGameObjectSelected) {
            currentSelection = null;
        } // Removing current selection
    }

    public void update(float delta) {
        this.handleSelection();

        for (GameObject gameObject : gameObjects) {
            gameObject.update();
        } // Updating game objects
        if(currentSelection != null) {
            currentSelection.update();
        } // Updating current selection

        camera.update(delta);
    }
    public void draw() {
        camera.draw();
    }
    public void resize(int width, int height) {
        camera.resize(width, height);
    }
}

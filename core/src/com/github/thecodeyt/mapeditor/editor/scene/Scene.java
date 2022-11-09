package com.github.thecodeyt.mapeditor.editor.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.scene.camera.SceneCamera;
import com.github.thecodeyt.mapeditor.editor.scene.gameobject.CircleObject;
import com.github.thecodeyt.mapeditor.editor.scene.gameobject.GameObject;
import com.github.thecodeyt.mapeditor.editor.scene.gameobject.selection.Selection;
import com.github.thecodeyt.mapeditor.math.input.Inputf;

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
        createNewSelection(circleObject);
    }
    public void removeSelectedGameObject() {
        if(currentSelection == null) {
            return;
        }

        gameObjects.remove(currentSelection.gameObject);
        currentSelection = null;
    }
    public void createNewSelection(GameObject gameObject) {
        // creating selection
        currentSelection = new Selection(this, gameObject);
    }
    public GameObject duplicateGameObject(GameObject gameObject) {
        GameObject copy = gameObject.copy();
        copy.position.add(copy.getSize()); // offset

        this.gameObjects.add(copy); // adding

        this.createNewSelection(copy); // new selection
        return copy;
    }
    private void handleSelection() {
        if(Inputf.isAnyActionExecuting()) {
            return;
        }

        if(currentSelection != null) {
            if(Gdx.input.isKeyJustPressed(Input.Keys.FORWARD_DEL)) {
                this.removeSelectedGameObject();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && Gdx.input.isKeyJustPressed(Input.Keys.D)) {
                this.duplicateGameObject(currentSelection.gameObject);
            }
        }

        if(!Inputf.justLeftClick()) {
            return;
        }
        Vector2 pointerPosition = Inputf.getPointerPosition(camera.viewport);

        boolean noGameObjectSelected = true;
        // Creating selection
        // don't want to select the same gameObject again
        if(currentSelection != null && currentSelection.gameObject.getHitBox().isPointColliding(pointerPosition)) {
            noGameObjectSelected = false;
        }
        else {
            for (GameObject gameObject : gameObjects) {
                // did pointer click on hitbox
                if(!gameObject.getHitBox().isPointColliding(pointerPosition)) {
                    continue;
                }

                // creating new selection
                createNewSelection(gameObject);
                noGameObjectSelected = false;
                break;
            }
        }

        // Removing current selection
        if(noGameObjectSelected) {
            currentSelection = null;
        }
    }

    public void update(float delta) {
        this.handleSelection();

        // Updating game objects
        for (GameObject gameObject : gameObjects) {
            gameObject.update();
        }
        // Updating current selection
        if(currentSelection != null) {
            currentSelection.update();
        }

        camera.update(delta);
    }
    public void draw() {
        camera.draw();
    }
    public void resize(int width, int height) {
        camera.resize(width, height);
    }
}

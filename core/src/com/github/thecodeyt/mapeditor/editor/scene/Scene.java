package com.github.thecodeyt.mapeditor.editor.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.scene.camera.SceneCamera;
import com.github.thecodeyt.mapeditor.editor.scene.gameobject.CircleObject;
import com.github.thecodeyt.mapeditor.editor.scene.gameobject.GameObject;
import com.github.thecodeyt.mapeditor.editor.scene.gameobject.selection.Selection;
import com.github.thecodeyt.mapeditor.math.input.Inputf;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    @Getter private SceneCamera camera;
    @Getter private List<GameObject> gameObjects = new ArrayList<>();
    @Getter private Selection currentSelection = null;

    public Scene() {
        camera = new SceneCamera(this);
    }

    public void createNewCircleObject(Vector2 position, float radius) {
        CircleObject circleObject = new CircleObject(this, position, radius);
        addNewGameObject(circleObject, true);
    }
    public void addNewGameObject(GameObject gameObject, boolean select) {
        gameObjects.add(gameObject);
        if(select)
            createNewSelection(gameObject);
    }
    public void removeSelectedGameObject() {
        if(currentSelection == null) {
            return;
        }

        gameObjects.remove(currentSelection.getGameObject());
        currentSelection = null;
    }
    public void createNewSelection(GameObject gameObject) {
        // creating selection
        currentSelection = new Selection(this, gameObject);
    }
    public GameObject duplicateGameObject(GameObject gameObject) {
        GameObject copy = gameObject.copy();
        copy.getPosition().add(copy.getSize()); // offset

        gameObjects.add(copy); // adding

        createNewSelection(copy); // new selection
        return copy;
    }
    private void handleSelection() {
        if(Inputf.isAnyActionExecuting()) {
            return;
        }

        if(currentSelection != null) {
            if(Gdx.input.isKeyJustPressed(Input.Keys.FORWARD_DEL)) {
                removeSelectedGameObject();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && Gdx.input.isKeyJustPressed(Input.Keys.D)) {
                duplicateGameObject(currentSelection.getGameObject());
            }
        }

        if(!Inputf.justLeftClick()) {
            return;
        }
        Vector2 pointerPosition = Inputf.getPointerPosition(camera.viewport);

        boolean noGameObjectSelected = true;
        // Creating selection
        // don't want to select the same gameObject again
        if(currentSelection != null && currentSelection.getGameObject().getHitBox().isPointColliding(pointerPosition)) {
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
        handleSelection();

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

package com.github.thecodeyt.mapeditor.editor.scene.gameobject.selection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.Constants;
import com.github.thecodeyt.mapeditor.editor.scene.Scene;
import com.github.thecodeyt.mapeditor.editor.scene.gameobject.GameObject;
import com.github.thecodeyt.mapeditor.math.input.Action;
import com.github.thecodeyt.mapeditor.math.input.Inputf;
import com.github.thecodeyt.mapeditor.math.Mathf;
import com.github.thecodeyt.mapeditor.math.Shapf;

public class Selection {
    public Scene scene;
    public GameObject gameObject;

    private Changer widthChanger;
    private Changer heightChanger;
    private Vector2 moveDelta;

    public Selection(Scene scene, GameObject gameObject) {
        this.scene = scene;
        this.gameObject = gameObject;
        this.widthChanger = new Changer();
        this.heightChanger = new Changer();
    }

    private void updateChangers() {
        widthChanger.position = new Vector2(gameObject.position.x+gameObject.size.x, gameObject.position.y+gameObject.size.y/2F);
        widthChanger.radius = Constants.CHANGER_SIZE*gameObject.size.x;

        heightChanger.position = new Vector2(gameObject.position.x+gameObject.size.x/2F, gameObject.position.y+gameObject.size.y);
        heightChanger.radius = Constants.CHANGER_SIZE*gameObject.size.y;
    }
    private void checkForActions() {
        // if there is interaction
        if(!Gdx.input.isButtonPressed(0)) {
            if (Inputf.getCurrentAction().groupID == 0)
                Inputf.setCurrentAction(Action.NONE);
            return;
        }
        // don't want conflicts with other actions
        if(Inputf.getCurrentAction() != Action.NONE) {
            return;
        }
        Vector2 pointerPosition = Inputf.getPointerPosition(scene.camera.viewport);

        if(widthChanger.getHitBox().isPointColliding(pointerPosition)) {
            Inputf.setCurrentAction(Action.CHANGING_WIDTH_OF_GAME_OBJECT);
            return;
        }
        if(heightChanger.getHitBox().isPointColliding(pointerPosition)) {
            Inputf.setCurrentAction(Action.CHANGING_HEIGHT_OF_GAME_OBJECT);
            return;
        }

        Inputf.setCurrentAction(Action.MOVING_GAME_OBJECT);
        this.moveDelta = Mathf.delta(gameObject.position, pointerPosition);
    }
    private void executeCurrentAction() {
        Vector2 pointerPosition = Inputf.getPointerPosition(scene.camera.viewport);
        boolean align = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT);

        switch (Inputf.getCurrentAction()) {
            case MOVING_GAME_OBJECT -> {
                gameObject.position = pointerPosition.cpy().sub(this.moveDelta);

                if(align) {
                    Mathf.align(gameObject.position, Constants.GRID_SPACING);
                }
            }
            case CHANGING_WIDTH_OF_GAME_OBJECT -> {
                gameObject.size.x = widthChanger.getNewSize(pointerPosition, align, gameObject.size.x);
            }
            case CHANGING_HEIGHT_OF_GAME_OBJECT -> {
                gameObject.size.y = widthChanger.getNewSize(pointerPosition, align, gameObject.size.y);
            }
        }
    }

    public void update() {
        updateChangers();
        checkForActions();
        executeCurrentAction();
    }
    public void draw() {
        ShapeRenderer shapeRenderer = this.scene.camera.shapeRenderer;
        shapeRenderer.setColor(Constants.GAME_OBJECT_SELECTION_COLOR);

        // lines
        Shapf.drawBorder(shapeRenderer, gameObject.position, gameObject.size, Constants.GAME_OBJECT_SELECTION_COLOR);

        // changers
        if(!Inputf.getCurrentAction().equals(Action.MOVING_GAME_OBJECT)) { // decoration
            widthChanger.draw(shapeRenderer);
            heightChanger.draw(shapeRenderer);
        }

        // anchor point
        shapeRenderer.setColor(Constants.POSITION_POINT_COLOR);
        shapeRenderer.circle(gameObject.position.x, gameObject.position.y, 1, 100);
    }
}

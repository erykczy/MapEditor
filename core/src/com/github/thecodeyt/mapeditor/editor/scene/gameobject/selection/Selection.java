package com.github.thecodeyt.mapeditor.editor.scene.gameobject.selection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.Constants;
import com.github.thecodeyt.mapeditor.editor.scene.Scene;
import com.github.thecodeyt.mapeditor.editor.scene.gameobject.GameObject;
import com.github.thecodeyt.mapeditor.math.Axis;
import com.github.thecodeyt.mapeditor.math.Mathf;
import com.github.thecodeyt.mapeditor.math.Shapf;
import com.github.thecodeyt.mapeditor.math.input.ActionDesc;
import com.github.thecodeyt.mapeditor.math.input.Branch;
import com.github.thecodeyt.mapeditor.math.input.Inputf;

public class Selection {
    public Scene scene;
    public GameObject gameObject;

    private SceneSizeChanger widthChanger;
    private SceneSizeChanger heightChanger;
    private Vector2 moveDelta;

    public Selection(Scene scene, GameObject gameObject) {
        this.scene = scene;
        this.gameObject = gameObject;
        this.widthChanger = new SceneSizeChanger(scene, new ActionDesc(Branch.GAME_OBJECT, Branch.SELECTION, Branch.CHANGE_SIZE, Branch.X), Axis.X, this.gameObject.getSize().x, Constants.MIN_GAME_OBJECT_SIZE, -1);
        this.heightChanger = new SceneSizeChanger(scene, new ActionDesc(Branch.GAME_OBJECT, Branch.SELECTION, Branch.CHANGE_SIZE, Branch.Y), Axis.Y, this.gameObject.getSize().y, Constants.MIN_GAME_OBJECT_SIZE, -1);
    }

    private void checkForActions() {
        // clearing action
        if(!Inputf.leftClick()) {
            if (Inputf.isAnyActionExecuting() && Inputf.getCurrentActionDesc().hasBranch(Branch.SELECTION))
                Inputf.cancelCurrentAction();
            return;
        }

        // don't want conflicts with other actions
        if(Inputf.isAnyActionExecuting()) {
            return;
        }
        Vector2 pointerPosition = Inputf.getPointerPosition(scene.camera.viewport);

        if(!this.gameObject.getHitBox().isPointColliding(pointerPosition)) {
            return;
        }

        Inputf.setCurrentActionDesc(new ActionDesc(Branch.GAME_OBJECT, Branch.SELECTION, Branch.CHANGE_POS));
        this.moveDelta = Mathf.delta(gameObject.position, pointerPosition);
    }
    private void executeCurrentAction() {
        if(!Inputf.isAnyActionExecuting() || !Inputf.getCurrentActionDesc().hasBranch(Branch.GAME_OBJECT, Branch.SELECTION)) {
            return;
        }

        Vector2 pointerPosition = Inputf.getPointerPosition(scene.camera.viewport);
        boolean align = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT);

        if(Inputf.getCurrentActionDesc().hasBranch(Branch.CHANGE_POS)) {
            gameObject.position = pointerPosition.cpy().sub(this.moveDelta);

            if(align) {
                Mathf.align(gameObject.position, Constants.GRID_SPACING);
            }
        }
    }
    private void updateChangers() {
        // UPDATING WIDTH CHANGER
        widthChanger.update(gameObject.getSize());
        // applying size to gameObject
        this.gameObject.setSize(new Vector2(this.widthChanger.currentSize, this.gameObject.getSize().y));
        // updating changer position
        this.widthChanger.position = new Vector2(this.gameObject.position.x+this.gameObject.getSize().x, this.gameObject.position.y+this.gameObject.getSize().y/2F);

        // UPDATING HEIGHT CHANGER
        heightChanger.update(gameObject.getSize());
        // applying size to gameObject
        this.gameObject.setSize(new Vector2(this.gameObject.getSize().x, this.heightChanger.currentSize));
        // updating changer position
        this.heightChanger.position = new Vector2(this.gameObject.position.x+this.gameObject.getSize().x/2F, this.gameObject.position.y+this.gameObject.getSize().y);
    }

    public void update() {
        this.updateChangers();
        this.checkForActions();
        this.executeCurrentAction();
    }
    public void draw() {
        ShapeRenderer shapeRenderer = this.scene.camera.shapeRenderer;
        shapeRenderer.setColor(Constants.GAME_OBJECT_SELECTION_COLOR);

        // lines
        Shapf.drawBorder(shapeRenderer, gameObject.position, gameObject.getSize(), Constants.GAME_OBJECT_SELECTION_COLOR);

        // changers
        if(!Inputf.isAnyActionExecuting() || !Inputf.getCurrentActionDesc().hasBranch(Branch.GAME_OBJECT, Branch.SELECTION, Branch.CHANGE_POS)) {  // decoration
            widthChanger.draw(shapeRenderer);
            heightChanger.draw(shapeRenderer);
        }

        // anchor point
        shapeRenderer.setColor(Constants.POSITION_POINT_COLOR);
        shapeRenderer.circle(gameObject.position.x, gameObject.position.y, 1, 100);
    }
}

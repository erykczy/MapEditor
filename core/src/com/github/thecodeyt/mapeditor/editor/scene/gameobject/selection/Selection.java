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
import com.github.thecodeyt.mapeditor.math.input.ActionDesc;
import com.github.thecodeyt.mapeditor.math.input.Branch;
import com.github.thecodeyt.mapeditor.math.input.Inputf;
import com.github.thecodeyt.mapeditor.math.render.Drawf;
import lombok.Getter;

public class Selection {
    @Getter private Scene scene;
    @Getter private GameObject gameObject;

    private SceneSizeChanger widthChanger;
    private SceneSizeChanger heightChanger;
    private Vector2 moveDelta;

    public Selection(Scene scene, GameObject gameObject) {
        this.scene = scene;
        this.gameObject = gameObject;
        this.widthChanger = new SceneSizeChanger(scene, new ActionDesc(Branch.GAME_OBJECT, Branch.SELECTION, Branch.CHANGE_SIZE, Branch.X), Axis.X, gameObject.getSize().x, Constants.MIN_GAME_OBJECT_SIZE, -1);
        this.heightChanger = new SceneSizeChanger(scene, new ActionDesc(Branch.GAME_OBJECT, Branch.SELECTION, Branch.CHANGE_SIZE, Branch.Y), Axis.Y, gameObject.getSize().y, Constants.MIN_GAME_OBJECT_SIZE, -1);
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
        Vector2 pointerPosition = Inputf.getPointerPosition(scene.getCamera().viewport);

        if(!gameObject.getHitBox().isPointColliding(pointerPosition)) {
            return;
        }

        Inputf.setCurrentActionDesc(new ActionDesc(Branch.GAME_OBJECT, Branch.SELECTION, Branch.CHANGE_POS));
        moveDelta = Mathf.delta(gameObject.getPosition(), pointerPosition);
    }
    private void executeCurrentAction() {
        if(!Inputf.isAnyActionExecuting() || !Inputf.getCurrentActionDesc().hasBranch(Branch.GAME_OBJECT, Branch.SELECTION)) {
            return;
        }

        Vector2 pointerPosition = Inputf.getPointerPosition(scene.getCamera().viewport);
        boolean align = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT);

        if(Inputf.getCurrentActionDesc().hasBranch(Branch.CHANGE_POS)) {
            gameObject.setPosition(pointerPosition.cpy().sub(moveDelta));

            if(align) {
                Vector2 newPos = gameObject.getPosition();
                Mathf.align(newPos, Constants.GRID_SPACING);
                gameObject.setPosition(newPos);
            }
        }
    }
    private void updateChangers() {
        // UPDATING WIDTH CHANGER
        widthChanger.update(gameObject.getSize());
        // applying size to gameObject
        gameObject.setSize(new Vector2(widthChanger.getCurrentSize(), this.gameObject.getSize().y));
        // updating changer position
        this.widthChanger.setPosition(new Vector2(this.gameObject.getPosition().x+this.gameObject.getSize().x, this.gameObject.getPosition().y+this.gameObject.getSize().y/2F));

        // UPDATING HEIGHT CHANGER
        heightChanger.update(gameObject.getSize());
        // applying size to gameObject
        this.gameObject.setSize(new Vector2(this.gameObject.getSize().x, this.heightChanger.getCurrentSize()));
        // updating changer position
        this.heightChanger.setPosition(new Vector2(this.gameObject.getPosition().x+this.gameObject.getSize().x/2F, this.gameObject.getPosition().y+this.gameObject.getSize().y));
    }

    public void update() {
        this.updateChangers();
        this.checkForActions();
        this.executeCurrentAction();
    }
    public void draw() {
        ShapeRenderer shapeRenderer = this.scene.getCamera().shapeRenderer;
        shapeRenderer.setColor(Constants.GAME_OBJECT_SELECTION_COLOR);

        // lines
        Drawf.drawBorder(shapeRenderer, gameObject.getPosition(), gameObject.getSize(), Constants.GAME_OBJECT_SELECTION_COLOR);

        // changers
        if(!Inputf.isAnyActionExecuting() || !Inputf.getCurrentActionDesc().hasBranch(Branch.GAME_OBJECT, Branch.SELECTION, Branch.CHANGE_POS)) {  // decoration
            widthChanger.draw(shapeRenderer);
            heightChanger.draw(shapeRenderer);
        }

        // anchor point
        shapeRenderer.setColor(Constants.POSITION_POINT_COLOR);
        shapeRenderer.circle(gameObject.getPosition().x, gameObject.getPosition().y, 1, 100);
    }
}

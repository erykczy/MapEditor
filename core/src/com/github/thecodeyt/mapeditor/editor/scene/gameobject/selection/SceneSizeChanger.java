package com.github.thecodeyt.mapeditor.editor.scene.gameobject.selection;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.Constants;
import com.github.thecodeyt.mapeditor.editor.scene.Scene;
import com.github.thecodeyt.mapeditor.math.Axis;
import com.github.thecodeyt.mapeditor.math.HitBox;
import com.github.thecodeyt.mapeditor.math.Mathf;
import com.github.thecodeyt.mapeditor.math.input.ActionDesc;
import com.github.thecodeyt.mapeditor.math.input.Inputf;

public class SceneSizeChanger {
    public Scene scene;
    public Vector2 position = new Vector2(0, 0);
    public float radius = Constants.CHANGER_SIZE;
    public float currentSize = 0;
    private ActionDesc actionDesc;
    public float minSize;
    public float maxSize;
    public Axis axis;

    public SceneSizeChanger(Scene scene, ActionDesc actionDesc, Axis axis, float currentSize, float minSize, float maxSize) {
        this.scene = scene;
        this.actionDesc = actionDesc;
        this.axis = axis;
        this.currentSize = currentSize;
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    public HitBox getHitBox() {
        return new HitBox(this.position.cpy().sub(radius, radius), new Vector2(radius*2, radius*2));
    }
    private float getNewSize(Vector2 pointerPosition, boolean align, float oldSize) {
        Vector2 delta_vec = Mathf.delta(this.position, pointerPosition);
        double delta = axis.equals(Axis.X) ? delta_vec.x : delta_vec.y;
        float newSize = oldSize;
        newSize += delta;

        if(align) {
            newSize = Mathf.align(newSize, Constants.GRID_SPACING);
        }

        return newSize >= minSize && (newSize <= maxSize || maxSize == -1) ? newSize : oldSize;
    }
    private void checkForAction() {
        Vector2 pointerPosition = Inputf.getPointerPosition(scene.camera.viewport);

        if(Inputf.leftClick()) {
            if(getHitBox().isPointColliding(pointerPosition) && !Inputf.isAnyActionExecuting()) {
                Inputf.setCurrentActionDesc(actionDesc);
            }
        }
        else {
            if(Inputf.isAnyActionExecuting() && Inputf.getCurrentActionDesc().equals(actionDesc))
                Inputf.cancelCurrentAction();
        }
    }
    private void executeAction() {
        Vector2 pointerPosition = Inputf.getPointerPosition(scene.camera.viewport);
        if(Inputf.isAnyActionExecuting() && Inputf.getCurrentActionDesc().equals(actionDesc)) {
            currentSize = getNewSize(pointerPosition, Inputf.shiftClick(), currentSize);
        }
    }

    public void update(Vector2 parentSize) {
        this.radius = Constants.CHANGER_SIZE*parentSize.x;
        checkForAction();
        executeAction();
    }
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.circle(this.position.x, this.position.y, this.radius, 100);
    }
}

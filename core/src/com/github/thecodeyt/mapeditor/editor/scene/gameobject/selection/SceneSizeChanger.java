package com.github.thecodeyt.mapeditor.editor.scene.gameobject.selection;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.Constants;
import com.github.thecodeyt.mapeditor.editor.scene.Scene;
import com.github.thecodeyt.mapeditor.math.Axis;
import com.github.thecodeyt.mapeditor.math.Mathf;
import com.github.thecodeyt.mapeditor.math.geometry.RectHitBox;
import com.github.thecodeyt.mapeditor.math.input.ActionDesc;
import com.github.thecodeyt.mapeditor.math.input.Inputf;
import lombok.Getter;
import lombok.Setter;

public class SceneSizeChanger {
    @Getter private Scene scene;
    @Getter @Setter private Vector2 position = new Vector2(0, 0);
    @Getter private float radius = Constants.CHANGER_SIZE;
    @Getter private float currentSize = 0;
    private ActionDesc actionDesc;
    @Getter private float minSize;
    @Getter private float maxSize;
    @Getter private Axis axis;

    public SceneSizeChanger(Scene scene, ActionDesc actionDesc, Axis axis, float currentSize, float minSize, float maxSize) {
        this.scene = scene;
        this.actionDesc = actionDesc;
        this.axis = axis;
        this.currentSize = currentSize;
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    public RectHitBox getHitBox() {
        return new RectHitBox(position.cpy().sub(radius, radius), new Vector2(radius*2, radius*2));
    }
    private float getNewSize(Vector2 pointerPosition, boolean align, float oldSize) {
        Vector2 delta_vec = Mathf.delta(position, pointerPosition);
        double delta = axis.equals(Axis.X) ? delta_vec.x : delta_vec.y;
        float newSize = oldSize;
        newSize += delta;

        if(align) {
            newSize = Mathf.align(newSize, Constants.GRID_SPACING);
        }

        return newSize >= minSize && (newSize <= maxSize || maxSize == -1) ? newSize : oldSize;
    }
    private void checkForAction() {
        Vector2 pointerPosition = Inputf.getPointerPosition(scene.getCamera().viewport);

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
        Vector2 pointerPosition = Inputf.getPointerPosition(scene.getCamera().viewport);
        if(Inputf.isAnyActionExecuting() && Inputf.getCurrentActionDesc().equals(actionDesc)) {
            currentSize = getNewSize(pointerPosition, Inputf.shiftClick(), currentSize);
        }
    }

    public void update(Vector2 parentSize) {
        radius = Constants.CHANGER_SIZE*parentSize.x;
        checkForAction();
        executeAction();
    }
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.circle(position.x, position.y, radius, 100);
    }
}

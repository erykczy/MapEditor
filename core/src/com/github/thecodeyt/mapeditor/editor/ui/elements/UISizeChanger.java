package com.github.thecodeyt.mapeditor.editor.ui.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.Constants;
import com.github.thecodeyt.mapeditor.editor.ui.UI;
import com.github.thecodeyt.mapeditor.math.Axis;
import com.github.thecodeyt.mapeditor.math.HitBox;
import com.github.thecodeyt.mapeditor.math.Mathf;
import com.github.thecodeyt.mapeditor.math.input.ActionDesc;
import com.github.thecodeyt.mapeditor.math.input.Inputf;

public class UISizeChanger extends UIElement {
    public float radius = Constants.CHANGER_SIZE*100;
    public float currentSize = 0;
    private ActionDesc changeSizeActionDesc;
    public float minSize;
    public float maxSize;
    public Axis axis;

    public UISizeChanger(UI ui, Vector2 position, ActionDesc onChangeSizeAction, Axis axis, float currentSize, float minSize, float maxSize) {
        super(ui, position, new Vector2());
        this.currentSize = currentSize;
        this.changeSizeActionDesc = onChangeSizeAction;
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.axis = axis;
    }

    public HitBox getHitBox() {
        return new HitBox(this.position.cpy().sub(radius, radius), new Vector2(radius*2, radius*2));
    }
    private float getNewSize(Vector2 pointerPosition, float oldSize) {
        Vector2 delta_vec = Mathf.delta(this.position, pointerPosition);
        double delta = axis.equals(Axis.X) ? delta_vec.x : delta_vec.y;
        float newSize = oldSize;
        newSize += delta;

        return newSize >= minSize && (newSize <= maxSize || maxSize == -1) ? newSize : oldSize;
    }
    private void checkForAction() {
        Vector2 pointerPosition = Inputf.getPointerPosition(ui.camera.viewport);

        if(Gdx.input.isButtonPressed(0)) {
            if(getHitBox().isPointColliding(pointerPosition) && !Inputf.isAnyActionExecuting()) {
                Inputf.setCurrentActionDesc(changeSizeActionDesc);
            }
        }
        else {
            if(Inputf.isAnyActionExecuting() && Inputf.getCurrentActionDesc().equals(changeSizeActionDesc))
                Inputf.cancelCurrentAction();
        }
    }
    private void executeAction() {
        Vector2 pointerPosition = Inputf.getPointerPosition(ui.camera.viewport);
        if(Inputf.isAnyActionExecuting() && Inputf.getCurrentActionDesc().equals(changeSizeActionDesc)) {
            currentSize = getNewSize(pointerPosition, currentSize);
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        this.checkForAction();
        this.executeAction();
    }

    @Override
    public void draw() {
        if(!isPresent(ui.camera.shapeRenderer)) {
            return;
        }

        ui.camera.shapeRenderer.circle(this.position.x, this.position.y, this.radius, 100);
    }
}

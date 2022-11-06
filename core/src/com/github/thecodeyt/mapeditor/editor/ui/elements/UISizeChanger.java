package com.github.thecodeyt.mapeditor.editor.ui.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.Constants;
import com.github.thecodeyt.mapeditor.editor.ui.UI;
import com.github.thecodeyt.mapeditor.math.Axis;
import com.github.thecodeyt.mapeditor.math.HitBox;
import com.github.thecodeyt.mapeditor.math.input.Action;
import com.github.thecodeyt.mapeditor.math.input.Inputf;
import com.github.thecodeyt.mapeditor.math.Mathf;

public class UISizeChanger extends UIElement {
    public Vector2 position = new Vector2();
    public float radius = Constants.CHANGER_SIZE*100;
    public float currentSize = 0;
    private Action changeSizeAction;
    public float minSize;
    public float maxSize;
    public Axis changeAxis;

    public UISizeChanger(UI ui, Action onChangeSizeAction, Axis changeAxis, float currentSize, float minSize, float maxSize) {
        super(ui);
        this.currentSize = currentSize;
        this.changeSizeAction = onChangeSizeAction;
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.changeAxis = changeAxis;
    }

    public HitBox getHitBox() {
        return new HitBox(this.position.cpy().sub(radius, radius), new Vector2(radius*2, radius*2));
    }
    private float getNewSize(Vector2 pointerPosition, float oldSize) {
        Vector2 delta_vec = Mathf.delta(this.position, pointerPosition);
        double delta = changeAxis.equals(Axis.X) ? delta_vec.x : delta_vec.y;
        float new_size = oldSize;
        new_size += delta;

        return new_size < minSize || new_size > maxSize ? oldSize : new_size;
    }
    private void checkForAction() {
        Vector2 pointerPosition = Inputf.getPointerPosition(ui.camera.viewport);
        // don't want conflict with other actions
        //if(!Inputf.getCurrentAction().equals(Action.NONE)) {
        //    return;
        //}

        if(Gdx.input.isButtonPressed(0)) {
            if(getHitBox().isPointColliding(pointerPosition) && Inputf.getCurrentAction().equals(Action.NONE)) {
                Inputf.setCurrentAction(changeSizeAction);
            }
        }
        else {
            if(Inputf.getCurrentAction().equals(changeSizeAction))
                Inputf.setCurrentAction(Action.NONE);
        }
    }
    private void executeAction() {
        Vector2 pointerPosition = Inputf.getPointerPosition(ui.camera.viewport);
        if(Inputf.getCurrentAction().equals(changeSizeAction)) {
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

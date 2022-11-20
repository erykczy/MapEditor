package com.github.thecodeyt.mapeditor.editor.ui.canvas.element.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.Constants;
import com.github.thecodeyt.mapeditor.editor.ui.camera.CanvasCamera;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.UICanvas;
import com.github.thecodeyt.mapeditor.math.Axis;
import com.github.thecodeyt.mapeditor.math.Mathf;
import com.github.thecodeyt.mapeditor.math.geometry.MultiRectHitbox;
import com.github.thecodeyt.mapeditor.math.geometry.RectHitBox;
import com.github.thecodeyt.mapeditor.math.input.ActionDesc;
import com.github.thecodeyt.mapeditor.math.input.Inputf;
import com.github.thecodeyt.mapeditor.math.render.Drawf;
import lombok.Getter;
import lombok.Setter;

public class UISizeChanger extends UIElement {
    @Getter @Setter private float radius = Constants.CHANGER_SIZE*100;
    @Getter @Setter private float currentSize = 0;
    @Getter @Setter private ActionDesc changeSizeActionDesc;
    @Getter @Setter private float minSize;
    @Getter @Setter private float maxSize;
    @Getter @Setter private Axis axis;
    @Getter @Setter private Color color;

    public UISizeChanger(UICanvas canvas, Vector2 position, Color color, ActionDesc onChangeSizeAction, Axis axis, float currentSize, float minSize, float maxSize) {
        super(canvas, position, new Vector2());
        this.currentSize = currentSize;
        this.changeSizeActionDesc = onChangeSizeAction;
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.axis = axis;
        this.color = color;
    }

    public MultiRectHitbox getHitBox() {
        return new MultiRectHitbox(new RectHitBox(this.position.cpy().sub(radius, radius), new Vector2(radius*2, radius*2)));
    }
    private float getNewSize(Vector2 pointerPosition, float oldSize) {
        Vector2 delta_vec = Mathf.delta(this.position, pointerPosition);
        double delta = axis.equals(Axis.X) ? delta_vec.x : delta_vec.y;
        float newSize = oldSize;
        newSize += delta;

        return newSize >= minSize && (newSize <= maxSize || maxSize == -1) ? newSize : oldSize;
    }
    private void checkForAction() {
        Vector2 pointerPosition = Inputf.getPointerPosition(getCanvas().getCamera().viewport);

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
        Vector2 pointerPosition = Inputf.getPointerPosition(getCanvas().getCamera().viewport);
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
        CanvasCamera camera = getCanvas().getCamera();
        if(!Drawf.isPresent(camera.shapeRenderer)) {
            return;
        }

        camera.shapeRenderer.setColor(getColor());
        //getPosition().add(0, Inputf.getPointerPosition(getCanvas().getCamera().viewport).y); // TODO debug, remove
        camera.shapeRenderer.circle(this.position.x, this.position.y, this.radius, 100);
        camera.shapeRenderer.flush();
    }
}

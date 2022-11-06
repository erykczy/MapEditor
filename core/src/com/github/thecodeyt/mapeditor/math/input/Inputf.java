package com.github.thecodeyt.mapeditor.math.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Inputf {
    private static Action currentAction = Action.NONE;

    public static Vector2 getPointerPosition(Viewport viewport) {
        Vector2 pointerPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        return viewport == null ? pointerPosition : viewport.unproject(pointerPosition);
    }
    public static Action getCurrentAction() {
        return currentAction;
    }
    public static void setCurrentAction(Action action) {
        currentAction = action;
    }
}

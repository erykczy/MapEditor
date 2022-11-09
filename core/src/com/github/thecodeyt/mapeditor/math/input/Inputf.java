package com.github.thecodeyt.mapeditor.math.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Inputf {
    private static ActionDesc currentActionDesc = null;

    public static boolean shiftClick() {
        return Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT);
    }
    public static boolean leftClick() {
        return Gdx.input.isButtonPressed(0);
    }
    public static boolean justLeftClick() {
        return Gdx.input.isButtonJustPressed(0);
    }

    public static Vector2 getPointerPosition(Viewport viewport) {
        Vector2 pointerPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        return viewport == null ? pointerPosition : viewport.unproject(pointerPosition);
    }
    public static ActionDesc getCurrentActionDesc() {
        return currentActionDesc;
    }
    public static boolean isAnyActionExecuting() {
        return currentActionDesc != null;
    }
    public static void cancelCurrentAction() {
        currentActionDesc = null;
    }
    public static void setCurrentActionDesc(ActionDesc action) {
        currentActionDesc = action;
    }
}

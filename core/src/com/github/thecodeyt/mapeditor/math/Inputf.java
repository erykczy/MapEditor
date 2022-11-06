package com.github.thecodeyt.mapeditor.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Inputf {
    private static Vector2 pointerPositionBeforeClicking;

    public static void update() {
        if(Gdx.input.isButtonJustPressed(0)) {
            pointerPositionBeforeClicking = getPointerPosition(null);
        }
    }

    public static Vector2 getPointerPosition(Viewport viewport) {
        Vector2 pointerPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        return viewport == null ? pointerPosition : viewport.unproject(pointerPosition);
    }
    public static Vector2 getPointerPositionBeforeClicking(Viewport viewport) {
        return viewport == null ? pointerPositionBeforeClicking.cpy() : viewport.unproject(pointerPositionBeforeClicking.cpy());
    }
}

package com.github.thecodeyt.mapeditor.math;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Screenf {
    public static Vector2 getBottomLeftCorner(Viewport viewport) {
        Vector2 screenSize = getScreenSize(viewport);
        Vector2 position = new Vector2(viewport.getCamera().position.x, viewport.getCamera().position.y);
        return position.cpy().sub(screenSize.scl(0.5F));
    }
    public static Vector2 getScreenSize(Viewport viewport) {
        OrthographicCamera camera = (OrthographicCamera) viewport.getCamera();
        float zoom = camera.zoom;
        Vector2 screenSize = new Vector2(viewport.getWorldWidth(), viewport.getWorldHeight()).scl(zoom);
        return screenSize;
    }
}

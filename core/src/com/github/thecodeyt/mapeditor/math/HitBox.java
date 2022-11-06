package com.github.thecodeyt.mapeditor.math;

import com.badlogic.gdx.math.Vector2;

public class HitBox {
    public Vector2 position;
    public Vector2 size;

    public HitBox(Vector2 position, Vector2 size) {
        this.position = position;
        this.size = size;
    }

    public boolean isPointColliding(Vector2 point) {
        return Mathf.isColliding(point, this.position, this.size.cpy().add(this.position));
    }
}

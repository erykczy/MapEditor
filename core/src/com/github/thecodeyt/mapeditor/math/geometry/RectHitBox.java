package com.github.thecodeyt.mapeditor.math.geometry;

import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.math.Mathf;
import lombok.Getter;

public class RectHitBox {
    @Getter private Vector2 position;
    @Getter private Vector2 size;

    public RectHitBox(Vector2 position, Vector2 size) {
        this.position = position;
        this.size = size;
    }

    public boolean isPointColliding(Vector2 point) {
        return Mathf.isColliding(point, position, size.cpy().add(position));
    }
}

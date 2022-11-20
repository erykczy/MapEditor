package com.github.thecodeyt.mapeditor.math.render;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import lombok.Setter;

public class Mask {
    @Getter @Setter private Vector2 p0 = new Vector2(), p1 = new Vector2();

    public Mask() {

    }
    public void set(Mask mask) {
        setP0(mask.getP0());
        setP1(mask.getP1());
    }

    public Rectangle getRectangle() {
        Vector2 size = p1.cpy().sub(p0);
        return new Rectangle(p0.x, p0.y, size.x, size.y);
    }
}

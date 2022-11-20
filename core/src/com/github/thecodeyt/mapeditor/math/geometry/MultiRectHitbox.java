package com.github.thecodeyt.mapeditor.math.geometry;

import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.math.Mathf;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class MultiRectHitbox {
    @Getter @Setter private List<RectHitBox> hitBox = new ArrayList<>();

    public MultiRectHitbox(RectHitBox... hitBox) {
        this.hitBox = new ArrayList<>(List.of(hitBox));
    }
    public MultiRectHitbox(MultiRectHitbox... hitBox) {
        for (MultiRectHitbox multiBox : List.of(hitBox)) {
            this.hitBox.addAll(multiBox.getHitBox());
        }
    }

    public boolean isPointColliding(Vector2 point) {
        for (RectHitBox box : hitBox) {
            if(Mathf.isColliding(point, box.getPosition(), box.getPosition().cpy().add(box.getSize()))) {
                return true;
            }
        }
        return false;
    }
}

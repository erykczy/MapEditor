package com.github.thecodeyt.mapeditor.editor.scene.gameobject;

import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.Constants;
import com.github.thecodeyt.mapeditor.editor.scene.Scene;
import lombok.Getter;

public class CircleObject extends GameObject {
    @Getter private float radius;

    public CircleObject(Scene scene, Vector2 position, float radius) {
        super(scene, position.sub(radius, radius), new Vector2(radius*2, radius*2));
        this.radius = radius;
    }

    @Override
    public GameObject copy() {
        CircleObject circleObject = new CircleObject(scene, position.cpy(), radius);
        return circleObject;
    }

    @Override
    public void update() {
        super.update();
    }
    @Override
    public void draw() {
        super.draw();
        int segments = Math.min(Math.round(radius+40), Constants.MAX_CIRCLE_SEGMENTS);
        scene.getCamera().shapeRenderer.circle(position.x+radius, position.y+radius, this.radius, segments);
    }

    @Override
    public void setSize(Vector2 size) {
        this.size.x = size.x;
        this.size.y = this.size.x;
        this.radius = this.size.x/2F;
    }

}

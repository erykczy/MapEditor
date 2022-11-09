package com.github.thecodeyt.mapeditor.editor.scene.gameobject;

import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.Constants;
import com.github.thecodeyt.mapeditor.editor.scene.Scene;

public class CircleObject extends GameObject {
    public float radius;

    public CircleObject(Scene scene, Vector2 position, float radius) {
        super(scene, position.sub(radius, radius), new Vector2(radius*2, radius*2));
        this.radius = radius;
    }

    @Override
    public GameObject copy() {
        CircleObject circleObject = new CircleObject(this.scene, this.position.cpy(), this.radius);
        return circleObject;
    }

    @Override
    public void update() {
    }
    @Override
    public void draw() {
        super.draw();
        int segments = Math.min(Math.round(this.radius+40), Constants.MAX_CIRCLE_SEGMENTS);
        System.out.println("segments: "+segments);
        this.scene.camera.shapeRenderer.circle(this.position.x+this.radius, this.position.y+this.radius, this.radius, segments);
    }

    @Override
    public void setSize(Vector2 size) {
        this.size.x = size.x;
        this.size.y = this.size.x;
        this.radius = this.size.x/2F;
    }

}

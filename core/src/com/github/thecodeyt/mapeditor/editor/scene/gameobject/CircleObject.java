package com.github.thecodeyt.mapeditor.editor.scene.gameobject;

import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.scene.Scene;

public class CircleObject extends GameObject {
    public float radius;

    public CircleObject(Scene scene, Vector2 position, float radius) {
        super(scene, position.sub(radius, radius), new Vector2(radius*2, radius*2));
        this.radius = radius;
    }

    @Override
    public void update() {
        this.size.y = this.size.x;
        this.radius = this.size.x/2F;
    }

    @Override
    public void draw() {
        super.draw();
        this.scene.camera.shapeRenderer.circle(this.position.x+this.radius, this.position.y+this.radius, this.radius, 50);
    }
}

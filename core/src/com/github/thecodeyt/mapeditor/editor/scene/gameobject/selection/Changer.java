package com.github.thecodeyt.mapeditor.editor.scene.gameobject.selection;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.Constants;
import com.github.thecodeyt.mapeditor.math.HitBox;
import com.github.thecodeyt.mapeditor.math.Mathf;

public class Changer {
    public Vector2 position = new Vector2(0, 0);
    public float radius = 0;

    public HitBox getHitBox() {
        return new HitBox(this.position.cpy().sub(radius, radius), new Vector2(radius*2, radius*2));
    }
    public float getNewSize(Vector2 pointerPosition, boolean align, float oldSize) {
        double delta = Mathf.delta(this.position, pointerPosition).x;
        float new_size = oldSize;

        new_size += delta;
        if(align) {
            new_size = Mathf.align(new_size, Constants.GRID_SPACING);
        }

        return new_size < Constants.MIN_GAME_OBJECT_SIZE ? oldSize : new_size;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.circle(this.position.x, this.position.y, this.radius, 100);
    }
}

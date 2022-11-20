package com.github.thecodeyt.mapeditor.editor.scene.gameobject;

import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.Constants;
import com.github.thecodeyt.mapeditor.editor.scene.Scene;
import com.github.thecodeyt.mapeditor.math.geometry.RectHitBox;
import lombok.Getter;
import lombok.Setter;

public class GameObject {
    @Getter
    protected Scene scene;
    @Getter @Setter
    protected Vector2 position;
    @Getter @Setter
    protected Vector2 size;

    public GameObject(Scene scene, Vector2 position, Vector2 size) {
        this.scene = scene;
        this.position = position;
        this.size = size;
    }

    public RectHitBox getHitBox() {
        float offsetX = Constants.CHANGER_SIZE*size.x;
        float offsetY = Constants.CHANGER_SIZE*size.y;
        Vector2 h_pos = position.cpy().sub(offsetX, offsetY);
        Vector2 h_size = size.cpy().add(offsetX*2, offsetY*2);

        return new RectHitBox(h_pos, h_size);
    }
    public GameObject copy() {
        GameObject copy = new GameObject(scene, position.cpy(), size.cpy());
        return copy;
    }

    public void update() {

    }
    public void draw() {

    }

    /*
    public Vector2 getSize() {
        return this.size;
    }
    public void setSize(Vector2 size) {
        this.size = size;
    }
     */
}

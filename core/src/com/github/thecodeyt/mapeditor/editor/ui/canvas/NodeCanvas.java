package com.github.thecodeyt.mapeditor.editor.ui.canvas;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.Constants;
import com.github.thecodeyt.mapeditor.editor.ui.camera.CanvasCamera;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.element.propertiestab.node.Node;
import lombok.Getter;
import lombok.Setter;

public class NodeCanvas extends Canvas<Node> {
    @Getter @Setter private CanvasCamera camera;

    public NodeCanvas() {

    }
    public NodeCanvas(CanvasCamera camera) {
        this.camera = camera;
    }

    public void update(float delta) {
        for (Node node : getElements()) {
            node.update(delta);
        }
    }
    public void draw() {
        for (Node node : getElements()) {
            node.draw();
        }
    }

    public void createNewNode(Vector2 position) {
        Color color = getElements().size() % 2 == 0 ? Constants.NODE_COLOR_0 : Constants.NODE_COLOR_1;
        Node node = new Node(this, color, position);
        addElement(node);
    }
}

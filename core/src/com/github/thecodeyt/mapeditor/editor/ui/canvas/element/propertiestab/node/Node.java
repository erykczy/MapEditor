package com.github.thecodeyt.mapeditor.editor.ui.canvas.element.propertiestab.node;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.Constants;
import com.github.thecodeyt.mapeditor.editor.ui.camera.CanvasCamera;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.NodeCanvas;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.element.CanvasElement;
import com.github.thecodeyt.mapeditor.math.render.Drawf;
import lombok.Getter;
import lombok.Setter;

public class Node extends CanvasElement {
    @Getter private final NodeCanvas nodeCanvas;
    @Getter @Setter private String value;
    @Getter private Color color;
    @Getter @Setter private Vector2 position;

    public Node(NodeCanvas nodeCanvas, Color color, Vector2 position) {
        this(nodeCanvas, color, position, Constants.DEFAULT_NODE_VALUE);
    }
    public Node(NodeCanvas nodeCanvas, Color color, Vector2 position, String value) {
        super(nodeCanvas);
        setValue(value);
        this.nodeCanvas = nodeCanvas;
        this.color = color;
        this.position = position;
    }

    public void update(float delta) {

    }
    public void draw() {
        CanvasCamera camera = getNodeCanvas().getCamera();
        if(Drawf.isPresent(camera.shapeRenderer)) {
            camera.shapeRenderer.setColor(getColor());
            camera.shapeRenderer.rect(position.x, position.y, 5, 5);
            camera.shapeRenderer.flush();
        }
    }
}

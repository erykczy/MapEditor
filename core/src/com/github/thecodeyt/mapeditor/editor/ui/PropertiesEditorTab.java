package com.github.thecodeyt.mapeditor.editor.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.ui.camera.CanvasCamera;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.NodeCanvas;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.UICanvas;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.element.ui.UITab;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.prefab.propertiestab.PropTabContextMenuPrefab;
import com.github.thecodeyt.mapeditor.math.geometry.MultiRectHitbox;
import com.github.thecodeyt.mapeditor.math.input.Inputf;
import com.github.thecodeyt.mapeditor.math.render.Drawf;
import com.github.thecodeyt.mapeditor.math.render.Mask;
import lombok.Getter;

public class PropertiesEditorTab extends UITab {
    public CanvasCamera camera;
    @Getter private UICanvas uiCanvas;
    @Getter private NodeCanvas nodeCanvas;
    private boolean created = false;

    public PropertiesEditorTab(UICanvas canvas) {
        super(canvas);
        //super(canvas, new Vector2(), new Vector2());
        this.uiCanvas = new UICanvas();
        this.nodeCanvas = new NodeCanvas();
        this.camera = new CanvasCamera(getNodeCanvas(), getUiCanvas());
        this.uiCanvas.setCamera(this.camera);
        this.nodeCanvas.setCamera(this.camera);
    }

    @Override
    protected void create() {
        super.create();
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void handleContextMenu() {
        if(Inputf.justRightClick()) {
            this.getUiCanvas().setCurrentContextMenu(null);
            MultiRectHitbox h = getHitBox();
            Vector2 pointerPos = Inputf.getPointerPosition(camera.viewport);
            // if colliding with properties tab
            if(!getHitBox().isPointColliding(pointerPos)) {
                return;
            }

            getCanvas().addPrefab((new PropTabContextMenuPrefab(this.getUiCanvas(), getNodeCanvas())));
        }
        if(Gdx.input.isButtonJustPressed(0)) {
            this.getUiCanvas().setCurrentContextMenu(null);
        }
    }

    public void update(float delta) {
        super.update(delta);

        this.getUiCanvas().update(delta);
        getNodeCanvas().update(delta);

        // Context menu
        handleContextMenu();
    }
    @Override
    public void drawOutOfDrawingContext() {
        Drawf.addMaskLayer(camera, getPanel().getMask());

        camera.draw();

        Drawf.removeLastMaskLayer(camera);
    }
    public void resize(int width, int height) {
        camera.resize(width, height);
    }
}

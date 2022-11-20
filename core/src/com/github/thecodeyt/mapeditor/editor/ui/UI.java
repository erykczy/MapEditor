package com.github.thecodeyt.mapeditor.editor.ui;

import com.badlogic.gdx.Gdx;
import com.github.thecodeyt.mapeditor.editor.ui.camera.CanvasCamera;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.UICanvas;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.element.ui.UIElement;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.prefab.propertiestab.PropTabPrefab;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.prefab.ui.SceneContextMenuPrefab;
import com.github.thecodeyt.mapeditor.math.input.Inputf;
import com.github.thecodeyt.mapeditor.screen.EditorScreen;
import lombok.Getter;

public class UI {
    public CanvasCamera<UICanvas> camera;
    @Getter private UICanvas canvas;
    @Getter private EditorScreen editor;

    public UI(EditorScreen editor) {
        this.editor = editor;
        this.canvas = new UICanvas();
        this.camera = new CanvasCamera<UICanvas>(getCanvas());
        this.canvas.setCamera(this.camera);
    }
    private boolean created = false;
    private void create() {
        PropTabPrefab propertiesTabPrefab = new PropTabPrefab(getCanvas());
        getCanvas().addPrefab((propertiesTabPrefab));
    }
    private void handleContextMenu() {
        if(Inputf.justRightClick()) {
            getCanvas().setCurrentContextMenu(null);
            boolean collidingWithUiElement = false;
            for(UIElement element : getCanvas().getElements()) {
                if(element.getHitBox() == null) {
                    continue;
                }
                if(element.getHitBox().isPointColliding(Inputf.getPointerPosition(camera.viewport))) {
                    collidingWithUiElement = true;
                    break;
                }
            }
            // if clicking at scene
            if(!collidingWithUiElement) {
                getCanvas().addPrefab((new SceneContextMenuPrefab(getCanvas(), getEditor().getScene())));
            }
        }
        if(Gdx.input.isButtonJustPressed(0)) {
            getCanvas().setCurrentContextMenu(null);
        }
    }
    public void update(float delta) {
        if(!created) {
            created = true;
            create();
        }

        getCanvas().update(delta);

        // Context menu
        handleContextMenu();
    }

    public void draw() {
        camera.draw();
    }
    public void resize(int width, int height) {
        camera.resize(width, height);
    }
}

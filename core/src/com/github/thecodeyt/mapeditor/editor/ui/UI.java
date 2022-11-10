package com.github.thecodeyt.mapeditor.editor.ui;

import com.badlogic.gdx.Gdx;
import com.github.thecodeyt.mapeditor.editor.ui.camera.UICamera;
import com.github.thecodeyt.mapeditor.editor.ui.elements.UIElement;
import com.github.thecodeyt.mapeditor.editor.ui.elements.contextmenu.ContextMenu;
import com.github.thecodeyt.mapeditor.editor.ui.prefab.PropertyTabPrefab;
import com.github.thecodeyt.mapeditor.editor.ui.prefab.RightClickContextMenuPrefab;
import com.github.thecodeyt.mapeditor.editor.ui.prefab.UIPrefab;
import com.github.thecodeyt.mapeditor.math.input.Inputf;
import com.github.thecodeyt.mapeditor.screen.EditorScreen;

import java.util.ArrayList;
import java.util.List;

public class UI {
    public UICamera camera;
    public List<UIElement> elements = new ArrayList<>();
    public List<UIPrefab> prefabs = new ArrayList<>();
    public ContextMenu currentContextMenu = null;
    public EditorScreen editor;
    private boolean created = false;

    public UI(EditorScreen editor) {
        this.camera = new UICamera(this);
        this.editor = editor;
    }

    public void setCurrentContextMenu(ContextMenu panel) {
        removeElement(currentContextMenu);
        currentContextMenu = panel;
        if(panel != null) {
            addElement(currentContextMenu);
        }
    }

    private void create() {
        created = true;
        prefabs.add(new PropertyTabPrefab(this));
    }
    public void update(float delta) {
        if(!created) {
            create();
        }

        for (UIElement element : elements) {
            element.update(delta);
        }
        for (UIPrefab prefab : prefabs) {
            prefab.update();
        }

        // Context menu
        if(Gdx.input.isButtonJustPressed(1)) {
            setCurrentContextMenu(null);
            boolean collidingWithUiElement = false;
            for(UIElement element : elements) {
                if(element.getHitBox() == null) {
                    continue;
                }
                if(element.getHitBox().isPointColliding(Inputf.getPointerPosition(camera.viewport))) {
                    collidingWithUiElement = true;
                    break;
                }
            }
            if(!collidingWithUiElement) {
                prefabs.add(new RightClickContextMenuPrefab(this));
            }
        }
        if(Gdx.input.isButtonJustPressed(0)) {
            setCurrentContextMenu(null);
        }
    }
    public void draw() {
        camera.draw();
    }
    public void resize(int width, int height) {
        camera.resize(width, height);
    }

    // GETTERS SETTERS
    public void addElement(UIElement element) {
        this.elements.add(element);
    }
    public void removeElement(UIElement element) {
        this.elements.remove(element);
    }
}

package com.github.thecodeyt.mapeditor.editor.ui;

import com.badlogic.gdx.Gdx;
import com.github.thecodeyt.mapeditor.editor.ui.camera.UICamera;
import com.github.thecodeyt.mapeditor.editor.ui.elements.ContextMenu;
import com.github.thecodeyt.mapeditor.editor.ui.elements.UIElement;
import com.github.thecodeyt.mapeditor.editor.ui.prefab.RightClickContextMenuPrefab;
import com.github.thecodeyt.mapeditor.screen.EditorScreen;

import java.util.ArrayList;
import java.util.List;

public class UI {
    public UICamera camera;
    public List<UIElement> elements = new ArrayList<>();
    public ContextMenu currentContextMenu = null;
    public EditorScreen editor;

    public UI(EditorScreen editor) {
        this.editor = editor;
        this.camera = new UICamera(this);
    }

    public void setCurrentContextMenu(ContextMenu panel) {
        elements.remove(currentContextMenu);
        currentContextMenu = panel;
        if(panel != null) {
            elements.add(currentContextMenu);
        }
    }

    public void update(float delta) {
        for (UIElement element : elements) {
            element.update(delta);
        }

        // Context menu
        if(Gdx.input.isButtonJustPressed(1)) {
            new RightClickContextMenuPrefab(this);
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
}

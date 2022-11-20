package com.github.thecodeyt.mapeditor.editor.ui.canvas;

import com.github.thecodeyt.mapeditor.editor.ui.camera.CanvasCamera;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.element.CanvasElement;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.element.ui.contextmenu.UIContextMenu;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.prefab.CanvasPrefab;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Canvas<T extends CanvasElement> {
    @Getter @Setter protected CanvasCamera camera;
    @Getter protected List<T> elements = new ArrayList<>();
    @Getter protected List<CanvasPrefab> prefabs = new ArrayList<>();
    @Getter protected UIContextMenu currentContextMenu = null;

    public Canvas() {

    }
    public Canvas(CanvasCamera camera) {
        this.camera = camera;
    }

    public void update(float delta) {
        for (T element : getElements()) {
            element.update(delta);
        }
        for (CanvasPrefab prefab : getPrefabs()) {
            prefab.update(delta);
        }
    }
    public void draw() {
        for (T element : getElements()) {
            element.draw();
        }
    }
    public void drawOutOfDrawingContext() {
        for (T element : getElements())
                element.drawOutOfDrawingContext();
    }
    public void resize(int width, int height) {
        for (T element : getElements()) {
            element.resize(width, height);
        }
    }

    public void addElement(T element) {
        getElements().add(element);
    }
    public void removeElement(T element) {
        getElements().remove(element);
    }
    public void addPrefab(CanvasPrefab prefab) {
        getPrefabs().add(prefab);
    }
    public void removePrefab(CanvasPrefab prefab) {
        getPrefabs().remove(prefab);
    }
    public void moveElementToTheFore(T element) {
        this.elements.remove(element);
        this.elements.add(this.elements.size(), element);
    }
    public void moveElementToTheBack(T element) {
        this.elements.remove(element);
        this.elements.add(0, element);
    }
}

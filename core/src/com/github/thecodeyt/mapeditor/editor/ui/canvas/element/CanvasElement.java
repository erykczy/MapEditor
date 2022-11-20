package com.github.thecodeyt.mapeditor.editor.ui.canvas.element;

import lombok.Getter;

public abstract class CanvasElement<T> {
    @Getter private T canvas;

    public CanvasElement(T canvas) {
        this.canvas = canvas;
    }

    public abstract void draw();
    public void drawOutOfDrawingContext() {

    }
    public void resize(int width, int height) {

    }
    public void update(float delta) {

    }
}

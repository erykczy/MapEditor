package com.github.thecodeyt.mapeditor.editor.ui.canvas.prefab;

import lombok.Getter;

public abstract class CanvasPrefab<T> {
    @Getter protected T canvas;

    public CanvasPrefab(T canvas) {
        this.canvas = canvas;
    }
    public void update(float delta) {

    }
}

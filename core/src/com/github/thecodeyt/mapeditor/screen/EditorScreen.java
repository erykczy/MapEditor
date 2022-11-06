package com.github.thecodeyt.mapeditor.screen;

import com.badlogic.gdx.Screen;
import com.github.thecodeyt.mapeditor.editor.scene.Scene;
import com.github.thecodeyt.mapeditor.editor.ui.UI;

public class EditorScreen implements Screen {
    public Scene scene;
    public UI ui;

    @Override
    public void show() {
        scene = new Scene();
        ui = new UI(this);
    }

    @Override
    public void render(float delta) {
        // Updating
        ui.update(delta);
        scene.update(delta);

        // Drawing
        scene.draw();
        ui.draw();
    }

    @Override
    public void resize(int width, int height) {
        scene.resize(width, height);
        ui.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

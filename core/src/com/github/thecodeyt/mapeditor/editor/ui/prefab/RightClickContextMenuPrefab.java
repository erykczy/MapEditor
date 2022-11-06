package com.github.thecodeyt.mapeditor.editor.ui.prefab;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.Constants;
import com.github.thecodeyt.mapeditor.editor.ui.UI;
import com.github.thecodeyt.mapeditor.editor.ui.elements.ContextMenu;

import java.util.HashMap;

public class RightClickContextMenuPrefab extends UIPrefab {
    public RightClickContextMenuPrefab(UI ui) {
        super(ui);
        Vector2 position = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        Vector2 uiPosition = ui.camera.viewport.unproject(position.cpy());
        Vector2 scenePosition = ui.editor.scene.camera.viewport.unproject(position.cpy());

        HashMap<String, Runnable> options = new HashMap<>();
        options.put("New GameObject", () -> {
            ui.editor.scene.createNewCircleObject(scenePosition, Constants.DEFAULT_CIRCLE_OBJECT_RADIUS);
        });
        options.put("Go to the center", () -> {
            ui.editor.scene.camera.viewport.getCamera().position.x = 0;
            ui.editor.scene.camera.viewport.getCamera().position.y = 0;
        });

        ContextMenu contextMenu = new ContextMenu(ui, uiPosition, new Vector2(105, 15), 2, 1, 2, options);
        ui.setCurrentContextMenu(contextMenu);
    }
}

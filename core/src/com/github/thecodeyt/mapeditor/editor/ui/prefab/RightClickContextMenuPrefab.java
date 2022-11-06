package com.github.thecodeyt.mapeditor.editor.ui.prefab;

import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.Constants;
import com.github.thecodeyt.mapeditor.editor.scene.Scene;
import com.github.thecodeyt.mapeditor.editor.ui.UI;
import com.github.thecodeyt.mapeditor.editor.ui.elements.contextmenu.ContextMenu;
import com.github.thecodeyt.mapeditor.editor.ui.elements.contextmenu.ContextMenuOption;
import com.github.thecodeyt.mapeditor.math.input.Inputf;

import java.util.ArrayList;
import java.util.List;

public class RightClickContextMenuPrefab extends UIPrefab {
    public RightClickContextMenuPrefab(UI ui) {
        super(ui);
        Scene scene = ui.editor.scene;
        Vector2 uiPointerPosition = Inputf.getPointerPosition(ui.camera.viewport);
        Vector2 scenePointerPosition = Inputf.getPointerPosition(scene.camera.viewport);
        List<ContextMenuOption> options = new ArrayList<>();

        // ADDING OPTIONS
        if(scene.currentSelection != null && scene.currentSelection.gameObject.getHitBox().isPointColliding(scenePointerPosition)) {
            options.add(new ContextMenuOption("Delete", scene::removeSelectedGameObject));
            options.add(new ContextMenuOption("Duplicate", () -> {
                scene.duplicateGameObject(scene.currentSelection.gameObject);
            }));
        }
        options.add(new ContextMenuOption("New GameObject", () -> {
            scene.createNewCircleObject(scenePointerPosition, Constants.DEFAULT_CIRCLE_OBJECT_RADIUS);
        }));
        options.add(new ContextMenuOption("Go to the center", () -> {
            scene.camera.viewport.getCamera().position.x = 0;
            scene.camera.viewport.getCamera().position.y = 0;
        }));

        ContextMenu contextMenu = new ContextMenu(ui, uiPointerPosition, new Vector2(105, 15), 2, 1, 2, options);
        ui.setCurrentContextMenu(contextMenu);
    }
}

package com.github.thecodeyt.mapeditor.editor.ui.canvas.prefab.ui;

import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.Constants;
import com.github.thecodeyt.mapeditor.editor.scene.Scene;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.UICanvas;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.element.ui.contextmenu.UIContextMenu;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.prefab.CanvasPrefab;
import com.github.thecodeyt.mapeditor.math.ContextMenuOption;
import com.github.thecodeyt.mapeditor.math.input.Inputf;

import java.util.ArrayList;
import java.util.List;

public class SceneContextMenuPrefab extends CanvasPrefab<UICanvas> {
    public SceneContextMenuPrefab(UICanvas canvas, Scene scene) {
        super(canvas);
        Vector2 uiPointerPosition = Inputf.getPointerPosition(getCanvas().getCamera().viewport);
        Vector2 scenePointerPosition = Inputf.getPointerPosition(scene.getCamera().viewport);
        List<ContextMenuOption> options = new ArrayList<>();

        // ADDING OPTIONS
        if(scene.getCurrentSelection() != null && scene.getCurrentSelection().getGameObject().getHitBox().isPointColliding(scenePointerPosition)) {
            options.add(new ContextMenuOption("Delete", scene::removeSelectedGameObject));
            options.add(new ContextMenuOption("Duplicate", () -> {
                scene.duplicateGameObject(scene.getCurrentSelection().getGameObject());
            }));
        }
        options.add(new ContextMenuOption("New GameObject", () -> {
            scene.createNewCircleObject(scenePointerPosition, Constants.DEFAULT_CIRCLE_OBJECT_RADIUS);
        }));
        options.add(new ContextMenuOption("Go to the center", () -> {
            scene.getCamera().viewport.getCamera().position.x = 0;
            scene.getCamera().viewport.getCamera().position.y = 0;
        }));

        UIContextMenu contextMenu = new UIContextMenu(getCanvas(), uiPointerPosition, new Vector2(105, 15), 2, 1, 2, options);
        getCanvas().setCurrentContextMenu(contextMenu);
    }
}

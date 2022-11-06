package com.github.thecodeyt.mapeditor.editor.ui.prefab;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.thecodeyt.mapeditor.editor.Constants;
import com.github.thecodeyt.mapeditor.editor.ui.UI;
import com.github.thecodeyt.mapeditor.editor.ui.elements.UISizeChanger;
import com.github.thecodeyt.mapeditor.editor.ui.elements.UIPanel;
import com.github.thecodeyt.mapeditor.math.Axis;
import com.github.thecodeyt.mapeditor.math.Screenf;
import com.github.thecodeyt.mapeditor.math.input.Action;

public class PropertiesPanelPrefab extends UIPrefab {
    public static final float WIDTH = 0.2F;
    public UIPanel panel;
    public UISizeChanger widthChanger;

    public PropertiesPanelPrefab(UI ui) {
        super(ui);
        Viewport viewport = ui.camera.viewport;
        Vector2 positionCorner = Screenf.getBottomLeftCorner(viewport);
        Vector2 screenSize = Screenf.getScreenSize(viewport);

        panel = new UIPanel(ui, positionCorner, new Vector2(WIDTH*screenSize.x, screenSize.y), Constants.PANEL_BACKGROUND_COLOR);
        widthChanger = new UISizeChanger(ui, Action.CHANGING_WIDTH_OF_PROPERTY_PANEL, Axis.X, panel.size.x, panel.size.x, screenSize.x-panel.size.x);

        panel.elements.add(widthChanger);
        ui.elements.add(panel);
    }

    private void updatePositionAndSize() {
        Viewport viewport = ui.camera.viewport;
        Vector2 positionCorner = Screenf.getBottomLeftCorner(viewport);
        Vector2 screenSize = Screenf.getScreenSize(viewport);

        panel.position = positionCorner;
        panel.size = new Vector2(widthChanger.currentSize, screenSize.y);
        widthChanger.position = panel.position.cpy().add(panel.size.x, panel.size.y/2F);
    }

    @Override
    public void update() {
        super.update();
        this.updatePositionAndSize();
    }
}

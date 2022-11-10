package com.github.thecodeyt.mapeditor.editor.ui.prefab;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.thecodeyt.mapeditor.editor.Constants;
import com.github.thecodeyt.mapeditor.editor.ui.UI;
import com.github.thecodeyt.mapeditor.editor.ui.elements.UIPanel;
import com.github.thecodeyt.mapeditor.editor.ui.elements.UISizeChanger;
import com.github.thecodeyt.mapeditor.math.Axis;
import com.github.thecodeyt.mapeditor.math.Screenf;
import com.github.thecodeyt.mapeditor.math.input.ActionDesc;
import com.github.thecodeyt.mapeditor.math.input.Branch;

public class PropertyTabPrefab extends UIPrefab {
    public static final float WIDTH = 0.2F;
    public UIPanel tab;
    public UISizeChanger widthChanger;

    public PropertyTabPrefab(UI ui) {
        super(ui);

        this.createTab();
        this.createWidthChanger();
    }

    private void createWidthChanger() {
        Viewport viewport = ui.camera.viewport;
        Vector2 screenSize = Screenf.getScreenSize(viewport);

        widthChanger = new UISizeChanger(ui, new Vector2(), new ActionDesc(Branch.TAB, Branch.CHANGE_SIZE, Branch.X), Axis.X, tab.size.x, tab.size.x, screenSize.x-tab.size.x);

        tab.addElement(widthChanger);
    }
    private void createTab() {
        Viewport viewport = ui.camera.viewport;
        Vector2 positionCorner = Screenf.getBottomLeftCorner(viewport);
        Vector2 screenSize = Screenf.getScreenSize(viewport);

        tab = new UIPanel(ui, positionCorner, new Vector2(WIDTH*screenSize.x, screenSize.y), Constants.PANEL_BACKGROUND_COLOR);
        ui.addElement(tab);
    }
    private void updatePositionAndSize() {
        Viewport viewport = ui.camera.viewport;
        Vector2 positionCorner = Screenf.getBottomLeftCorner(viewport);
        Vector2 screenSize = Screenf.getScreenSize(viewport);

        tab.position = positionCorner;
        tab.size = new Vector2(widthChanger.currentSize, screenSize.y);
        widthChanger.position = tab.position.cpy().add(tab.size.x, tab.size.y/2F);
    }

    @Override
    public void update() {
        super.update();
        this.updatePositionAndSize();
    }
}

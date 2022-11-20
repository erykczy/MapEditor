package com.github.thecodeyt.mapeditor.editor.ui.canvas.element.ui;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.thecodeyt.mapeditor.editor.Constants;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.UICanvas;
import com.github.thecodeyt.mapeditor.math.Axis;
import com.github.thecodeyt.mapeditor.math.Screenf;
import com.github.thecodeyt.mapeditor.math.geometry.MultiRectHitbox;
import com.github.thecodeyt.mapeditor.math.input.ActionDesc;
import com.github.thecodeyt.mapeditor.math.input.Branch;
import lombok.Getter;
import lombok.Setter;

public class UITab extends UIElement {
    public static final float WIDTH = 0.2F;
    @Getter @Setter private UIPanel panel;
    @Getter @Setter UISizeChanger widthChanger;

    public UITab(UICanvas canvas) {
        super(canvas, Screenf.getBottomLeftCorner(canvas.getCamera().viewport), new Vector2());

        createPanel();
        createWidthChanger();
        //getCanvas().moveElementToTheFore(getPanel());

        //setPropertiesEditor(new PropertiesEditor(canvas, this));
        //getTab().getPanelCanvas().addElement(getPropertiesEditor());
    }

    @Override
    public MultiRectHitbox getHitBox() {
        return new MultiRectHitbox(getPanel().getHitBox(), getWidthChanger().getHitBox());
    }

    private void createPanel() {
        Viewport viewport = getCanvas().getCamera().viewport;
        Vector2 screenSize = Screenf.getScreenSize(viewport);

        setPanel(new UIPanel(getCanvas(), getPosition(), new Vector2(WIDTH*screenSize.x, screenSize.y), Constants.PANEL_BACKGROUND_COLOR));
        //getCanvas().addElement(getPanel());
    }
    private void createWidthChanger() {
        Viewport viewport = getCanvas().getCamera().viewport;
        Vector2 screenSize = Screenf.getScreenSize(viewport);

        setWidthChanger(new UISizeChanger(getCanvas(), new Vector2(), Constants.PANEL_BACKGROUND_COLOR.cpy().sub(0.02F, 0.02F, 0.02F, 0F), new ActionDesc(Branch.TAB, Branch.CHANGE_SIZE, Branch.X), Axis.X, getPanel().getSize().x, getPanel().getSize().x, screenSize.x-getPanel().getSize().x/2F));

        //getCanvas().addElement(getWidthChanger());
    }

    private void updatePositionAndSize() {
        Viewport viewport = getCanvas().getCamera().viewport;
        Vector2 positionCorner = Screenf.getBottomLeftCorner(viewport);
        Vector2 screenSize = Screenf.getScreenSize(viewport);

        getPanel().setPosition(positionCorner);
        getPanel().setSize(new Vector2(widthChanger.getCurrentSize(), screenSize.y));
        getWidthChanger().setPosition(getPanel().getPosition().cpy().add(getPanel().getSize().x, getPanel().getSize().y / 2F));
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        getWidthChanger().update(delta);
        getPanel().update(delta);

        updatePositionAndSize();
    }

    @Override
    public void draw() {
        getWidthChanger().draw();
            getPanel().draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        getWidthChanger().resize(width, height);
        getPanel().resize(width, height);
    }
}

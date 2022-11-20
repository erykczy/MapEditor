package com.github.thecodeyt.mapeditor.editor.ui.canvas.element.ui.contextmenu;

import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.Constants;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.UICanvas;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.element.ui.UIButton;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.element.ui.UIPanel;
import com.github.thecodeyt.mapeditor.math.ContextMenuOption;

import java.util.List;

public class UIContextMenu extends UIPanel {
    public UIContextMenu(UICanvas canvas, Vector2 position, Vector2 optionSize, float spacingX, float spacingY, float startSpacingY, List<ContextMenuOption> options) {
        super(canvas, position, new Vector2(optionSize.x+spacingX*2, options.size()*(spacingY+optionSize.y)+startSpacingY), Constants.CONTEXT_MENU_BACKGROUND_COLOR);
        position.y -= this.size.y;

        float optionY = startSpacingY;
        for (ContextMenuOption option : options) {
            UIButton button = new UIButton(getPanelCanvas(), position.cpy().add(spacingX, this.size.y-optionSize.y).sub(0, optionY), optionSize, Constants.CONTEXT_MENU_BACKGROUND_COLOR, Constants.CONTEXT_MENU_BACKGROUND_COLOR.cpy().add(Constants.HOVER_COLOR), Constants.TEXT_COLOR, option.name, new Vector2(2, 2), option.onClick);

            getPanelCanvas().addElement(button);
            optionY += optionSize.y+spacingY;
        }
    }
}

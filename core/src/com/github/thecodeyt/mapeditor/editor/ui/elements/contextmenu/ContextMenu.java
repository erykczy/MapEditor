package com.github.thecodeyt.mapeditor.editor.ui.elements.contextmenu;

import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.Constants;
import com.github.thecodeyt.mapeditor.editor.ui.UI;
import com.github.thecodeyt.mapeditor.editor.ui.elements.UIButton;
import com.github.thecodeyt.mapeditor.editor.ui.elements.UIPanel;

import java.util.List;

public class ContextMenu extends UIPanel {
    public ContextMenu(UI ui, Vector2 position, Vector2 optionSize, float spacingX, float spacingY, float startSpacingY,  List<ContextMenuOption> options) {
        super(ui, position, new Vector2(optionSize.x+spacingX*2, options.size()*(spacingY+optionSize.y)+startSpacingY), Constants.CONTEXT_MENU_BACKGROUND_COLOR);
        position.y -= this.size.y;

        float optionY = startSpacingY;
        for (ContextMenuOption option : options) {
            UIButton button = new UIButton(ui, position.cpy().add(spacingX, this.size.y-optionSize.y).sub(0, optionY), optionSize, Constants.CONTEXT_MENU_BACKGROUND_COLOR, Constants.HOVER_COLOR, Constants.TEXT_COLOR, option.name, option.onClick);

            elements.add(button);
            optionY += optionSize.y+spacingY;
        }
    }
}

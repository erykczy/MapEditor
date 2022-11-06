package com.github.thecodeyt.mapeditor.editor.ui.elements;

import com.badlogic.gdx.math.Vector2;
import com.github.thecodeyt.mapeditor.editor.Constants;
import com.github.thecodeyt.mapeditor.editor.ui.UI;

import java.util.ArrayList;
import java.util.HashMap;

public class ContextMenu extends Panel {
    public ContextMenu(UI ui, Vector2 position, Vector2 optionSize, float spacingX, float spacingY, float startSpacingY,  HashMap<String, Runnable> options) {
        super(ui, position, new Vector2(optionSize.x+spacingX*2, options.size()*(spacingY+optionSize.y)+startSpacingY), Constants.CONTEXT_MENU_COLOR);
        position.y -= this.size.y;

        float optionY = startSpacingY;
        ArrayList<String> keys = new ArrayList<>(options.keySet());
        for(int i = keys.size()-1; i >= 0; i--) {
            String option = keys.get(i);
            Runnable runnable = options.get(option);

            Button button = new Button(ui, position.cpy().add(spacingX, this.size.y-optionSize.y).sub(0, optionY), optionSize, Constants.CONTEXT_MENU_COLOR, Constants.HOVER_COLOR, Constants.TEXT_COLOR, option, runnable);

            elements.add(button);
            optionY += optionSize.y+spacingY;
        }
    }
}

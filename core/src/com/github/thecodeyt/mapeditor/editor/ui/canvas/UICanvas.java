package com.github.thecodeyt.mapeditor.editor.ui.canvas;

import com.github.thecodeyt.mapeditor.editor.ui.camera.CanvasCamera;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.element.ui.UIElement;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.element.ui.contextmenu.UIContextMenu;
import lombok.Getter;

public class UICanvas extends Canvas<UIElement> {
    @Getter private UIContextMenu currentContextMenu = null;

    public UICanvas() {

    }
    public UICanvas(CanvasCamera<UICanvas> camera) {
        this.camera = camera;
    }

    public void setCurrentContextMenu(UIContextMenu contextMenu) {
        removeElement(currentContextMenu);
        currentContextMenu = contextMenu;
        if(contextMenu != null) {
            addElement(contextMenu);
        }
    }
}

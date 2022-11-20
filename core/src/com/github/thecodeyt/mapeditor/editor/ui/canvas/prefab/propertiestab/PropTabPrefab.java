package com.github.thecodeyt.mapeditor.editor.ui.canvas.prefab.propertiestab;

import com.github.thecodeyt.mapeditor.editor.ui.PropertiesEditorTab;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.UICanvas;
import com.github.thecodeyt.mapeditor.editor.ui.canvas.prefab.CanvasPrefab;

public class PropTabPrefab extends CanvasPrefab<UICanvas> {
    //@Getter @Setter private PropertiesEditor propertiesEditor;

    public PropTabPrefab(UICanvas canvas) {
        super(canvas);

        //Vector2 positionCorner = Screenf.getBottomLeftCorner(canvas.getCamera().viewport);
        PropertiesEditorTab tab = new PropertiesEditorTab(canvas);

        canvas.addElement(tab);
    }
}

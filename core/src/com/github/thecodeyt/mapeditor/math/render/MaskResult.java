package com.github.thecodeyt.mapeditor.math.render;

import com.badlogic.gdx.math.Rectangle;
import lombok.Getter;
import lombok.Setter;

public class MaskResult {
    @Getter @Setter private Rectangle rectangle = new Rectangle();
    @Getter @Setter private boolean pushed = false;

}

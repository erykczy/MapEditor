package com.github.thecodeyt.mapeditor.math.input;

public enum Action {
    NONE(-1),
    MOVING_GAME_OBJECT(0),
    CHANGING_WIDTH_OF_GAME_OBJECT(0),
    CHANGING_HEIGHT_OF_GAME_OBJECT(0),
    CHANGING_WIDTH_OF_PROPERTY_PANEL(1);

    public int groupID;
    private Action(int groupID) {
        this.groupID = groupID;
    }
}

package com.github.thecodeyt.mapeditor.math;

public class ContextMenuOption {
    public String name;
    public Runnable onClick;

    public ContextMenuOption(String name, Runnable onClick) {
        this.name = name;
        this.onClick = onClick;
    }
}

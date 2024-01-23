package com.yuanren.tvinteractions.log;

public enum ActionType {
    TYPE_ACTION_DIRECTION("D-Pad Action"),
    TYPE_ACTION_LEFT("D-Pad Left"),
    TYPE_ACTION_RIGHT("D-Pad Right"),
    TYPE_ACTION_UP("D-Pad Up"),
    TYPE_ACTION_DOWN("D-Pad Down"),
    TYPE_ACTION_ENTER("Enter/Ok"),
    TYPE_ACTION_BACK("Back"),
    TYPE_ACTION_VOLUME("Change volume");
    private final String name;

    private ActionType(String name) {
        this.name = name;
    }

}

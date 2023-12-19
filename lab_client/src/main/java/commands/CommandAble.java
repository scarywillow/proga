package commands;

import exceptions.InvalidArgumentException;

import util.Request;

public abstract class CommandAble {
    public static String name = "abstract";
    public static String description = "abstract command";

    public abstract Request getRequest(String arg) throws InvalidArgumentException;

    String getName() {
        return name;
    }

    String getDescription() {
        return description;
    }
}

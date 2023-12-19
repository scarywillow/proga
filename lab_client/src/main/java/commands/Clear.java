package commands;

import util.Request;

public class Clear extends CommandAble {
    final public static String name = "clear";
    final public static String description = "очистить коллекцию";

    @Override
    public Request getRequest(String arg) {
        return new Request(name);
    }

    @Override
    public String getDescription() {
        return description;
    }
}

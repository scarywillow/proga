package commands;

import util.Request;

public class FieldAscending extends CommandAble {
    final public static String name = "field_ascending";
    final public static String description = "Вывести fieldAscending";

    @Override
    public Request getRequest(String arg) {
        return new Request(name);
    }

    public String getDescription() {
        return description;
    }
}

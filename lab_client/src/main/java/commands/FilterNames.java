package commands;

import util.Request;

public class FilterNames extends CommandAble {
    final public static String name = "filter_names";
    final public static String description = "Отфильтровать имена";

    @Override
    public Request getRequest(String arg) {
        return new Request(name);
    }

    public String getDescription() {
        return description;
    }
}

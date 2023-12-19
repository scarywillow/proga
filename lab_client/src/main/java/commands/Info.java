package commands;

import exceptions.InvalidArgumentException;
import util.Request;

public class Info extends CommandAble {
    final public static String name = "info";
    final public static String description = "вывести в стандартный поток вывода информацию о коллекции";

    @Override
    public Request getRequest(String arg) {
        return new Request(name);
    }

    public String getDescription() {
        return description;
    }
}

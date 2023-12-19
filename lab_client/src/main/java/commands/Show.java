package commands;

import util.Request;

public class Show extends CommandAble{
    final public static String name = "show";
    final public static String description = "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";

    @Override
    public Request getRequest(String arg) {
        return new Request(name);
    }

    public String getDescription() {
        return description;
    }
}

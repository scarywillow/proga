package commands;

import content.City;
import exceptions.InvalidArgumentException;
import util.Request;

public class UpdateId extends CommandAble{
    final public static String name = "update_id";
    final public static String description = "обновить значение элемента коллекции, id которого равен заданному";

    @Override
    public Request getRequest(String arg) throws InvalidArgumentException {
        long id;
        try {
            id = Long.parseLong(arg);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException("Эта команда требует аргумент: id");
        }
        return new Request(name, arg, City.prompt());
    }

    public String getDescription() {
        return description;
    }
}

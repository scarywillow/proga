package commands;

import content.City;
import exceptions.InvalidArgumentException;
import util.Request;

public class ReplaceIfLow  extends CommandAble {
    final public static String name = "replace_if_low";
    final public static String description = "заменить значение по ключу, если новое значение меньше старого";

    @Override
    public Request getRequest(String arg) throws InvalidArgumentException {

        if (arg == null) {
            throw new InvalidArgumentException("Эта команда требует аргумент: ключ элемента коллекции");
        }

        return new Request(name, arg, City.prompt());
    }

    public String getDescription() {
        return description;
    }
}

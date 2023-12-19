package commands;

import content.City;
import exeptions.UserNotFoundException;
import util.Auth;
import util.CollectionManager;
import util.Request;

public class FieldAscending implements CommandAble {
    CollectionManager collection;
    final public static String description = "вывести элементы коллекции в порядке возрастания";

    /**
     * Constructor of print_ascending command
     *
     * @param collection CollectionManager instance
     */
    public FieldAscending(CollectionManager collection) {
        this.collection = collection;
    }

    @Override
    public String run(Request req) {
        try {
            if (!Auth.checkRequest(req)) return "Ошибка авторизации: неверный пароль";
        } catch (UserNotFoundException e) {
            return "Ошибка авторизации: юзер не найден";
        }
        return collection.getAscending();
    }

    public String getDescription() {
        return description;
    }
}

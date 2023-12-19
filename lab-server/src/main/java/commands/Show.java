package commands;

import content.City;
import exeptions.UserNotFoundException;
import util.Auth;
import util.CollectionManager;
import util.Request;

public class Show implements CommandAble {
    CollectionManager collection;
    final public static String description = "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";


    public Show(CollectionManager collection) {
        this.collection = collection;
    }

    @Override
    public String run(Request req) {
        try {
            if (!Auth.checkRequest(req)) return "Ошибка авторизации: неверный пароль";
        } catch (UserNotFoundException e) {
            return "Ошибка авторизации: юзер не найден";
        }

        return collection.toString();
    }

    public String getDescription() {
        return description;
    }
}

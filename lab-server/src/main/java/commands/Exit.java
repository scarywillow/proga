package commands;

import content.City;
import exeptions.UserNotFoundException;
import util.Auth;
import util.CollectionManager;
import util.Request;

public class Exit implements CommandAble {
    CollectionManager collection;
    final public static String description = "завершить программу";

    public Exit(CollectionManager collection) {
        this.collection = collection;
    }

    @Override
    public String run(Request req) {
        try {
            if (!Auth.checkRequest(req)) return "Ошибка авторизации: неверный пароль";
        } catch (UserNotFoundException e) {
            return "Ошибка авторизации: юзер не найден";
        }
        return null;
    }

    public String getDescription() {
        return description;
    }
}

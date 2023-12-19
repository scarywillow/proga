package commands;

import content.City;
import exeptions.UserNotFoundException;
import util.Auth;
import util.CollectionManager;
import util.Request;

public class Clear implements CommandAble {
    CollectionManager collection;
    final public static String description = "очистить коллекцию";


    public Clear(CollectionManager collection) {
        this.collection = collection;
    }

    @Override
    public String run(Request req) {
        try {
            if (!Auth.checkRequest(req)) return "Ошибка авторизации: неверный пароль";
        } catch (UserNotFoundException e) {
            return "Ошибка авторизации: юзер не найден";
        }
        if(collection.clear(req.getLogin())) {
            return "Коллекция очищена";
        } else {
            return "Не очищена";
        }
    }

    public String getDescription() {
        return description;
    }
}

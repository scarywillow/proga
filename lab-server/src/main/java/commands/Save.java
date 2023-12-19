package commands;

import content.City;
import exeptions.UserNotFoundException;
import util.Auth;
import util.CollectionManager;
import util.Request;

public class Save implements CommandAble {
    CollectionManager collection;
    final public static String description = "сохранить коллекцию в файл";

    public Save(CollectionManager collection) {
        this.collection = collection;
    }

    @Override
    public String run(Request req) {
        try {
            if (!Auth.checkRequest(req)) return "Ошибка авторизации: неверный пароль";
        } catch (UserNotFoundException e) {
            return "Ошибка авторизации: юзер не найден";
        }
//        collection.save();
        System.out.println("Сохранено");
        return null;
    }

    public String getDescription() {
        return description;
    }
}

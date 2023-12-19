package commands;


import content.City;
import exeptions.InvalidArgumentException;
import exeptions.UserNotFoundException;
import util.Auth;
import util.CollectionManager;
import util.Request;

public class RemoveLowerKey implements CommandAble{
    CollectionManager collection;
    final public static String description = "удалить из коллекции все элементы, превышающие заданный";


    public RemoveLowerKey(CollectionManager collection) {
        this.collection = collection;
    }


    @Override
    public String run(Request req) throws InvalidArgumentException {

        try {
            if (!Auth.checkRequest(req)) return "Ошибка авторизации: неверный пароль";
        } catch (UserNotFoundException e) {
            return "Ошибка авторизации: юзер не найден";
        }


        return "Удалено элементов: " + collection.removeLower(req.getObj(), req.getLogin());
    }

    public String getDescription() {
        return description;
    }
}

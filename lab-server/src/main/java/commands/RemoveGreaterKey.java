package commands;


import content.City;
import exeptions.InvalidArgumentException;
import exeptions.UserNotFoundException;
import util.Auth;
import util.CollectionManager;
import util.Request;

public class RemoveGreaterKey implements CommandAble{
    CollectionManager collection;
    final public static String description = "удалить из коллекции все элементы, ключ которых превышает заданный";


    public RemoveGreaterKey(CollectionManager collection) {
        this.collection = collection;
    }


    @Override
    public String run(Request req) throws InvalidArgumentException {
        try {
            if (!Auth.checkRequest(req)) return "Ошибка авторизации: неверный пароль";
        } catch (UserNotFoundException e) {
            return "Ошибка авторизации: юзер не найден";
        }
        if (req.getArg() == null){
            throw new InvalidArgumentException("Эта команда требует аргумент: ключ элемента коллекции");
        }

        return "Удалено элементов: " + collection.removeGreaterKey(req.getArg(), req.getLogin());
    }

    public String getDescription() {
        return description;
    }
}

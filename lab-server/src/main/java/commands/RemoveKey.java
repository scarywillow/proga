package commands;


import content.City;
import exeptions.InvalidArgumentException;
import exeptions.UserNotFoundException;
import util.Auth;
import util.CollectionManager;
import util.Request;

public class RemoveKey implements CommandAble{
    CollectionManager collection;
    final public static String description = "удалить элемент из коллекции по его ключу";

    public RemoveKey(CollectionManager collection) {
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
        if (!collection.contains(req.getArg())) {
            throw new InvalidArgumentException("Элемента с таким ключом не существует");
        }
        collection.removeKey(req.getArg(), req.getLogin());
        return "Успешно удалено";
    }

    public String getDescription() {
        return description;
    }
}

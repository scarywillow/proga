package commands;


import content.City;
import exeptions.InvalidArgumentException;
import exeptions.UserNotFoundException;
import util.Auth;
import util.CollectionManager;
import util.Request;

public class ReplaceIfLow implements CommandAble{
    CollectionManager collection;

    final public static String description = "заменить значение по ключу, если новое значение больше старого";

    public ReplaceIfLow(CollectionManager collection) {
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
            throw new InvalidArgumentException("Элемент с таким ключом не существует");
        }

        return collection.replaceIfLow(req.getArg(), req.getObj());
    }

    public String getDescription() {
        return description;
    }
}

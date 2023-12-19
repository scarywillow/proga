package commands;


import content.City;
import exeptions.InvalidArgumentException;
import exeptions.UserNotFoundException;
import util.Auth;
import util.CollectionManager;
import util.Request;

public class Insert implements CommandAble {
    CollectionManager collection;
    final public static String description = "добавить новый элемент с заданным ключом";

    public Insert(CollectionManager collection) {
        this.collection = collection;
    }

    @Override
    public String run(Request req) throws InvalidArgumentException {

        try {
            if (!Auth.checkRequest(req)) return "Ошибка авторизации: неверный пароль";
        } catch (UserNotFoundException e) {
            return "Ошибка авторизации: юзер не найден";
        }


//        System.out.println("insert" + arg + obj);
        if (req.getArg() == null) {
            throw new InvalidArgumentException("Эта команда требует аргумент: ключ элемента коллекции");
        }
        if (collection.contains(req.getArg())) {
            throw new InvalidArgumentException("Элемент с таким ключом уже существует");
        }
        City obj = req.getObj();
        if (obj == null) {
            obj = City.prompt();
        }
        collection.insert(req.getArg(), obj, req.getLogin());
        return "Город успешно добавлен";
    }

    public String getDescription() {
        return description;
    }
}

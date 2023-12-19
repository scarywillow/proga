package commands;


import content.City;
import exeptions.InvalidArgumentException;
import exeptions.UserNotFoundException;
import util.Auth;
import util.CollectionManager;
import util.Request;

public class UpdateId implements CommandAble {
    CollectionManager collection;

    final public static String description = "обновить значение элемента коллекции, id которого равен заданному";

    public UpdateId(CollectionManager collection) {
        this.collection = collection;
    }

    @Override
    public String run(Request req) throws InvalidArgumentException {
        try {
            if (!Auth.checkRequest(req)) return "Ошибка авторизации: неверный пароль";
        } catch (UserNotFoundException e) {
            return "Ошибка авторизации: юзер не найден";
        }

        Integer id;
        try {
            id = Integer.parseInt(req.getArg());
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException("Эта команда требует аргумент: id");
        }
        if (!collection.containsID(id)) {
            throw new InvalidArgumentException("Элемента с таким id нет");
        }

//        collection.update(id, req.getObj());
        return "Город успешно отредактирован";
    }

    public String getDescription() {
        return description;
    }
}

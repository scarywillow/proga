package commands;


import content.City;
import exeptions.InvalidArgumentException;
import exeptions.UserNotFoundException;
import util.Auth;
import util.CollectionManager;
import util.Request;

import java.util.Map;

public class Help implements CommandAble {
    final public static String description = "вывести справку по доступным командам";
    final public static String name = "help";

    private Map<String, CommandAble> commands;


    public Help(Map<String, CommandAble> commands) {
        this.commands = commands;
    }


    @Override
    public String run(Request req) throws InvalidArgumentException {
        try {
            if (!Auth.checkRequest(req)) return "Ошибка авторизации: неверный пароль";
        } catch (UserNotFoundException e) {
            return "Ошибка авторизации: юзер не найден";
        }
        String resp = "";
        for (String key : commands.keySet()) {
            resp += key + ": " + commands.get(key).getDescription() + "\n";
        }
        return resp;
    }

    public String getDescription() {
        return description;
    }
}

package util;

import commands.*;
import exceptions.InvalidArgumentException;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private Map<String, CommandAble> commands = new HashMap<>();

    public CommandManager() {
        commands.put(Clear.name, new Clear());
        commands.put(Info.name, new Info());
        commands.put(Show.name, new Show());
        commands.put(Insert.name, new Insert());
        commands.put(UpdateId.name, new UpdateId());
        commands.put(RemoveKey.name, new RemoveKey());
        commands.put(ExecuteScript.name, new ExecuteScript(this));
        //commands.put(Exit.name, new Exit());
        commands.put(FieldAscending.name, new FieldAscending());
        commands.put(FilterNames.name, new FilterNames());
        commands.put(RemoveGreaterKey.name, new RemoveGreaterKey());
        commands.put(RemoveLowerKey.name, new RemoveLowerKey());
        commands.put(ReplaceIfLow.name, new ReplaceIfLow());
        commands.put(Help.name, new Help(commands));

    }


    public Request run(String[] args) {
        try {
            return commands.get(args[0]).getRequest(args.length > 1 ? args[1] : null);
        } catch (NullPointerException e) {
            if (!args[0].isEmpty()) {
                System.out.println("Нет такой команды. Вызовите help для справки по командам.");
            }
            return null;
        } catch (InvalidArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}

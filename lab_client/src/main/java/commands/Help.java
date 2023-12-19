package commands;

import util.Request;

import java.util.Map;

public class Help extends CommandAble {
    final public static String description = "вывести справку по доступным командам";
    final public static String name = "help";


    private Map<String, CommandAble> commands;


    public Help(Map<String, CommandAble> commands) {
        this.commands = commands;
    }

    @Override
    public Request getRequest(String arg) {
        for (String key : commands.keySet()) {
            System.out.println("\u001B[34m" + key + ": " + "\u001B[0m" + commands.get(key).getDescription());
        }
        return null;
    }

    public String getDescription() {
        return description;
    }
}

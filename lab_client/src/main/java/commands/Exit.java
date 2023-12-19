package commands;

import util.Request;

public class Exit extends CommandAble {
    final public static String name = "exit";
    final public static String description = "завершить программу";

    @Override
    public Request getRequest(String arg) {
        System.out.println("Выход из программы");
        return new Request("exit");
    }

    public String getDescription() {
        return description;
    }
}
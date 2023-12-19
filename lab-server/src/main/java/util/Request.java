package util;

import content.City;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 322L;
    private String command;
    private String arg;
    private City obj;
    private String login;
    private String password;

    public Request(String command) {
        this.command = command;
    }

    public Request(String command, String arg) {
        this.command = command;
        this.arg = arg;
    }

    public Request(String command, String arg, City obj) {
        this.command = command;
        this.arg = arg;
        this.obj = obj;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Request{" +
                "command='" + command + '\'' +
                ", arg='" + arg + '\'' +
                ", obj=" + obj +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getCommand() {
        return command;
    }

    public String getArg() {
        return arg;
    }

    public City getObj() {
        return obj;
    }
}

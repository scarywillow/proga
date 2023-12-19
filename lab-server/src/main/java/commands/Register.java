package commands;

import content.City;
import exeptions.InvalidArgumentException;
import util.Auth;
import util.Request;

public class Register implements CommandAble {
    final public static String description = null;

    @Override
    public String run(Request req) {
        System.out.println(req);
        if (Auth.register(req.getLogin(), req.getPassword())) {
            return "true";
        } else {
            return "false";
        }
    }


    public String getDescription() {
        return description;
    }

}
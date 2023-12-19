package commands;

import exeptions.UserNotFoundException;
import util.Auth;
import util.Request;

public class Login implements CommandAble {
    final public static String description = null;

    @Override
    public String run(Request req) {
        try {
            if (Auth.checkRequest(req)) {
                return "true";
            } else {
                return "false";
            }
        } catch (UserNotFoundException e) {
            return "User not found";
        }
    }

    public String getDescription() {
        return description;
    }

}

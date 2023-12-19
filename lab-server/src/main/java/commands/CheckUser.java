package commands;

import util.DataBase;
import util.Request;

public class CheckUser implements CommandAble {
    final public static String description = null;

    @Override
    public String run(Request req) {
        System.out.println(req);
         if (DataBase.getInstance().getUser(req.getArg()) != null) {
            System.out.println("yes");
            return "true";
        } else {
            System.out.println("no");
            return "false";
        }

    }

    public String getDescription() {
        return description;
    }
}

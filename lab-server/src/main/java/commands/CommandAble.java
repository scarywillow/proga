package commands;


import content.City;
import exeptions.DBError;
import exeptions.InvalidArgumentException;
import util.Request;

public interface CommandAble {
    String run(Request req) throws InvalidArgumentException, DBError;
    String getDescription();
}

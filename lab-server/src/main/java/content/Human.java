package content;


import exeptions.InvalidParameterException;
import util.IOManager;

import java.io.Serializable;

public class Human implements Serializable {
    private static final long serialVersionUID = 322L;
    private Integer height; //Значение поля должно быть больше 0

    public Human(Integer height) {
        this.height = height;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public static Human prompt() {
        Integer height = promptHeight();
        return new Human(height);
    }

    public static Integer parseHeight(String str) throws InvalidParameterException {
        int height;
        try {
            height = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("Неверный формат");
        }
        if (height <= 0) {
            throw new InvalidParameterException("Значение должно быть больше 0");
        }
        return height;
    }

    private static Integer promptHeight() {
        try {
            return parseHeight(IOManager.prompt("Рост: "));
        } catch (InvalidParameterException e) {
            System.out.println(e.getMessage());
            return promptHeight();
        }
    }

    @Override
    public String toString() {
        return "\n Рост: " + height;
    }

    public String getCSVHuman() {
        return Integer.toString(height);
    }

}


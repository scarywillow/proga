package content;


import exeptions.InvalidParameterException;
import util.IOManager;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private static final long serialVersionUID = 322L;
    private Double x; //Поле не может быть null
    private int y;

    public Coordinates(Double x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Coordinates prompt() {
        Double x = promptX();
        Integer y = promptY();
        return new Coordinates(x, y);
    }

    public static Double parseX(String str) throws InvalidParameterException {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("Неверный формат");
        }
    }

    public static Double promptX() {
        try {
            return parseX(IOManager.prompt("Координаты: \n Введите число\n x: "));
        } catch (InvalidParameterException e) {
            System.out.println(e.getMessage());
            return promptX();
        }
    }

    public static Integer parseY(String str) throws InvalidParameterException {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("Неверный формат");
        }
    }

    public static Integer promptY() {
        try {
            return parseY(IOManager.prompt("Координаты: \n Введите число\n y: "));
        } catch (InvalidParameterException e) {
            System.out.println(e.getMessage());
            return promptY();
        }
    }


    @Override
    public String toString() {
        return "\nКоординаты: " +
                "\n x: " + x +
                "\n y: " + y;
    }
}

package content;


import exeptions.InvalidParameterException;
import util.IOManager;

public enum Government {
    DESPOTISM,
    COMMUNISM,
    NOOCRACY;

    public static Government prompt() {
        return (Government) IOManager.enumPrompt(Government.values(), "Устройство государства", "");
    }

    public static Government parse(String str) throws InvalidParameterException {
        if (str.equals("null")) {
            return null;
        }
        try {
            return Government.valueOf(str);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Нет такого варианта");
        }
    }
}

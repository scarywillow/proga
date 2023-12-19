package content;

import exeptions.InvalidParameterException;
import org.apache.commons.lang3.ArrayUtils;
import util.IOManager;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Objects;

public class City implements Serializable {
    private static final long serialVersionUID = 322L;
    private static int lastId = 1;
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final Coordinates coordinates; //Поле не может быть null
    private  LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private final Double area; //Значение поля должно быть больше 0, Поле не может быть null
    private final Long population; //Значение поля должно быть больше 0, Поле не может быть null
    private final Long metersAboveSeaLevel;
    private double agglomeration;
    private final Government government; //Поле может быть null
    private final StandardOfLiving standardOfLiving; //Поле не может быть null
    private final Human governor; //Поле может быть null
    private String owner = "";

    public City(String name, Coordinates coordinates, LocalDate localDate, Double area, Long population, Long metersAboveSeaLevel,
                double agglomeration, Government government, StandardOfLiving standardOfLiving, Human governor,
                Integer id) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = localDate.now();
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.agglomeration = agglomeration;
        this.government = government;
        this.standardOfLiving = standardOfLiving;
        this.governor = governor;
        this.id = id;

    }

    public String getName() {
        return name;
    }

    public Double getArea() {
        return area;
    }

    public double getAgglomeration() {
        return agglomeration;
    }

    public Integer getId() {
        return id;
    }

    public Long getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }

    public StandardOfLiving getStandardOfLiving() {
        return standardOfLiving;
    }

    public void setAgglomeration(double agglomeration) {
        this.agglomeration = agglomeration;
    }

    public Long getPopulation() {
        return population;
    }

    public Government getGovernment() {
        return government;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setID(Integer id) {
        this.id = id;
    }

    public Human getGovernor() {
        return governor;
    }

    public City(String name, Coordinates coordinates, Double area, Long population, Long metersAboveSeaLevel,
                double agglomeration, Government government, StandardOfLiving standardOfLiving, Human governor) {
        this.name = name;
        this.coordinates = coordinates;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.agglomeration = agglomeration;
        this.government = government;
        this.standardOfLiving = standardOfLiving;
        this.governor = governor;
        this.creationDate = LocalDate.now();
        this.id = ++lastId;
    }

    public static City prompt() {
        String name = promptName();
        Coordinates coordinates = Coordinates.prompt();
        Government government = Government.prompt();
        Human governor = Human.prompt();
        StandardOfLiving standardOfLiving = StandardOfLiving.prompt();
        double area = promptArea();
        long population = promptPopulation();
        long meters = promptMeters();
        double agglomeration = promptAgglomeration();

        return new City(name, coordinates, area, population, meters, agglomeration, government, standardOfLiving, governor);


    }

    private static String promptName() {
        String str = IOManager.prompt("Название: ");
        try {
            return parseName(str);
        } catch (InvalidParameterException e) {
            System.out.println(e.getMessage());
        }
        return promptName();
    }

    private static String parseName(String str) throws InvalidParameterException {
        if (str.isEmpty()) {
            throw new InvalidParameterException("Название не может быть пустым");
        }
        return str;
    }

    private  static Double parseArea(String str) throws InvalidParameterException {
        double area;
        try {
            area = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("Неверный формат");
        } if (area <= 0) {
            throw new InvalidParameterException("Значение должно быть больше 0");
        }

        return area;
    }

    private static Double promptArea() {
        String str = IOManager.prompt("Area число больше 0: ");
        try {
            return parseArea(str);
        } catch (InvalidParameterException e) {
            System.out.println(e.getMessage());
            return promptArea();
        }
    }

    private  static Long parsePopulation(String str) throws InvalidParameterException {
        long population;
        try {
            population = Long.parseLong(str);
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("Неверный формат");
        } if (population <= 0) {
            throw new InvalidParameterException("Значение должно быть больше 0");
        }

        return population;
    }

    private static Long promptPopulation() {
        String str = IOManager.prompt("Population число больше 0: ");
        try {
            return parsePopulation(str);
        } catch (InvalidParameterException e) {
            System.out.println(e.getMessage());
            return promptPopulation();
        }
    }

    private  static Long parseMeters(String str) throws InvalidParameterException {
        long meters;
        try {
            meters = Long.parseLong(str);
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("Неверный формат");
        }
        return meters;
    }

    private static Long promptMeters() {
        String str = IOManager.prompt("Meters число: ");
        try {
            return parseMeters(str);
        } catch (InvalidParameterException e) {
            System.out.println(e.getMessage());
            return promptMeters();
        }
    }

    private  static Double parseAgglomeration(String str) throws InvalidParameterException {
        double agglomeration;
        try {
            agglomeration = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("Неверный формат");
        }

        return agglomeration;
    }

    private static Double promptAgglomeration() {
        String str = IOManager.prompt("agglomeration число: ");
        try {
            return parseAgglomeration(str);
        } catch (InvalidParameterException e) {
            System.out.println(e.getMessage());
            return promptAgglomeration();
        }
    }

    private static final HashSet<Integer> ids = new HashSet<>();

    private static Integer parseId(String str) throws InvalidParameterException {
        int id = 0;
        try {
            id = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("Некорректный id");
        } if (id <= 0) {
            throw new InvalidParameterException("id должен быть > 0");
        }
        if (ids.contains(id)) {
            throw new InvalidParameterException("id не уникален");
        }
        ids.add(id);
        return id;
    }

    private static LocalDate parseDate(String str) throws InvalidParameterException {
        try {
            return LocalDate.parse(str);
        } catch (DateTimeParseException e) {
            throw new InvalidParameterException("Неверная дата");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return id == city.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static City createCity(String [] data) throws InvalidParameterException {
        Integer id = parseId(data[1]);
        if (id > lastId) lastId = id;
        LocalDate date = parseDate(data[2]);
        String name = parseName(data[3]);
        Double x = Coordinates.parseX(data[4]);
        Integer y = Coordinates.parseY(data[5]);
        Coordinates coordinates = new Coordinates(x, y);
        Government government = Government.parse(data[6]);
        Integer height = Human.parseHeight(data[7]);
        StandardOfLiving standardOfLiving = StandardOfLiving.parse(data[8]);
        double area = parseArea(data[9]);
        long population = parsePopulation(data[10]);
        long meters = parseMeters(data[11]);
        double agglomeration = parseAgglomeration(data[12]);
        Human governor = new Human(height);

        City city = new City(name, coordinates, date, area, population, meters,
        agglomeration, government, standardOfLiving, governor, id);
        return city;
    }

    public String[] getCSVCity(String str) {
        String[] arr = ArrayUtils.addAll(new String[]{str,
                Integer.toString(id),
                creationDate.toString(), name, Double.toString(coordinates.getX()),
                Integer.toString(coordinates.getY()), government+"", governor.getCSVHuman(),
                standardOfLiving+"", Double.toString(area), Long.toString(population),
                Long.toString(metersAboveSeaLevel), Double.toString(agglomeration)});
        return arr;
    }

    @Override
    public String toString() {
        return "Город c id " + id +
                ", названием " + name +
                "\nСоздатель: " + owner +
                " " + coordinates + "\ndata: " + creationDate
                + "\n" + area + " " + population + " " +
                metersAboveSeaLevel + " " + agglomeration + " " + government + " " + standardOfLiving + governor;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}

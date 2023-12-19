package util;

import content.*;
import exeptions.DBError;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.TreeMap;

public class DataBase {
    private static DataBase instance = null;
    Connection conn;

    private DataBase() {
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres","scarywillow");//, "s335078","bH8wj8pt3HnBbDN3");
// "jdbc:postgresql://localhost:5432/postgres"
            PreparedStatement st = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS \"users\" (" +
                            "login VARCHAR (20) PRIMARY KEY," +
                            "hash VARCHAR (100) NOT NULL," +
                            "salt VARCHAR (10) NOT NULL" +
                            ");" +

                            "CREATE TABLE if NOT EXISTS cities (" +
                            "key VARCHAR(20) NOT NULL UNIQUE," +
                            "id SERIAL PRIMARY KEY," +
                            "owner VARCHAR(20) references \"users\"(login) ON DELETE SET NULL," +
                            "name VARCHAR(20) NOT NULL," +
                            "creation_date TIMESTAMP NOT NULL," +

                            "coord_x FLOAT," +
                            "coord_y INTEGER NOT NULL," +

                            "population NUMERIC," +
                            "meters NUMERIC," +
                            "area NUMERIC," +
                            "agglomeration NUMERIC," +
                            "government VARCHAR(255)," +
                            "standard VARCHAR(255)," +
                            "height NUMERIC" +
                            ");"
            );

            st.executeUpdate();
        } catch (PSQLException e) {
            System.out.println("Ошибка подключения к базе");
            System.exit(1);
        } catch (SQLException e) {
        }
    }

    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }


    public TreeMap<String, City> getAll() {                 /* Запись таблицы в коллекцию */
        TreeMap<String, City> cities = new TreeMap<>();

        try {
            ResultSet res = conn.prepareStatement("SELECT * FROM cities").executeQuery();
            while (res.next()) {
                City city = new City(
                        res.getString("name"),
                        new Coordinates(res.getDouble("coord_x"), res.getInt("coord_y")),
                        res.getDouble("area"),
                        res.getLong("population"),
                        res.getLong("meters"),
                        res.getDouble("agglomeration"),
                        res.getString("government") == null ? null : Government.valueOf(res.getString("government")),
                        res.getString("standard") == null ? null : StandardOfLiving.valueOf(res.getString("standard")),
                        new Human(res.getInt("height"))
                );
                city.setOwner(res.getString("owner"));
                cities.put(res.getString("key"), city);
            }
        } catch (SQLException | NullPointerException e) {
        }
        return cities;
    }

    public User getUser(String arg) {
        try {
            CallableStatement st = conn.prepareCall("SELECT * FROM \"users\" WHERE login = ?");
            st.setString(1, arg);
            ResultSet res = st.executeQuery();
            if (res.next()) {
                return new User(arg, res.getString("hash"), res.getString("salt"));
            }
        } catch (SQLException e) {

        }
        return null;
    }

    public boolean isUserExist(String arg) {          /* Проверка существования пользователя */
        try {
            CallableStatement st = conn.prepareCall("SELECT * FROM \"users\" WHERE login = ?");
            st.setString(1, arg);
            ResultSet res = st.executeQuery();
            return res.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean addUser(String login, String hash, String salt) {  /* Добавление пользователя */
        try {
            if (isUserExist(login)) {
                return false;
            }
            PreparedStatement st = conn.prepareStatement("INSERT INTO \"users\" (login, hash, salt) VALUES (?, ?, ?)");
            st.setString(1, login);
            st.setString(2, hash);
            st.setString(3, salt);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    /*
    public boolean update(Long id, City movie, String login) {

        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("DELETE FROM cities");
            for (int i = 0; i < result.size(); i++) {
                PreparedStatement st = conn.prepareStatement("INSERT INTO cities (name, coord_x, coord_y" +
                        "area, population, meters, agglomeration, government, standard" +
                        "height, owner) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                City currentCity = result.get(i);
                st.setString(1, currentCity.getName());
                st.setDouble(2, currentCity.getCoordinates().getX());
                st.setInt(3, currentCity.getCoordinates().getY());
                st.setDouble(4, currentCity.getArea());
                st.setLong(5, currentCity.getPopulation());
                st.setLong(6, currentCity.getMetersAboveSeaLevel());
                st.setDouble(7, currentCity.getAgglomeration());
                st.setString(8, currentCity.getGovernment().toString());
                st.setString(9, currentCity.getStandardOfLiving().toString());
                st.setString(10, currentCity.getGovernor().toString());
                st.setString(11, currentCity.getOwner());
            }
            return true;

        } catch (SQLException e) {
            return false;
        }

    }
    */

    public boolean insert(String key, City city, String login) throws DBError {
        if (isCityExistByKey(key)) {
            throw new DBError("Уже есть элемент с таким ключом");
        }
        return add(key, city, login) != -1;
    }

    public boolean removeLower(Integer id, String login) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM cities WHERE LENGTH(key) < ? AND owner = ?");
            ps.setInt(1, id);
            ps.setString(2, login);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean removeKey(String key, String login) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM cities WHERE key = ? AND owner = ?");
            ps.setString(1, key);
            ps.setString(2, login);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {

            return false;
        }
    }

    public boolean removeGreaterKey(String key, String login) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM cities WHERE id > ? AND owner = ?");
            ps.setInt(1, key.length());
            ps.setString(2, login);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {

            return false;
        }
    }

    public boolean clear(String login) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM cities WHERE owner = ?");
            ps.setString(1, login);
            int res = ps.executeUpdate();
            return res > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    private boolean isCityExistByKey(String key) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM movies WHERE 'key' = ?");
            ps.setString(1, key);
            ResultSet res = ps.executeQuery();
            return res.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public int add(String key, City city, String login) {
        try {

            PreparedStatement ps = conn.prepareStatement("INSERT INTO cities (name, coord_x, coord_y, " +
                    "area, population, meters, agglomeration, government, standard, " +
                    "height, owner, key, creation_date, id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" +
                    "RETURNING id");
            ps.setString(1, city.getName());
            ps.setDouble(2, city.getCoordinates().getX());
            ps.setInt(3, city.getCoordinates().getY());
            ps.setDouble(4, city.getArea());
            ps.setLong(5, city.getPopulation());
            ps.setLong(6, city.getMetersAboveSeaLevel());
            ps.setDouble(7, city.getAgglomeration());
            ps.setString(8, city.getGovernment().toString());
            ps.setString(9, city.getStandardOfLiving().toString());
            ps.setInt(10, city.getGovernor().getHeight());
            ps.setString(11, login);
            ps.setString(12, key);
            ps.setDate(13, Date.valueOf(city.getCreationDate()));
            ps.setInt(14, city.getId());
            ResultSet res = ps.executeQuery();
            res.next();
            return res.getInt("id");
        } catch (Exception e) {
            return -1;
        }
    }
}
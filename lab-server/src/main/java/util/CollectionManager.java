package util;

import content.City;
import exeptions.DBError;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class CollectionManager {
    private TreeMap<String, City> cities;
    private final LocalDate date;
    ReadWriteLock lk = new ReentrantReadWriteLock();
    private DataBase db = DataBase.getInstance();

    public CollectionManager() {
        DataBase db = DataBase.getInstance();
        this.date = LocalDate.now();
        this.cities =db.getAll();
    }

    public boolean clear(String login) {
        if (lk.writeLock().tryLock()) {
            try {
                if (db.clear(login)) {
                    cities.entrySet().removeIf(entry -> entry.getValue().getOwner().equals(login));   /* stream()

                            .filter((e) -> e.getValue().getOwner().equals(login))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (l, r) -> l, TreeMap::new)); */
                    return true;
                }
            } finally {
                lk.writeLock().unlock();
            }
        } else {
            System.out.println("Попытка параллельной записи");
        }
        return false;
    }


    @Override
    public String toString() {
        return cities
                .entrySet()
                .stream()
                .map((e) -> e.getKey() + ":\n" + e.getValue().toString() + "\n")
                .sorted(Comparator.comparingInt(String::length))
                .reduce((a, b) -> a + b)
                .orElse("Коллекция пуста\n")
                .trim();
    }

    public String getInfo() {
        return "type: " + cities.getClass() + "\n" + "date: " + date + "\nsize: " + cities.size();
    }
    /*
    public void save() {
        db.update(cities);
    }
    */
    public boolean contains(String key) {
        return cities.containsKey(key);
    }

    public boolean containsID(Integer id) {
        return cities.values().stream().anyMatch((v) -> Objects.equals(v.getId(), id));
    }

    public boolean insert(String key, City city, String login) {
        if (lk.writeLock().tryLock()) {
            try {
                if (contains(key)) return false;

                if (db.insert(key, city, login)) {
                    city.setOwner(login);
                    cities.put(key, city);
                        return true;
                }

            } catch (DBError dbError) {
                System.out.println("Ошибка записи");
                    dbError.printStackTrace();
            } finally {
                lk.writeLock().unlock();
            }
        } else {
            System.out.println("Попытка параллельной записи");
        }
        return false;
    }

    public String getAscending() {
        return cities.entrySet().stream()
                .sorted((e1, e2) -> {
                    City v1 = e1.getValue();
                    City v2 = e2.getValue();
                    return v1.getId().compareTo(v2.getId());
                })
                .map((s) -> s + "\n")
                .sorted(Comparator.comparingInt(String::length))
                .reduce((a, b) -> a + b)
                .orElse("Коллекция пуста\n");
    }


    public int removeLower(City city, String login) {
        AtomicInteger i = new AtomicInteger();

        if (lk.writeLock().tryLock()) {
            try {
                if (db.removeLower(city.getId(), login)) {
                    cities = cities.entrySet().stream()
                            .filter((e) -> {
                                if ((e.getValue().getId() < city.getId()) && (e.getValue().getOwner().equals(login))) {
                                    i.getAndIncrement();
                                    return false;
                                }
                                return true;
                            })
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (l, r) -> l, TreeMap::new));
                }

            } finally {
                lk.writeLock().unlock();
            }
        } else {
            System.out.println("Попытка параллельной записи");
        }


        return i.get();
    }


    // .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (r, l) -> l, TreeMap::new));

    public boolean removeKey(String key, String login) {
        if (lk.writeLock().tryLock()) {
            try {
                if (db.removeKey(key, login)) {
                    cities = db.getAll();
                    return true;
                    /* cities.get(key).getOwner().equals(login) && */
                }
            } finally {
                lk.writeLock().unlock();
            }
        } else {
            System.out.println("Попытка параллельной записи");
        }
        return false;
    }

    /*
    public void update(Integer id, City city, String login) {
        if (lk.writeLock().tryLock()) {
            try {
                if (!db.update(id, city, login)) {
                    throw new DBError("Ошибка при обновлении в бд");
                }

                cities = cities.entrySet().stream()
                        .peek((e) -> {
                            if (e.getValue().getId() == id) {
                                e.setValue(city);
                            }
                        })
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (l, r) -> l, TreeMap::new));
            } finally {
                lk.writeLock().unlock();
            }
        } else {
            System.out.println("Попытка параллельной записи");
        }
    }
    */

    public String replaceIfLow(String key, City movie) {
        cities = cities.entrySet().stream()
                .peek((e) -> {
                    if ((e.getKey().equals(key)) && (e.getValue().getId() < movie.getId())) {
                        e.setValue(movie);
                    }
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (r, l) -> l, TreeMap::new));
        return "";
    }

    public int removeGreaterKey(String key, String login) {
        AtomicInteger i = new AtomicInteger();

        if (lk.writeLock().tryLock()) {
            try {

                if (db.removeGreaterKey(key, login)) {
                    cities = cities.entrySet().stream()
                            .filter((e) -> {
                                if ((e.getKey().length() > key.length()) && (e.getValue().getOwner().equals(login))) {
                                    i.getAndIncrement();
                                    return false;
                                }
                                return true;
                            })
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (l, r) -> l, TreeMap::new));
                }

            } finally {
                lk.writeLock().unlock();
            }
        } else {
            System.out.println("Попытка параллельной записи");
        }
        return i.get();
    }





}

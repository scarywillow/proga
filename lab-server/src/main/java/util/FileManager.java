package util;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import content.City;
import exeptions.InvalidParameterException;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.TreeMap;

public class FileManager {
    private TreeMap<String, City> cities;
    private String src;

    public FileManager(String src) {
        this.src = src;
        this.cities = open(src);
    }

    private TreeMap<String, City> open(String src) {
        TreeMap<String, City> tmpMovies = new TreeMap<>();
        Path path = Paths.get(src);
        if (!Files.isRegularFile(path)) {
            System.out.println("File not Regular");
            System.exit(1);
        }
        if (!Files.isWritable(path)) {
            System.out.println("File not writable");
            System.exit(1);
        }
        if (!Files.isReadable(path)) {
            System.out.println("File not readable");
            System.exit(1);
        }
        long ind = 0;
        try {
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            CSVReader csv = new CSVReader(reader);
            String[] line;
            while ((line = csv.readNext()) != null) {
                City city;
                try {
                    city = City.createCity(line);
                    tmpMovies.put(line[0], city);
                } catch (InvalidParameterException e) {
                    e.printStackTrace();
                } finally {
                    ind += 1;
                }
            }
        } catch (IOException | CsvValidationException e) {
            System.out.println("Неверный формат файла");
            System.exit(1);
        }

        return tmpMovies;
    }


    public TreeMap<String, City> read() {
        return cities;
    }

    public void save() {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(src));

            for (String key : cities.keySet()) {
                writer.writeNext(cities.get(key).getCSVCity(key), false);
                System.out.println(Arrays.toString(cities.get(key).getCSVCity(key)));
            }
            writer.close();

        } catch (IOException | Error ignored) {
        }
    }
}

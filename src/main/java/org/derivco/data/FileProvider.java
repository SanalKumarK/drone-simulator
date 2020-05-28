package org.derivco.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class FileProvider {
    public static List<String> readInputFile(String fileName) {
        List<String> inputList = new ArrayList<>();
        try {
            File input = new File(fileName);
            Scanner scanner = new Scanner(input);
            while(scanner.hasNext()) {
                inputList.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Exception in reading the file :" + e.getMessage());
        }
        return inputList;
    }

    public static Properties loadProperties() {
        String fileName = "config.properties";
        File file = getResource(fileName);
        if (file == null) {
            System.out.println("File Not Found: " + fileName);
            return null;
        }
        return readFile(file);
    }

    private static Properties readFile(File file) {
        try (Reader reader = Files.newBufferedReader(Paths.get(file.getPath()))) {
            Properties prop = new Properties();
            prop.load(reader);
            return prop;
        } catch (Exception ex) {
            System.out.println("Error in reading file content: " + ex.getMessage());
        }
        return null;
    }

    /**
     * Read the file from the resources folder.
     *
     * @param fileName
     * @return
     */
    private static File getResource(String fileName) {
        URL resource = ClassLoader.getSystemClassLoader().getResource(fileName);
        if (resource != null) {
            return new File(resource.getFile());
        }
        return null;
    }
}

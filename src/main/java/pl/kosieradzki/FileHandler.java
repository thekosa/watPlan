package pl.kosieradzki;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The FileHandler class provides utility methods for reading the content of files.
 * It includes methods to read the entire content of a file as a single String
 * or to read the content line by line into a List of Strings.
 */
public class FileHandler {
    /**
     * Reads the content of a file specified by its file path and returns it as a single string.
     * The method reads the file line by line and appends each line to a StringBuilder,
     * ensuring that the entire file content is captured and returned as a single String.
     *
     * @param filePath the path to the file that needs to be read
     * @return a string containing the full content of the file
     * @throws FileNotFoundException if the file specified by the file path is not found
     */
    public String readFileToString(String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine());
        }
        return stringBuilder.toString();
    }

    /**
     * Reads the content of a file line by line and returns the lines as a list of strings.
     * Each line in the file is added to the list without newline characters.
     *
     * @param filePath the path to the file to be read
     * @return a list of strings, where each string represents a line from the file
     * @throws FileNotFoundException if the file specified by the file path is not found
     */
    public List<String> readFile(String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));
        List<String> list = new ArrayList<>();
        while (scanner.hasNextLine()) {
            list.add(scanner.nextLine().replace("\n", ""));
        }
        return list;
    }
}

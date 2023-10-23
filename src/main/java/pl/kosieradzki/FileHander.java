package pl.kosieradzki;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileHander {
    public String readFile(String fileName) throws FileNotFoundException {
        Scanner scanner= new Scanner(new File("src\\filters\\" + fileName));
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine());
        }
        return stringBuilder.toString();
    }

    public void deleteFile(String fileName) {
        File file = new File(fileName);
        file.delete();
    }
}

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Encrypt {

    private Scanner scanner = new Scanner(System.in);

    public String encrypt() throws IOException {
        StringBuilder result = new StringBuilder();
        System.out.println("which File shall be encrypted?(Total Path please)");
        String sentence = readFile(scanner.nextLine());
        System.out.println("File has been imported");
        System.out.println("The Encryption Key?(only letters)");
        String key = scanner.nextLine().toUpperCase();
        for (int i = 0, j = 0; i < sentence.length(); i++) {
            if (sentence.charAt(i) >= 'A' && sentence.charAt(i) <= 'Z') {
               result.append((char)(((sentence.charAt(i) + key.charAt(j)) % 26) + 'A'));
                j = j >= key.length() - 1 ? 0 : j + 1;
            } else {
                result.append(sentence.charAt(i));
            }
        }
        FileWriter writer = new FileWriter(new File("Result/encrypted.txt"), false);
        writer.write(result.toString());
        writer.close();
        return result.toString();
    }

    String readFile(String path) throws FileNotFoundException {
        StringBuilder inputData = new StringBuilder();
        Scanner fileScanner = new Scanner(new File(path));
        while (fileScanner.hasNextLine()) {
            inputData.append(fileScanner.nextLine().toUpperCase());
        }
        return inputData.toString();
    }
}

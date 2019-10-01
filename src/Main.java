import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static final HashMap<Character, Double> letterStatistics = new HashMap<>();

    public static void main(String[] args) {
        readStatistics();
        Encrypt encrypt = new Encrypt();
        Decrypt decrypt = new Decrypt();
        decrypt.decrypt(encrypt.encrypt());
    }

    public static void readStatistics() {
        try {
            File letterFile = new File("letters.txt");
            Scanner fileScanner = new Scanner(letterFile);
            while (fileScanner.hasNextLine()) {
                String input = fileScanner.nextLine();
                letterStatistics.put(input.split(";")[0].charAt(0), Double.parseDouble(input.split(";")[1]) / 100);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}

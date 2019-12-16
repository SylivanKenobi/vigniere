import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static final HashMap<Character, Double> letterStatistics = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    private static Encrypt encrypt = new Encrypt();
    private static Decrypt decrypt = new Decrypt();

    public static void main(String[] args) throws FileNotFoundException {
        readStatistics();
        Boolean loop = true;
        while (loop) {
            System.out.println("What do you want?\n(1) Encrypt\n(2) Decrypt\n(x) Stop");
            switch (scanner.nextLine()) {
                case "1":
                    encrypt.encrypt();
                    break;
                case "2":
                    decrypt.decrypt();
                    break;
                case "x":
                    loop = false;
                    break;
            }
        }


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

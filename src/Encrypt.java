import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Encrypt {

    private Scanner scanner = new Scanner(System.in);
    private Scanner fileScanner;
    private File inputFile;
    private ArrayList<String> inputData = new ArrayList<>();
    private String key;
    private String oneWord = "";
    private String result = "";

    public String encrypt() throws FileNotFoundException {

        System.out.println("which File shall be encrypted?(Total Path please)");
        inputFile = new File(scanner.nextLine());
        fileScanner = new Scanner(inputFile);
        while (fileScanner.hasNextLine()) {
            String input = fileScanner.nextLine();
            inputData.add(input);
        }
        System.out.println("File has been imported");
        System.out.println("The Encryption Key?");
        key = scanner.nextLine();
        for (int i = 0; i < inputData.size(); i++) {
            inputData.set(i, inputData.get(i).replaceAll("[^a-zA-Z0-9]+", ""));
            oneWord += inputData.get(i);
        }
        char[] letters = oneWord.toUpperCase().toCharArray();
        char[] keyLetters = key.toUpperCase().toCharArray();
        char[] newKeyLetters = new char[letters.length];
        for (int i = 0, j = 0; i < letters.length; i++, j++) {
            if (j == keyLetters.length) {
                j = 0;
            }
            newKeyLetters[i] = keyLetters[j];
        }
        for (int i = 0; i < letters.length; i++) {
            result += (char) (((letters[i] + newKeyLetters[i]) % 26) + 'A');
        }
        try {
            FileWriter writer = new FileWriter(new File("output.txt"), false);
            writer.write(result);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

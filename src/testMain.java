import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class testMain {
    private static double chiSquare = 0.0;
    public static void main(String[] args) {
        HashMap<Character, Double> letterStatistics = new HashMap<>();
        File letterFile;
        Scanner fileScanner = null;
        String test = "HNIFMTISUTCHUMVSNAIEHYREGOAICBEFSNNGDTISTIPKTSDRTARISRHLAIRKNAITTHDGBGTRRITPBNOHNDSUEONEONEHSOUEEWDOTNOTKTOHGLATSNDSRUHSAOADFLEKTSAHCTKCTDEQETETUHRMKHTEAATTHAERCSYSYCTNGFNNISINHGISPKPCEMEEBSTHESEWHPPAIAIWWTYGIIAATTTOLBLIEI";
        try {
            letterFile = new File("letters.txt");
            fileScanner = new Scanner(letterFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (fileScanner.hasNextLine()) {
            String input = fileScanner.nextLine();
            letterStatistics.put(input.split(";")[0].charAt(0), Double.parseDouble(input.split(";")[1]) / 100);
        }

        letterStatistics.forEach((abc, stat) -> {
            System.out.println("hi");
            int letterCount = 0;
            for (int i = 0; i< test.length(); i++){
                if (test.charAt(i) == abc) {
                    letterCount++;
                }
            }
            chiSquare += (Math.pow(letterCount - (stat * test.length()), 2)) / (stat * test.length());
            System.out.println(chiSquare + " " + abc);

        });


    }
}

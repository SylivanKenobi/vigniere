import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class Decrypt {

    private TreeMap<Integer, Integer> keyLength = new TreeMap<>();
    private TreeMap<Integer, Integer> keyLengthSorted = new TreeMap<>();
    private TreeMap<Integer, Integer> possibleKeysLengths = new TreeMap<>();
    private HashMap<String, ArrayList<Integer>> tripplets = new HashMap<>();
    public static final HashMap<Character, Double> letterStatistics = new HashMap<>();
    private ArrayList<Caesar> caesars = new ArrayList<>();
    private File letterFile;
    private int userKey;
    int possibleKeylength = 1;
    private Scanner scanner;
    private Scanner fileScanner;
    private String caesarString = "";
    private ArrayList<String> caesarStrings = new ArrayList<>();
    private double chiSquare;


    public String decrypt(String toDecrypt) {
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
//        Aufbrechen und zählen der tripplets
        for (int i = 0; i < toDecrypt.length(); i++) {
            if (i + 3 > toDecrypt.length()) {
                break;
            }
            if (tripplets.containsKey(toDecrypt.substring(i, i + 3))) {
                ArrayList<Integer> zs = tripplets.get(toDecrypt.substring(i, i + 3));
                zs.add(i);
                tripplets.put(toDecrypt.substring(i, i + 3), zs);
            } else {
                ArrayList<Integer> zs = new ArrayList<>();
                zs.add(i);
                tripplets.put(toDecrypt.substring(i, i + 3), zs);
            }

        }

        //Anzahl möglicher Schlüssellängen berechnen
        tripplets.forEach((tripplets, indexesOfTripplets) -> {
            for (int index = 0; index < indexesOfTripplets.size(); index++) {
                if (index + 1 != indexesOfTripplets.size() && indexesOfTripplets.size() > 2) {
                    for (int e = 1; e <= 20; e++) {
                        if ((indexesOfTripplets.get(index + 1) - indexesOfTripplets.get(index)) % e == 0) {
                            if (keyLength.containsKey(e)) {
                                keyLength.put(e, keyLength.get(e) + 1);
                            } else {
                                keyLength.put(e, 1);
                            }
                        }
                    }
                }
            }
        });
        keyLength.forEach((k, v) -> {
            keyLengthSorted.put(v, k);
        });
        keyLengthSorted.descendingMap().forEach((k, v) -> {
            if (v % possibleKeylength == 0) {
                possibleKeysLengths.put(k, v);
                possibleKeylength = v;
            }
        });
        System.out.println("Possible key lengths or a fraction of it");
        System.out.println(possibleKeysLengths.descendingMap());
//      create Key
        scanner = new Scanner(System.in);
        System.out.println("Which key length should be tested?");
        userKey = Integer.parseInt(scanner.nextLine());
//        split text up in caesar parts
        for (int i = 0; i < userKey; i++) {
            caesarString = "";
            for (int j = i; j < toDecrypt.length(); j += userKey) {
                caesarString += toDecrypt.charAt(j);
            }
            caesarStrings.add(caesarString);
        }
        caesars = caesarShift(caesarStrings);
        caesars.forEach(caesar -> caesar.findKeyLetter());
        return "";
    }


    private ArrayList<Caesar> caesarShift(ArrayList<String> caesarStrings) {
        ArrayList<Caesar> newCeasars = new ArrayList<>();
        caesarStrings.forEach(i -> {
            Caesar caesar = new Caesar();
            ArrayList<String> shiftedStrings = new ArrayList<>();
            letterStatistics.forEach((k, v) -> {
                String shiftedString = "";
                for (int j = 0; j < i.length(); j++) {
                    shiftedString += (char) (((i.charAt(j) + k) % 26) + 'A');
                }
                shiftedStrings.add(shiftedString);
                caesar.setEncrypted(i);
            });
            caesar.setPossibleDecryptions(shiftedStrings);
            newCeasars.add(caesar);
        });
        return newCeasars;
    }
}
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Decrypt {

    private Scanner scanner;
    private Scanner consoleScanner = new Scanner(System.in);

    public String decrypt() throws FileNotFoundException {
        System.out.println("Which File shall be cracked?");
        String toDecrypt = readFile(consoleScanner.nextLine());
        HashMap<String, ArrayList<Integer>> tripplets = createAndCountTripplets(toDecrypt);
        TreeMap<Integer, Integer> keyLength = calculatePossibleKeyLengths(tripplets);
        TreeMap<Integer, Integer> keyLengthSorted = sortByKeyLength(keyLength);
        TreeMap<Integer, Integer> possibleKeysLengths = guessKeyLength(keyLengthSorted);
        System.out.println("Possible key lengths or a fraction of it");
        System.out.println(possibleKeysLengths.descendingMap());
        System.out.println("Which key length should be tested?");
        ArrayList<Caesar> caesars = splitTextByKeyLength(Integer.parseInt(consoleScanner.nextLine()), toDecrypt);
        caesars = caesarShift(caesars);
        caesars.forEach(caesar -> caesar.findKeyLetter());
        System.out.println("Which key should be used");
        decryptWithKey(toDecrypt, consoleScanner.nextLine());
        return "";
    }

    private void decryptWithKey(String toDecrypt, String key) {
        String capitalizedKey = key.toUpperCase();
        StringBuilder decrypted = new StringBuilder();
        for (int i = 0, j = 0; j < toDecrypt.length(); j++) {
            if (toDecrypt.charAt(i) >= 'A' && toDecrypt.charAt(i) <= 'Z') {
                decrypted.append((char) ((((toDecrypt.charAt(j) - capitalizedKey.charAt(i)) + 26) % 26) + 'A'));
                i = i >= capitalizedKey.length() - 1 ? 0 : i + 1;
            } else {
                decrypted.append(toDecrypt.charAt(j));
            }
        }
        System.out.println(decrypted);
    }

    private ArrayList<Caesar> splitTextByKeyLength(int userKey, String toDecrypt) {
        ArrayList<Caesar> newCeasars = new ArrayList<>();
        String caesarString = "";
        for (int i = 0; i < userKey; i++) {
            caesarString = "";
            for (int j = i; j < toDecrypt.length(); j += userKey) {
                caesarString += toDecrypt.charAt(j);
            }
            newCeasars.add(new Caesar(caesarString));
        }
        return newCeasars;
    }

    private TreeMap<Integer, Integer> guessKeyLength(TreeMap<Integer, Integer> keyLengthSorted) {
        TreeMap<Integer, Integer> possibleKeysLengths = new TreeMap<>();
        AtomicInteger possibleKeylength = new AtomicInteger(1);
        keyLengthSorted.descendingMap().forEach((k, v) -> {
            if (v % possibleKeylength.get() == 0) {
                possibleKeysLengths.put(k, v);
                possibleKeylength.set(v);
            }
        });
        return possibleKeysLengths;
    }

    private TreeMap<Integer, Integer> sortByKeyLength(TreeMap<Integer, Integer> keyLength) {
        TreeMap<Integer, Integer> keyLengthSorted = new TreeMap<>();
        keyLength.forEach((k, v) -> {
            keyLengthSorted.put(v, k);
        });
        return keyLengthSorted;
    }

    private String readFile(String path) throws FileNotFoundException {
        File inputFile = new File(path);
        String toDecrypt = "";
        scanner = new Scanner(inputFile);
        while (scanner.hasNextLine()) {
            toDecrypt = scanner.nextLine();
        }
        return toDecrypt;
    }

    private HashMap<String, ArrayList<Integer>> createAndCountTripplets(String toDecrypt) {
        HashMap<String, ArrayList<Integer>> tripplets = new HashMap<>();
        for (int i = 0; i < toDecrypt.length() - 2; i++) {
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
        return tripplets;
    }

    private TreeMap<Integer, Integer> calculatePossibleKeyLengths(HashMap<String, ArrayList<Integer>> tripplets) {
        TreeMap<Integer, Integer> keyLength = new TreeMap<>();
        tripplets.forEach((tripplet, indexesOfTripplets) -> {
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
        return keyLength;
    }

    private ArrayList<Caesar> caesarShift(ArrayList<Caesar> caesars) {
        caesars.forEach(caesar -> {
            String encrypted = caesar.getEncrypted();
            ArrayList<String> shiftedStrings = new ArrayList<>();
            Main.letterStatistics.forEach((k, v) -> {
                String shiftedString = "";
                for (int j = 0; j < encrypted.length(); j++) {
                    shiftedString += (char) (((encrypted.charAt(j) + k) % 26) + 'A');
                }
                shiftedStrings.add(shiftedString);
            });
            caesar.setPossibleDecryptions(shiftedStrings);
        });
        return caesars;
    }
}
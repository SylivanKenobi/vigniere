import java.util.ArrayList;
import java.util.TreeMap;

public class Caesar {
    private String encrypted;
    private ArrayList<String> possibleDecryptions;
    private ArrayList<String> possibleKeyValues;
    private double chiSquare = 0.0;
    private double expected = 0.0;

    public Caesar(String encrypted) {
        this.encrypted = encrypted;
    }

    public void setEncrypted(String encrypted) {
        this.encrypted = encrypted;
    }

    public void setPossibleDecryptions(ArrayList<String> possibleDecryptions) {
        this.possibleDecryptions = possibleDecryptions;
    }

    public String getEncrypted(){
        return this.encrypted;
    }

    public TreeMap<Double, Character> findKeyLetter() {
        TreeMap<Double, Character> chiSquares = new TreeMap<>();
        possibleDecryptions.forEach(i -> {
            Main.letterStatistics.forEach((abc, stat) -> {
                int counter = 0;
                for (int j = 0; j < i.length(); j++) {
                    if (i.charAt(j) == abc) {
                        counter++;
                    }
                }
                expected = stat * i.length();
                chiSquare += ((counter - expected) * (counter - expected)) / expected;
            });
            char letter = possibleDecryptions.size() - possibleDecryptions.indexOf(i) == 26 ? 'A' : (char) (possibleDecryptions.size() - possibleDecryptions.indexOf(i) + 65);
            chiSquares.put(chiSquare, letter);
            chiSquare = 0.0;
        });
        System.out.println(chiSquares);
        return chiSquares;
    }
}

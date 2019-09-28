import java.util.ArrayList;
import java.util.TreeMap;

public class Caesar {
    private String encrypted;
    private ArrayList<String> possibleDecryptions;
    private ArrayList<String> possibleKeyValues;
    private double chiSquare = 0.0;
    private double expected = 0.0;

    public String getEncrypted() {
        return encrypted;
    }

    public ArrayList<String> getPossibleDecryptions() {
        return possibleDecryptions;
    }

    public ArrayList<String> getPossibleKeyValues() {
        return possibleKeyValues;
    }

    public void setEncrypted(String encrypted) {
        this.encrypted = encrypted;
    }

    public void setPossibleDecryptions(ArrayList<String> possibleDecryptions) {
        this.possibleDecryptions = possibleDecryptions;
    }

    public void setPossibleKeyValues(ArrayList<String> possibleKeyValues) {
        this.possibleKeyValues = possibleKeyValues;
    }

    public void findKeyLetter() {
        TreeMap<Double, Integer> chiSquares = new TreeMap<>();
        possibleDecryptions.forEach(i -> {
            Decrypt.letterStatistics.forEach((abc, stat) -> {
                int counter = 0;
                for (int j = 0; j < i.length(); j++) {
                    if (i.charAt(j) == abc) {
                        counter++;
                    }
                }
                expected = stat * i.length();
                chiSquare += ((counter - expected) * (counter - expected)) / expected;
            });
            chiSquares.put(chiSquare, possibleDecryptions.indexOf(i));
            chiSquare = 0.0;
        });
        System.out.println(chiSquares);
    }
}

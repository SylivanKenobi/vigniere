import java.util.ArrayList;

public class Caesar {
    private String encrypted;
    private ArrayList<String> possibleDecryptions;
    private ArrayList<String> possibleKeyValues;
    private double percent = 0.0;

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
        System.out.println("hi");
        possibleDecryptions.forEach(i -> {
            percent = 0.0;
            Decrypt.letterStatistics.forEach((abc, stat) -> {
                int counter = 0;
                for (int j = 0; j < i.length(); j++) {
                    if (i.charAt(j) == abc) {
                        counter++;
                    }
                }
                percent = (counter/i.length())*100;
            });
            System.out.println(percent);
            System.out.println(i.length());

        });
    }
}

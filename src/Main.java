public class Main {
    public static void main(String[] args) {
        Encrypt encrypt = new Encrypt();
        Decrypt decrypt = new Decrypt();
        decrypt.decrypt(encrypt.encrypt());
    }
}

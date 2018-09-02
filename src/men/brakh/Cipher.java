package men.brakh;

public interface Cipher {
    String encode(String message, int key);
    String decode(String message, int key);
}

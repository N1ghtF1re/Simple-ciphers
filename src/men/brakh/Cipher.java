package men.brakh;


/**
 * Cipher interface
 *
 * @author  Pankratiew Alexandr
 * @version  1.0
 */

public interface Cipher {
    String encode(String message, int key);
    String decode(String message, int key);
}

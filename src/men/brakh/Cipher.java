package men.brakh;


/**
 * Cipher interface
 *
 * @author  Pankratiew Alexandr
 * @version  1.0
 */

public interface Cipher {
    String encode(String message, String key);
    String decode(String message, String key);
    default String encode(String message, int key) {throw new ArithmeticException("Integer key is not uses");}
    default String decode(String message, int key) {throw new ArithmeticException("Integer key is not uses");}
}

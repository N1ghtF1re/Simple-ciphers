package men.brakh;

/**
 * Class Allows you to encrypt and Ciphertext messages using a cipher "rail-fence"
 * About cipher: https://en.wikipedia.org/wiki/Rail_fence_cipher
 * @author  Pankratiew Alexandr
 * @version  1.0
 */
public class RailFence implements Cipher  {

    /**
     * The method determines at what distance the next symbol is at the same "height".                             <br>
     * Example:                                                                                                    <br>
     * 1     7        13                                                                                           <br>
     *  2   6 8     12  ...                                                                                        <br>
     *   3 5   9  11                                                                                               <br>
     *    4     10                                                                                                 <br>
     * 2,6,8,12 are at the same height, but distance between 2 and 6 = 4 chars, between 6 and 8 = 2                <br>
     * Method takes this into account
     *
     * @param iteration  The number of the current character in the line at one "height"
     * @param row  The number of current
     * @param size  The number of rows (key)
     * @return Distance the next symbol is at the same "height"
     */
    private int getTerm(int iteration, int row, int size) {
        if ((size == 0) || (size == 1)) {
            return 1;
        }
        if((row == 0) || (row == size-1)) { // Max. distance is achieved at the ends and equally (size-1)*2
            return (size-1)*2;
        }

        if (iteration % 2 == 0) { // In the description of the method above this identity is demonstrated
            return (size-1-row)*2;
        }
        return 2*row;
    }

    /**
     * The method encodes messages with a cipher "Rail-Fence" with the specified key.
     *
     * @param message Plaintext, which should be encrypted
     * @param key key (The number of "rails" (rows))
     * @return Encoded message
     * @see RailFence#getTerm(int, int, int)
     */
    @Override
    public String encode(String message, int key) {
        if (key < 0) {
            throw new ArithmeticException("Negative key value");
        }
        String encodedMessage = "";

        for(int row = 0; row < key; row++) { // Look rows
            int iter = 0; // The number of the character in the row
            for(int i = row; i < message.length(); i += getTerm(iter++, row, key)) {
                // i - the number of row character in source line

                encodedMessage += message.charAt(i); // "Add characters line by row"
            }


        }
        return encodedMessage;
    }

    /**
     * The method decodes messages with a cipher "Rail-Fence" with the specified key.                              <br>
     *                                                                                                             <br>
     * Decode algorithm (example):                                                                                 <br>
     * <b>Ciphertext</b> = cgroryytahpp                                                                            <br>
     *                                                                                                             <br>
     * At rows it:                                                                                                 <br>
     * ?     ?                                                                                                     <br>
     *  ?   ? ?   ?                                                                                                <br>
     *   ? ?   ? ?                                                                                                 <br>
     *    ?     ?                                                                                                  <br>
     * characters №0, №1 in a source ciphertext - characters №0 and №0+6 (is obtained by
     * {@link RailFence#getTerm(int, int, int)}) in decoded text
     * I.e. char "c" in ciphertext - char №0 in a decoded string, "g" - №6
     *
     * <br><i> с?????g????? </i><br>
     *
     * characters №2, №3, №4, №5 in a source ciphertext - characters №1 and №1+4, №1+4+2, №1+4+2+4
     * (is obtained by {@link RailFence#getTerm(int, int, int)}) in decoded text                                   <br>
     * I.e. char "r" in ciphertext - char №1 in a decoded string, "o" - №5, ...
     * <br><i> cr???ogr???y </i><br>
     *
     * And so on...
     *
     *
     * @param message Ciphertext, width should be decrypted
     * @param key key (The number of "rails" (rows))
     * @see RailFence#getTerm(int, int, int)
     * @return Decoded message
     */
    @Override
    public String decode(String message, int key) {
        if (key < 0) {
            throw new ArithmeticException("Negative key value");
        }
        StringBuilder decodedMessage = new StringBuilder(message);
        int currPosition = 0; // Position in source string
        for(int row = 0; row < key; row++) { // Look rows
            int iter = 0; // The number of the character in the row
            for(int i = row; i < message.length(); i += getTerm(iter++, row, key)) {
                decodedMessage.setCharAt(i, message.charAt(currPosition++));
            }


        }

        return decodedMessage.toString();
    }
}

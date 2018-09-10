package men.brakh;

/**
 * Class Allows you to encrypt and decrypt messages using a cipher Vigener Cipher
 * About cipher: https://en.wikipedia.org/wiki/Vigen%C3%A8re_cipher
 * @author  Pankratiew Alexandr
 * @version  1.0
 */
public class VigenerCipher implements Cipher {
    private String alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";

    /**
     * A class constructor with no parameters. The Russian alphabet is used by default.
     */
    public VigenerCipher() {
    }

    /**
     * A class constructor
     * @param alphabet Basic alphabet. The characters are written consecutively by a string. Example: "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
     */
    public VigenerCipher(String alphabet) {
        this.alphabet = alphabet;
    }

    /**
     * Returns the index of a character in the base alphabet
     * @param chr Character
     * @return Index of the character in the base alphabet
     */
    private int getAlphabetIndex(char chr) {
        return alphabet.indexOf(chr);
    }

    /**
     * Generating a Progressive Key
     * @param key Keyword
     * @param len Text length
     * @return Progressive Key
     */
    private String generateProgressiveKey(String key, int len) {
        StringBuilder progrKey = new StringBuilder(len);

        for (int i = 0; i <= len; i++) {
            // index = (Alphabet.IndexOf(Key[i % keyLength]) + i div keyLength) mod keyLength
            int tmp = (getAlphabetIndex(key.charAt(i % (key.length()))) + (i/key.length())) % alphabet.length();
            progrKey.append(alphabet.charAt(tmp));
        }

        return progrKey.toString();
    }

    /**
     * Encrypting the message with the Vigener cipher
     * @param message Plaintext
     * @param keyword Keyword
     * @return Cyphertext
     */
    @Override
    public String encode(String message, String keyword) {
        String progrKey = generateProgressiveKey(keyword, message.length());
        StringBuilder cipherText = new StringBuilder(message);
        for(int i = 0; i < message.length(); i++) {
            // f(a) = (a + k) mod N
            int charIndex = (getAlphabetIndex(message.charAt(i)) +
                    getAlphabetIndex(progrKey.charAt(i))) % alphabet.length();
            cipherText.setCharAt(i,alphabet.charAt(charIndex));
        }
        return cipherText.toString();
    }

    /**
     * Decrypting the message with the Vigener cipher
     * @param message Ciphertext
     * @param keyword Keyword
     * @return Plaintext
     */
    @Override
    public String decode(String message, String keyword) {
        String progrKey = generateProgressiveKey(keyword, message.length());
        StringBuilder plaintext = new StringBuilder(message);
        for(int i = 0; i < message.length(); i++) {
            // a = (f(a) + (N-k)) mod N
            int charIndex = (getAlphabetIndex(message.charAt(i)) +
                    (alphabet.length() - getAlphabetIndex(progrKey.charAt(i)))) % alphabet.length();
            plaintext.setCharAt(i,alphabet.charAt(charIndex));
        }
        return plaintext.toString();
    }
    
}

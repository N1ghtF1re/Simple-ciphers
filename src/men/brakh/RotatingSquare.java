package men.brakh;

/**
 * Class Allows you to encrypt and decrypt messages using a cipher Roating Square
 * @author  Pankratiew Alexandr
 * @version  1.0
 */
public class RotatingSquare {
    private int[][] key = {{0,0}, {3,1}, {2,2}, {1,3}};
    private int length = 4;

    /**
     * A class constructor with no parameters. Default key: key;
     */
    public RotatingSquare() {

    }

    /**
     * A class constructor
     * @param key The array of "holes" in the square of the format [ [x,y], [x,y], ..]. Example: {{0,0}, {3,1}, {2,2}, {1,3}}
     */
    public RotatingSquare(int[][] key) {
        this.key = key;
        length = key.length;
    }

    /**
     * Returns the index when the matrix is rotated
     * @param iteration - Current iteration
     * @return array of indexs
     */
    private int[][] getIndexs(int iteration) {
        int[][] temp = new int[length][2];
        for (int[] elem : key) {
            int x;
            if((iteration == 0) || (iteration == 3)) {
                x = elem[0];
            } else {
                x = (length-1) - elem[0];
            }
            int y;
            if (iteration <=1) {
                y = elem[1];
            } else {
                y = length - 1 - elem[1];
            }
            int[] el = new int[2];
            el[0] = x;
            el[1] = y;

            temp[el[1]] = el;
        }
        return temp;
    }

    /**
     * Expands the string to the multiplicity of N characters, filling in the missing English alphabet
     * @param str Source string
     * @param multiplicity Multiplicity
     * @return Expanded string
     */
    private String expandString(String str, int multiplicity) {
        StringBuilder temp = new StringBuilder(str);
        char letter = 'A';
        // Делаем длину строки кратной len^2
        while (temp.length() % multiplicity != 0) {
            temp.append(letter++);
            if (letter > 'Z') {
                letter = 'A';
            }
        }

        return temp.toString();
    }

    /**
     * Encrypt a message from length^2 characters using the reversing square method
     * @param plaintext Plaintext (length^2 characters)
     * @return CipherText (length^2 characters)
     */
    private String squareEncode(String plaintext){
        char[][] matrix = new char[length][length];
        int currIndex = 0;
        for(int i = 0; i < matrix.length; i++) {
            int[][] newIndexs = getIndexs(i); // Array of indexs after flip
            for(int[] indexs:newIndexs) {
                matrix[indexs[1]][indexs[0]] = plaintext.charAt(currIndex++); // Put the symbols in the "holes" of the square
            }
        }

        StringBuilder ciphertext = new StringBuilder();

        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix.length; j++) {
                ciphertext.append(matrix[i][j]); // Enter the rows of a square in a string
            }
        }
        return ciphertext.toString();
    }


    /**
     * Encrypts a string of any length using the rotating square method
     * @param plaintext Plaintext
     * @return Ciphertext
     * @see RotatingSquare#squareEncode(String)
     */
    public String encode(String plaintext){
        String regex = "(?<=\\G.{" + length*length +"})";
        plaintext = expandString(plaintext, length*length); // Expand String

        String[] arr = plaintext.split(regex);

        StringBuilder ciphertext = new StringBuilder();

        for(String element:arr) {
            ciphertext.append(squareEncode(element)); // Encode len^2 characters and push to the total ciphertext
        }


        return ciphertext.toString();
    }

    /**
     * Decrypt a message from length^2 characters using the reversing square method
     * @param ciphertext Ciphertext (length^2 characters)
     * @return Plaintext (length^2 characters)
     */
    public String squareDecode(String ciphertext){
        char[][] matrix = new char[length][length];
        int currIndex = 0;

        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix.length; j++) {
                matrix[i][j] = ciphertext.charAt(currIndex++);
            }
        }


        StringBuilder plaintext = new StringBuilder();
        for(int i = 0; i < matrix.length; i++) {
            int[][] newIndexs = getIndexs(i);
            for(int[] indexs:newIndexs) {
                plaintext.append(matrix[indexs[1]][indexs[0]]);
            }
        }




        return plaintext.toString();
    }

    /**
     * Decrypt a string (LENGTH OF LINE SHOULD BE MULTIPLICITY OF LEN^2 (default: 16)) using the rotating square method
     * @param ciphertext Ciphertext
     * @return Plaintext
     * @see RotatingSquare#squareDecode(String)
     */
    public String decode(String ciphertext){
        if (ciphertext.length() % length*length != 0) {
            throw new RuntimeException("Invalid cryptotext");
        }
        String regex = "(?<=\\G.{" + length*length +"})";

        String[] arr = ciphertext.split(regex);

        StringBuilder plaintext = new StringBuilder();

        for(String element:arr) {
            plaintext.append(squareDecode(element));
        }

        return plaintext.toString();
    }

}

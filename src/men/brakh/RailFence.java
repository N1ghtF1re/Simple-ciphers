package men.brakh;

public class RailFence implements Cipher  {

    private int getTerm(int iteration, int row, int size) {
        if((row == 0) || (row == size-1)) {
            return (size-1)*2;
        }

        if (iteration % 2 == 0) {
            return (size-1-row)*2;
        }
        return 2*row;
    }

    @Override
    public String encode(String message, int key) {
        if (key < 0) {
            throw new ArithmeticException("Negative key value");
        }
        String encodedMessage = "";

        for(int i = 0; i < key; i++) {
            int iter = 0;
            for(int j = i; j < message.length(); j += getTerm(iter++, i, key)) {
                encodedMessage += message.charAt(j);
            }


        }
        return encodedMessage;
    }

    @Override
    public String decode(String message, int key) {
        if (key < 0) {
            throw new ArithmeticException("Negative key value");
        }
        StringBuilder decodedMessage = new StringBuilder(message);
        int currPosition = 0;
        for(int i = 0; i < key; i++) {
            int iter = 0;
            for(int j = i; j < message.length(); j += getTerm(iter++, i, key)) {
                decodedMessage.setCharAt(j, message.charAt(currPosition++));
            }


        }

        return decodedMessage.toString();
    }
}

package men.brakh;



public class Demo {
    public static void main(String args[]) {
        Cipher railFence = new RailFence();

        String message = "Cryptography";
        System.out.println( railFence.encode(message, 4) );
        System.out.println( railFence.decode(railFence.encode(message, 8), 8) );
        System.out.println( railFence.decode(railFence.encode(message, 4), 4) );
        System.out.println( railFence.decode("cgroryytahpp", 4) );
    }
}

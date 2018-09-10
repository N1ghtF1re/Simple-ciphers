package men.brakh;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.*;

public class Lab1Frame extends JFrame {
    private JButton button = new JButton("To do everything!\n");
    private JTextField input = new JTextField("",40);
    private JLabel label = new JLabel("Key:");
    private JRadioButton radioRailFence = new JRadioButton("Rail fence");
    private JRadioButton radioRoatingSquare = new JRadioButton("Rotating square");
    private JRadioButton radioVigener = new JRadioButton("Vigener");
    private JRadioButton radioIsEncrypt = new JRadioButton("Encode");
    private JRadioButton radioIsDecrypt = new JRadioButton("Decode");


    final static String inputFile = "input.txt";
    final static String outputFile = "output.txt";

    public static String readFile(String filename) {
        try {
            return new String(Files.readAllBytes(Paths.get(filename)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void writeFile(String filename, String message) {
        try (PrintWriter out = new PrintWriter(filename)) {
            out.print(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static void writeFile(String message) {
        writeFile(outputFile, message);
    }

    public static String readFile() {
        return readFile(inputFile);
    }

    static String onlyEng(String s) {
        return s.replaceAll("[^A-Za-zА]", "").toUpperCase();
    }
    static String onlyRus(String s) {
        return s.replaceAll("[^А-Яа-яёЁА]", "").toUpperCase();
    }

    public Lab1Frame() {
        super("Perfect encoder/decoder");
        this.setBounds(100,100,500,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = this.getContentPane();
        container.setLayout(new FlowLayout());
        container.add(label);
        container.add(input);




        ButtonGroup isEncGroup = new ButtonGroup();
        Panel encOrDecPanel = new Panel(new GridLayout(1,2));
        isEncGroup.add(radioIsDecrypt);
        isEncGroup.add(radioIsEncrypt);
        encOrDecPanel.add(radioIsDecrypt);
        encOrDecPanel.add(radioIsEncrypt);
        container.add(encOrDecPanel);

        ButtonGroup cipherGroup = new ButtonGroup();
        cipherGroup.add(radioRailFence);
        cipherGroup.add(radioRoatingSquare);
        cipherGroup.add(radioVigener);

        Panel cipherPanel = new Panel(new GridLayout(1,3));

        cipherPanel.add(radioRailFence);
        cipherPanel.add(radioRoatingSquare);
        cipherPanel.add(radioVigener);

        container.add(cipherPanel);

        radioRailFence.setSelected(true);
        radioIsEncrypt.setSelected(true);


        button.addActionListener(new ButtonEventListener());
        container.add(button);
    }

    void dialogMSG(String message, String title) {
        JOptionPane.showMessageDialog(null,
                message,
                title,
                JOptionPane.PLAIN_MESSAGE);
    }

    class ButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String message = "";
            if (radioIsEncrypt.isSelected()) {
                message += "Encrypted\n";

                String ciphertext = "";
                if (radioRailFence.isSelected()) {
                    String plaintext = onlyEng(readFile());
                    message += "Key is " + input.getText() + "\n";
                    message += "Plaintext: " + plaintext + "\n";
                    message += "Language: English\n";
                    Cipher railFence = new RailFence();
                    try {
                        ciphertext = railFence.encode(plaintext, Integer.parseInt(input.getText()));
                    } catch (NumberFormatException | ArithmeticException e2)  {
                        dialogMSG("Bad key\nException: " + e2, "Error!");
                        return;
                    }
                } else if(radioVigener.isSelected()) {
                    message += "Key is " + onlyRus(input.getText()) + "\n";
                    message += "Language: Russian\n";
                    String plaintext = onlyRus(readFile());
                    message += "Plaintext: " + plaintext + "\n";
                    Cipher vigener = new VigenerCipher();
                    try {
                        ciphertext = vigener.encode(plaintext, onlyRus(input.getText()));
                    } catch (NumberFormatException | ArithmeticException e2)  {
                        dialogMSG("Bad key\nException: " + e2, "Error!");
                        return;
                    }
                } else if (radioRoatingSquare.isSelected()) {
                    String plaintext = onlyEng(readFile());
                    message += "Plaintext: " + plaintext + "\n";
                    message += "Language: English\n";
                    RotatingSquare rotatingSquare = new RotatingSquare();
                    try {
                        ciphertext = rotatingSquare.encode(plaintext);
                    } catch (StringIndexOutOfBoundsException e2)  {
                        dialogMSG("Source text does not contain English letters", "Error!");
                        return;
                    }

                }
                writeFile(ciphertext);
                message += "Ciphertext: " + ciphertext + "\n";
            } else {
                message += "Decrypted\n";

                String plaintext = "";

                if (radioRailFence.isSelected()) {
                    message += "Key is " + onlyEng(input.getText()) + "\n";
                    String ciphertext = onlyEng(readFile());
                    message += "Ciphertext: " + ciphertext + "\n";
                    message += "Language: English\n";
                    Cipher railFence = new RailFence();
                    try {
                        plaintext = railFence.decode(ciphertext, Integer.parseInt(input.getText()));
                    } catch (NumberFormatException | ArithmeticException e2) {
                        dialogMSG("Bad key\nException: " + e2, "Error!");
                        return;
                    }

                } else if(radioVigener.isSelected()) {
                    message += "Key is " + onlyRus(input.getText()) + "\n";
                    String ciphertext = onlyRus(readFile());
                    message += "Ciphertext: " + ciphertext + "\n";
                    message += "Language: Russian\n";
                    Cipher vigener = new VigenerCipher();
                    try {
                        plaintext = vigener.decode(ciphertext, onlyRus(input.getText()));
                    } catch (NumberFormatException | ArithmeticException e2) {
                        dialogMSG("Bad key\nException: " + e2, "Error!");
                        return;
                    }
                } else if (radioRoatingSquare.isSelected()) {
                    String ciphertext = onlyEng(readFile());
                    message += "Ciphertext: " + ciphertext + "\n";
                    message += "Language: English\n";
                    RotatingSquare rotatingSquare = new RotatingSquare();
                    try {
                        plaintext = rotatingSquare.decode(ciphertext);
                    } catch (NumberFormatException | ArithmeticException e2) {
                        dialogMSG("Bad ciphertext\nException: " + e2, "Error!");
                        return;
                    }

                }
                writeFile(plaintext);
                message += "Plaintext: " + plaintext + "\n";
            }
            dialogMSG(message, "Great!");

        }
    }

    public static void main(String[] args) {
        Lab1Frame app = new Lab1Frame();
        app.setVisible(true);
        System.out.println( readFile() );
    }
}
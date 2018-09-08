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

    class ButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String message = "";
            if (radioIsEncrypt.isSelected()) {
                message += "Encrypted\n";
                message += "Key is " + input.getText() + "\n";

                String ciphertext = "";
                String plaintext = onlyEng(readFile());
                message += "Plaintext: " + plaintext + "\n";
                if (radioRailFence.isSelected()) {
                    message += "Language: English\n";
                    RailFence railFence = new RailFence();
                    ciphertext = railFence.encode(plaintext, Integer.parseInt(input.getText()));
                    writeFile(ciphertext);
                }
                message += "Ciphertext: " + ciphertext + "\n";
            } else {
                message += "Decrypted\n";
                message += "Key is " + onlyEng(input.getText()) + "\n";

                String ciphertext = onlyEng(readFile());
                String plaintext = "";
                message += "Ciphertext: " + ciphertext + "\n";
                if (radioRailFence.isSelected()) {
                    message += "Language: English\n";
                    RailFence railFence = new RailFence();
                    plaintext = railFence.decode(ciphertext, Integer.parseInt(input.getText()));
                    writeFile(plaintext);
                }
                message += "Plaintext: " + plaintext + "\n";
            }
            JOptionPane.showMessageDialog(null,
                    message,
                    "Output",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    public static void main(String[] args) {
        Lab1Frame app = new Lab1Frame();
        app.setVisible(true);
        System.out.println( readFile() );
    }
}
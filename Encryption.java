import java.util.Scanner;

public class Encryption {
    public static void main(String[] args) {
        // create a scanner to scan user input
        Scanner scan = new Scanner(System.in);
        // start the encryption program
        // prompts
        System.out.println("Welcome to the Vigenère Cypher Encryption Program.");
        System.out.println("Please enter PASS followed by your passkey to set up a passkey for encrypting or decrypting");
        System.out.println("Please enter ENCRYPT followed by your passkey to encrypt it.");
        System.out.println("Please enter DECRYPT followed by your passkey to decrypt it.");
        System.out.println("Please enter QUIT to exit the program.");
        // keep running the program until the user quits.
        while (true) {
            String input = scan.nextLine().trim();
            if (input.equalsIgnoreCase("QUIT")) {
                System.out.println("Program terminated."); // if user types "QUIT", terminate the program.
                break;
            }
            // split the input into separate words
            String[] p = input.split("\\s+", 2);
            if (p.length < 2) {
                System.out.println("Invalid input format. It should be '<ACTION> <MESSAGE>'");
                continue;
            }
            String action = p[0];
            String message = p[1];
            // if user types "PASS", set up a passkey
            if (action.equalsIgnoreCase("PASS")) {
                System.out.println("Passkey set up successfully!");
                continue;
            }
            // if user types "ENCRYPT", encrypt the message
            if (action.equalsIgnoreCase("ENCRYPT")) {
                System.out.println("Encrypted message: " + encrypt(message));
                continue;
            }
            // if user types "DECRYPT", decrypt the message
            if (action.equalsIgnoreCase("DECRYPT")) {
                System.out.println("Decrypted message: " + decrypt(message));
                continue;
            }
            // if the action is not valid, print the error message
            System.out.println("Invalid action. Please enter PASS, ENCRYPT, DECRYPT, or QUIT.");
        }
    }
}

import java.util.Scanner;

public class Encryption {
    // store the passkey globally
    private static String passkey = null;
    // String function to encrypt the plain text message using Vigenere cypher logic
    public static String encrypt(String plain_text, String passkey) {
        // create a StringBuilder to store the encrypted message
        StringBuilder encrypted_message = new StringBuilder();
        // convert plain text and passkey to uppercase
        plain_text = plain_text.toUpperCase();
        passkey = passkey.toUpperCase();
        // loop through each character in the plain text message
        for (int i = 0; i < plain_text.length(); i++) {
            // get the current character
            char current_char = plain_text.charAt(i);
            // get the corresponding character from the passkey
            char passkey_char = passkey.charAt(i % passkey.length());
            // encrypt the current character using the passkey character
            char encrypted_char = (char) ((current_char + passkey_char) % 128);
            // append the encrypted character to the encrypted message
            encrypted_message.append(encrypted_char);
        }
        // return the encrypted message as a string
        return encrypted_message.toString();
    }
    // String function to decrypt the Vigenere cypher
    public static String decrypt(String encrypted_message, String passkey) {
        // create a StringBuilder to store the decrypted message
        StringBuilder decrypted_message = new StringBuilder();
        // convert encrypted message and passkey to uppercase
        encrypted_message = encrypted_message.toUpperCase();
        passkey = passkey.toUpperCase();
        // loop through each character in the encrypted message
        for (int i = 0; i < encrypted_message.length(); i++) {
            // get the current character
            char current_char = encrypted_message.charAt(i);
            // get the corresponding character from the passkey
            char passkey_char = passkey.charAt(i % passkey.length());
            // decrypt the current character using the passkey character
            char decrypted_char = (char) ((current_char - passkey_char + 128) % 128);
            // append the decrypted character to the decrypted message
            decrypted_message.append(decrypted_char);
        }
        // return the decrypted message as a string
        return decrypted_message.toString();
    }
    public static void main(String[] args) {
        // create a scanner to scan user input
        Scanner scan = new Scanner(System.in);
        // start the encryption program
        // prompts
        System.out.println("Welcome to the Vigenère Cypher Encryption Program.");
        System.out.println("List of possible commands:");
        System.out.println("- PASS <passkey>: Set up a passkey");
        System.out.println("- ENCRYPT <message>: Encrypt message using stored passkey");
        System.out.println("- DECRYPT <message>: Decrypt message using stored passkey");
        System.out.println("- QUIT: Exit the program");
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
                System.out.println("Invalid input format. It should be '<ACTION> <PLAINTEXT>'");
                continue;
            }
            String action = p[0];
            String content = p[1];
            // if user types "PASS", set up a passkey
            if (action.equalsIgnoreCase("PASS")) {
                passkey = content;
                System.out.println("Passkey set up successfully!");
                continue;
            }
            // if the passkey is not set up, print the error message
            if (passkey == null) {
                System.out.println("ERROR: Passkey not set");
                continue;
            }
            // if user types "ENCRYPT", encrypt the plain text message
            if (action.equalsIgnoreCase("ENCRYPT")) {
                System.out.println("Encrypted result: " + encrypt(content, passkey));
                continue;
            }
            // if user types "DECRYPT", decrypt the Vigenere cypher
            if (action.equalsIgnoreCase("DECRYPT")) {
                System.out.println("Decrypted result: " + decrypt(content, passkey));
                continue;
            }
            // if the action is not valid, print the error message
            System.out.println("Invalid action. Please enter PASS, ENCRYPT, DECRYPT, or QUIT.");
        }
        scan.close();
    }
}

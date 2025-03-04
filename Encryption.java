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
            char plain_char = plain_text.charAt(i); // get each character in the plain text message
            char passkey_char = passkey.charAt(i % passkey.length()); // get each character in the passkey
            // ignore all non-alphabet characters
            if (plain_char < 'A' || plain_char > 'Z') {
                encrypted_message.append(plain_char);
                continue;
            }
            char encrypted_char = (char) (((plain_char - 'A' + (passkey_char - 'A')) % 26) + 'A');
            encrypted_message.append(encrypted_char);
        }
        // return the encrypted message as a string
        return encrypted_message.toString();
    }
    
    // check if the input to encryption, decryption, or passkey contains non-alphabetic characters. 
    // if it does, it is invalid
    public static boolean containsNonAlphabetic(String str) {
        if (str == null || str.isEmpty()) {
            return false; // handle empty/null strings as needed
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isLetter(str.charAt(i))) {
                return true;
            }
        }
        return false;
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
            char encrypted_char = encrypted_message.charAt(i); // get each character in the encrypted message
            char passkey_char = passkey.charAt(i % passkey.length()); // get each character in the passkey
            // ignore all non-alphabet characters
            if (encrypted_char < 'A' || encrypted_char > 'Z') {
                decrypted_message.append(encrypted_char);
                continue;
            }
            char decrypted_char = (char) (((encrypted_char - passkey_char + 26) % 26) + 'A');
            decrypted_message.append(decrypted_char);
        }
        // return the decrypted message as a string
        return decrypted_message.toString();
    }

    // main function
    public static void main(String[] args) {
        // create a scanner to scan user input
        Scanner scan = new Scanner(System.in);
        // start the encryption program
        // keep running the program until the user quits.
        while (true) {
            String input = scan.nextLine().trim();
            if (input.equalsIgnoreCase("QUIT")) {
                System.out.println("Encryption program terminated."); // if user types "QUIT", terminate the program.
                break;
            }
            // split the input into separate words
            String[] p = input.split("\\s+", 2);
            if (p.length < 2) {
                System.out.println("Invalid input format. It should be '<ACTION> <MESSAGE>'");
                continue;
            }
            String action = p[0];
            String content = p[1];
            // if user types "PASSWORD", set up a passkey
            if (action.equalsIgnoreCase("PASSWORD")) {
                if (containsNonAlphabetic(content)) {
                    System.out.println("ERROR: Passkey contains non-alphabetic characters");
                } 
                else if(content == ""){
                    System.out.println("ERROR: Passkey is empty");
                }
                else {
                    passkey = content;
                }
                continue;
            }
            // if the passkey is not set up, print the error message
            if (passkey == null) {
                System.out.println("ERROR: Passkey not set");
                continue;
            }
            // if user types "ENCRYPT", encrypt the plain text message
            if (action.equalsIgnoreCase("ENCRYPT")) {
                if(containsNonAlphabetic(content)){
                    System.out.println("ERROR: Message contains non-alphabetic characters");               
                }
                else if (content == "") {
                    System.out.println("ERROR: Message is empty");
                }
                else{
                    System.out.println("Encrypted result: " + encrypt(content, passkey));
                }
                continue;
            }
            // if user types "DECRYPT", decrypt the Vigenere cypher
            if (action.equalsIgnoreCase("DECRYPT")) {
                if(containsNonAlphabetic(content)){
                    System.out.println("ERROR: Message contains non-alphabetic characters");               
                }
                else if (content == "") {
                    System.out.println("ERROR: Message is empty");
                }
                else{
                    System.out.println("Decrypted result: " + decrypt(content, passkey));
                }
                continue;
            }
            // if the action is not valid, print the error message
            System.out.println("Invalid action. Please enter PASS, ENCRYPT, DECRYPT, or QUIT.");
        }
        scan.close();
    }
}
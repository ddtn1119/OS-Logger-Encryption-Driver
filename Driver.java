import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    // check if the input contains non-alphabetic characters
    public static boolean containsNonAlphabetic(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isLetter(c)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Correct usage: java Driver <log_file_name>");
            return;
        }

        String log_file_name = args[0];
        // store the command history using array list
        List<String> cmd_history = new ArrayList<>();
        // initialise the string of password to be empty
        String password = "";

        try {
            // initialise the logger process
            Process log_process = new ProcessBuilder("java", "Logger", log_file_name).start();
            PrintWriter log_writer = new PrintWriter(log_process.getOutputStream(), true);
            // initialise the encryption process
            Process encryption_process = new ProcessBuilder("java", "Encryption").start();
            BufferedReader encryption_reader = new BufferedReader(new InputStreamReader(encryption_process.getInputStream()));
            PrintWriter encryption_writer = new PrintWriter(encryption_process.getOutputStream(), true);
            // display the menu
            log_writer.println("START Driver started.");
            System.out.println("The driver program started.\n");
            System.out.println("List of available commands:");
            System.out.println("1. PASSWORD <password>");
            System.out.println("2. ENCRYPT <message>");
            System.out.println("3. DECRYPT <message>");
            System.out.println("4. HISTORY");
            System.out.println("5. QUIT\n");
            System.out.println("Note: Non-alphabetical characters in <password> or <message> are not allowed.\n");
            // create a scanner to read user inputs
            Scanner scan = new Scanner(System.in);
            // loop until the user quits
            while (true) {
                // prompt the user to enter command
                System.out.print("Enter command: ");
                // remove leading or trailing whitespaces in commands
                String cmd = scan.nextLine().trim();
                // still continue even if the command is empty
                if (cmd.isEmpty()) continue;
                // split command
                String[] p = cmd.split("\\s+", 2);
                String action = p[0];
                String message = (p.length > 1) ? p[1] : ""; // in some commands like HISTORY or QUIT, we don't need <message>

                // HISTORY SELECTION MENU
                if (action.equalsIgnoreCase("HISTORY")) {
                    if (cmd_history.isEmpty()) {
                        System.out.println("History is empty.");
                        continue;
                    }
                    // give each command in history its number index for references
                    System.out.println("Command history:");
                    for (int i = 0; i < cmd_history.size(); i++) {
                        System.out.println((i + 1) + ". " + cmd_history.get(i));
                    }
                    // prompt the user to enter a number to reuse a command
                    System.out.print("Enter a number to reuse a command, or 0 to return: ");
                    int choice;
                    try {
                        choice = Integer.parseInt(scan.nextLine().trim());
                        if (choice == 0) continue;
                        if (choice < 1 || choice > cmd_history.size()) {
                            System.out.println("Invalid selection.");
                            continue;
                        }
                    } 
                    catch (NumberFormatException e) {
                        System.out.println("Invalid input.");
                        continue;
                    }
                    // reuse selected command
                    cmd = cmd_history.get(choice - 1); 
                    p = cmd.split("\\s+", 2);
                    action = p[0];
                    message = (p.length > 1) ? p[1] : "";
                    // prevent reuse of password command
                    if (action.equalsIgnoreCase("PASSWORD")) {
                        System.out.println("ERROR: You cannot reuse password command for security purposes. Please set up a new password.");
                        log_writer.println("HISTORY Selected command: " + cmd);
                        log_writer.println("ERROR You cannot reuse password command for security purposes. Please set up a new password.");
                        continue;
                    }
                    // otherwise, log and execute the selected command
                    System.out.println("Reusing command: " + cmd);
                    log_writer.println("HISTORY Selected command: " + cmd);
                }
                // logging QUIT into the log file
                if (action.equalsIgnoreCase("QUIT")) {
                    log_writer.println("QUIT Driver terminated.");
                    log_writer.flush(); // force the log writer to log the command
                    log_writer.close(); // close the log writer
                    log_process.waitFor(); // wait until the log writer is done
                    // clean up everything before terminating the program
                    encryption_writer.close();
                    encryption_reader.close();
                    scan.close();
                    // destroy all processes before terminating the program
                    log_process.destroy();
                    encryption_process.destroy();
                    System.out.println("Encryption program terminated.");
                    break;
                }

                // PASSWORD handling (but do NOT log the actual password)
                if (action.equalsIgnoreCase("PASSWORD")) {
                    if (containsNonAlphabetic(message)) {
                        // non-alphabetic characters in passkey are not allowed, thus making passkey invalid.
                        System.out.println("ERROR: Passkey contains non-alphabetic characters.");
                        log_writer.println("ERROR Passkey contains non-alphabetic characters.");
                    } 
                    else if (message == "") {
                        // passkey cannot be empty
                        System.out.println("ERROR: Passkey cannot be empty.");
                        log_writer.println("ERROR Passkey cannot be empty.");
                    }
                    else {
                        // store message in the password variable
                        password = message;
                        encryption_writer.println("PASSWORD " + password);
                        encryption_writer.flush();
                        // replace the password with stars
                        String hidden_password = password.replaceAll("[a-zA-Z]", "*");
                        System.out.println("Password set successfully.");
                        log_writer.println("PASSWORD " + hidden_password);
                        log_writer.println("INFO Password set successfully.");
                        cmd_history.add("PASSWORD " + hidden_password);  // hide password in history
                    }
                }
                // ENCRYPTION handling
                else if (action.equalsIgnoreCase("ENCRYPT")) {
                    if (password.isEmpty()) {
                        System.out.println("Error: No password set. Please use PASSWORD <password>.");
                        log_writer.println("ERROR No password set. Please use PASSWORD <password>.");
                        continue;
                    }
                    encryption_writer.println("ENCRYPT " + message);
                    encryption_writer.flush();
                    String encrypted_response = encryption_reader.readLine();
                    System.out.println(encrypted_response);
                    log_writer.println("ENCRYPT " + message);
                    log_writer.println("RESULT " + encrypted_response);
                    cmd_history.add("ENCRYPT " + message);  // store original message (not the result)
                }
                // DECRYPTION handling
                else if (action.equalsIgnoreCase("DECRYPT")) {
                    if (password.isEmpty()) {
                        System.out.println("Error: No password set. Please use PASSWORD <password>.");
                        log_writer.println("ERROR No password set. Please use PASSWORD <password>.");
                        continue;
                    }
                    encryption_writer.println("DECRYPT " + message);
                    encryption_writer.flush();
                    String decrypted_response = encryption_reader.readLine();
                    System.out.println(decrypted_response);
                    log_writer.println("DECRYPT " + message);
                    log_writer.println("RESULT " + decrypted_response);
                    cmd_history.add("DECRYPT " + message);  // store original message (not the result)
                }
                // invalid command error handling
                else {
                    System.out.println("Invalid action. Please enter PASSWORD, ENCRYPT, DECRYPT, HISTORY, or QUIT.");
                }
            }
        } 
        catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }
}
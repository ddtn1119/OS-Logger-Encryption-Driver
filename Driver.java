import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;


public class Driver {
    // main function
    public static void main(String[] args) {
        // ensure that the user input format on the terminal is correct
        if (args.length != 1) {
            System.out.println("Correct usage: java Driver <log_file_name>");
            return;
        }
        // get the log file name from the command line argument
        String log_file_name = args[0];
        // store the history of commands in a list
        List<String> cmd_history = new ArrayList<>();
        // store the password in String
        String password = "";

        try {
            // build and start the logger process first
            Process log_process = new ProcessBuilder("java", "Logger", log_file_name).start();
            PrintWriter log_writer = new PrintWriter(log_process.getOutputStream(), true);
            // then build and start the encryption process
            Process encryption_process = new ProcessBuilder("java", "Encryption").start();
            BufferedReader encryption_reader = new BufferedReader(new InputStreamReader(encryption_process.getInputStream()));
            PrintWriter encryption_writer = new PrintWriter(encryption_process.getOutputStream(), true);
            // start the driver
            log_writer.println("START Driver started.");
            // display the menu
            System.out.println("The driver program started.\n");
            System.out.println("\nList of available commands:");
            System.out.println("1. PASSWORD <password>");
            System.out.println("2. ENCRYPT <message>");
            System.out.println("3. DECRYPT <message>");
            System.out.println("4. HISTORY");
            System.out.println("5. QUIT\n");
            // create a scanner to read user inputs from the console.
            Scanner scan = new Scanner(System.in);
            // loop until the user quits
            while (true) {
                // prompt the user to enter command
                System.out.print("Enter command: ");
                String cmd = scan.nextLine().trim();
                // continue if command is empty
                if (cmd.isEmpty()) {
                    continue;
                }
                // add the command to the command history list
                cmd_history.add(cmd);
                // log the command
                log_writer.println(cmd);
                if (cmd.equalsIgnoreCase("QUIT")) {
                    System.out.println("Encryption program terminated."); // if user types "QUIT", terminate the program.
                    break;
                }
                // split the input into separate words
                String[] p = cmd.split("\\s+", 2);
                if (p.length < 2) {
                    System.out.println("Invalid input format. It should be '<ACTION> <MESSAGE>'");
                    continue;
                }
                String action = p[0];
                String message = p[1];
                // if the action is "PASSWORD", send "PASS <password>" to the encryption process
                if (action.equalsIgnoreCase("PASSWORD")) {
                    password = message;
                    encryption_writer.println("PASSWORD " + password); // send "PASSWORD <password>" to Encryption process
                    encryption_writer.flush();
                    System.out.println("Password set successfully to " + password + ".");
                    log_writer.println("INFO Password set to " + password + ".");
                }
                // if the action is "ENCRYPT", send the message to the encryption process
                else if (action.equalsIgnoreCase("ENCRYPT")) {
                    if(password.isEmpty()){
                        System.out.println("Error: No password set. Please use PASSWORD <password>.");
                        continue;
                    }
                    encryption_writer.println("ENCRYPT " + message);
                    encryption_writer.flush();
                    String encrypted_response = encryption_reader.readLine();
                    System.out.println(encrypted_response);
                    log_writer.println("RESPONSE " + encrypted_response);
                }
                // if the action is "DECRYPT", send the message to the encryption process
                else if (action.equalsIgnoreCase("DECRYPT")) {
                    if(password.isEmpty()){
                        System.out.println("Error: No password set. Please use PASSWORD <password>.");
                        continue;
                    }
                    encryption_writer.println("DECRYPT " + message);
                    encryption_writer.flush();
                    String decrypted_response = encryption_reader.readLine();
                    System.out.println(decrypted_response);
                    log_writer.println("RESPONSE " + decrypted_response);
                }
                // if the action is "HISTORY", display the command history
                else if (action.equalsIgnoreCase("HISTORY")) {
                    System.out.println("Command history:");
                    for (String history : cmd_history) {
                        System.out.println(history);
                    }
                }
                else {
                    System.out.println("Invalid action. Please enter PASSWORD, ENCRYPT, DECRYPT, HISTORY, or QUIT.");
                }
            }
            // cleanup
            scan.close();
            log_writer.close();
            encryption_writer.close();
            encryption_reader.close();
            log_process.destroy();
            encryption_process.destroy();
        }
        catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }
}
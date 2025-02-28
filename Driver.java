import java.io.*;
import java.util.*;
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

        try {
            // build and start the logger process first
            Process log_process = new ProcessBuilder("java", "Logger", log_file_name).start();
            PrintWriter log_writer = new PrintWriter(log_process.getOutputStream(), true);
            // then build and start the encryption process
            Process encryption_process = new ProcessBuilder("java", "Encryption").start();
            BufferedReader encryption_reader = new BufferedReader(new InputStreamReader(encryption_process.getInputStream()));
            PrintWriter encryption_writer = new PrintWriter(encryption_process.getOutputStream(), true);
            // start the driver
            log_writer.println("The driver started.");
            // display the menu
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
            }
        }
        catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }
}
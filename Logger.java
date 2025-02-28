import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Logger {
    // new command: clear the log file
    private static void clearLogFile(String logFileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(logFileName))) {
            writer.print("");
        } catch (IOException e) {
            System.err.println("Error occurred when clearing log file: " + e.getMessage());
        }
    }

    // main function
    public static void main(String[] args) {
        // print the error message if the number of arguments is not equal to 1 and the argument is not correct.
        if (args.length != 1) {
            System.out.println("Correct usage: java Logger <log_file_name>");
            return;
        }
        // file name is indicated after "java Logger" in index zero
        String log_file_name = args[0];
        // create a new logger file if it does not exist yet.
        try {
            File myFile = new File(log_file_name);
            if (myFile.createNewFile()) {
                System.out.println("Log file created: " + myFile.getName());
            } else {
                System.out.println("Log file already exists.");
            }
        } 
        catch (IOException e) {
            System.out.println("An error occurred while creating a log file.");
            e.printStackTrace();
        }

        // write to the logger text file
        try (PrintWriter log_writer = new PrintWriter(new FileWriter(log_file_name, true));
            // create a scanner to read user inputs
            Scanner scan = new Scanner(System.in)) {
            // start the logger
            System.out.println("Logger started. Enter log messages (type CLEAR to clear log file, QUIT to exit):");
            // keep running the program until the user quits.
            while (true) {
                String input = scan.nextLine().trim();
                if (input.equalsIgnoreCase("QUIT")) {
                    System.out.println("Logger terminated."); // if user types "QUIT", terminate the logger.
                    break;
                }
                if (input.equalsIgnoreCase("CLEAR")) {
                    clearLogFile(log_file_name); // if user types "CLEAR", clear the log file
                    System.out.println("Log file cleared.");
                    continue;
                }
                // split the input into separate words
                String[] p = input.split("\\s+", 2);
                if (p.length < 2) {
                    System.out.println("Invalid log message format. It should be '<ACTION> <MESSAGE>'");
                    continue;
                }
                
                String action = p[0];
                String message = p[1];
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                String logEntry = String.format("%s [%s] %s", timestamp, action, message);
                // print the log message
                log_writer.println(logEntry);
                log_writer.flush();
                System.out.println("Logged successfully! Please check your log file.");
            }
        } 
        catch (IOException e) {
            System.err.println("Error occurred when writing to log file: " + e.getMessage());
        }
    }
}
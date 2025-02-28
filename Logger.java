import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Logger {
    // clear log file method
    private static void clearLogFile(String logFileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(logFileName))) {
            writer.print("");
        } catch (IOException e) {
            System.err.println("Error occurred when clearing log file: " + e.getMessage());
        }
    }
    
    // main method
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Correct usage: java Logger <log_file_name>");
            return;
        }

        String log_file_name = args[0];

        // create a log file if it does not exist yet
        try {
            File myFile = new File(log_file_name);
            if (myFile.createNewFile()) {
                System.out.println("Log file created: " + myFile.getName());
            } else {
                System.out.println("Log file already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating a log file.");
            e.printStackTrace();
        }

        try (PrintWriter log_writer = new PrintWriter(new FileWriter(log_file_name, true));
            Scanner scan = new Scanner(System.in)) {

            System.out.println("Logger started. Enter log messages (type CLEAR to clear log file, QUIT to exit):");
            while (true) {
                String input = scan.nextLine().trim();
                if (input.equalsIgnoreCase("QUIT")) {
                    System.out.println("Logger terminated.");
                    break;
                }
                if (input.equalsIgnoreCase("CLEAR")) {
                    clearLogFile(log_file_name);
                    System.out.println("Log file cleared.");
                    continue;
                }

                String[] p = input.split("\\s+", 2);
                if (p.length < 2) {
                    System.out.println("Invalid log message format. It should be '<ACTION> <MESSAGE>'");
                    continue;
                }

                String action = p[0];
                String message = p[1];
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                String logEntry = String.format("%s [%s] %s", timestamp, action, message);
                log_writer.println(logEntry);
                log_writer.flush();
                System.out.println("Logged successfully! Please check your log file.");
            }
        } catch (IOException e) {
            System.err.println("Error occurred when writing to log file: " + e.getMessage());
        }
    }
}
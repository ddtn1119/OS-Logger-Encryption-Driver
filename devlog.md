CS 4348.501 Project 1 [2025-02-26 16:17]

The logger program seems to be the easiest since all we do is printing out the log message with time frames.
First, I wrote code to create a log file if it does not exist yet. Then I implemented the function to print the message to the console after every time it has been logged and write the log message to the log file as long as the user does not quit the program. When everything works, I implemented a new function to clear the log file if the command is clear. This is not indicated in the project description but I feel like it is one of the essential commands for this logger program. Finally, I utilised the Process class to execute the process to cmd.exe on Windows.

[2025-02-27 14:56]
Thinking about how should I start working on the Encryption Program part of the project... First, I am going to create a basic code template for this program.
# CS 4348.501 Project 1 Devlog

[2025-02-26 16:17]
The logger program seems to be the easiest since all we do is printing out the log message with time frames.
First, I wrote code to create a log file if it does not exist yet. Then I implemented the function to print the message to the console after every time it has been logged and write the log message to the log file as long as the user does not quit the program. When everything works, I implemented a new function to clear the log file if the command is clear. This is not indicated in the project description but I feel like it is one of the essential commands for this logger program. Finally, I utilised the Process class to execute the process to cmd.exe on Windows (just to test how the Process class works in Java).

[2025-02-27 14:56]
Thinking about how should I start working on the Encryption Program part of the project... First, I am going to create a basic code template for this program. 

[2025-02-27 16:19]
I have implemented the code template for the program, and tried to implement encrypt and decrypt functions for the Vigenere Cypher Encryption Program based on my research, but somehow, the result is empty (my program does not print out anything yet). Maybe the problem lies in the encryption logic...

[2025-02-27 17:46]
I have figured out one issue. The issue was my program expected a single input command like "ENCRYPT <message>" but instead using a predefined passkey. It treated both the plain text message and the passkey (e.g., "HELLO") the same. And the passkey was not stored yet. The PASS command did nothing except print a message. Because the encryption function was not given a correct passkey and plain text, it returned unexpected results. So what I did was storing the passkey when the user enters "PASS <passkey>" in the content variable, and used the stored passkey when encrypting and decrypting messages, then ensured encryption & decryption logic correctly processes characters.

[2025-02-07 17:51]
However, after I finished this issue above, the printed results are not correct yet... Maybe the issues lied in the encryption logic again (e.g., the Vigenere cypher encryption formula). Anyway, my goal is to complete the Encryption Program by the end of today, making sure that the program works accurately and perfectly.

[2025-02-07 18:09]
I figured out that the issue came from the encryption formula. So for the encryption formula, instead of "(char) ((current_char + passkey_char) % 128)", I used "char encrypted_char = (char) (((plain_char - 'A' + (passkey_char - 'A')) % 26) + 'A')". For the decryption formula, I used the following formula "char decryptedChar = (char) (((encryptedChar - passkeyChar + 26) % 26) + 'A')". After compiling & testing the Encryption program, I found out that it finally works successfully (e.g., "HELLO" is encrypted to "OIWWC"). All functions work perfectly, except that I deliberately made the output structure different from the output in the project 1 description to make it look more "professional" a little bit. I will continue updating and monitoring this program if necessary, then I will move to the Driver program portion of this project. 

[2025-02-07 18:38]
Note: I forgot to create this devlog.md before starting this project. I created this devlog while working on the Logger program instead. My apologies.

[2025-02-07 23:00]
In this night session, I plan to start implementing the Driver program. I will set up the template for the Driver program which includes the scanner to read user inputs. First, I wrote the code that checks the input command from the user is correct (Usage: java Driver <log_file_name>). The log file name should be in String and the command history should be store in a list/array list. I tried to build the process to execute the logger first using Process class. PrintWriter will be used to write text to output stream. For the encryption program process. I also built the process, created a PrintWriter (to write results to the log file) and BufferedReader to read user inputs. Then I created the menu and prompts to prompt user to enter commands and loop until the user quits. At this point I am a bit tired but I will continue working on this tomorrow or later. I will write the logic to execute logger and encryption commands as I know.

[2025-02-28 14:57]
Today I will be implementing the actual logic for the program loop. 
After finishing the loop, when I tested PASSWORD, ENCRYPT, and DECRYPT, the results made no sense, so I try again...
At 15:33, PASSWORD works, however, all other commands do not work very well... so I deleted my debugging prompts in Encryption and Logger and tried again...
After testing multiple times, I don't know why error still occurs such as passkey not set...
Then, I found out that the problems are due to miscommunication between Driver and Encryption... the structure/wording do not match. I fixed them accordingly.
After I fixed all issues, deleted all unnecessary/redundant prompts/code in Encryption.java, and ensured that both programs communicated effectively, all commands worked!
Then, the history command does not work well (invalid input format error occurred), I figured out that the error is due to the handle input splitting.
I fixed the issue by modifying the input handling to allow single-word commands like "HISTORY" and "QUIT".
But then the QUIT command is not sent to the encryption program and logger before exiting the program. I don't know how.
And after this, I implemented error checking to my code. There were so many issues happening because programs miscommunicated, like error checking did not work properly. It did not check errors properly. But they were all fixed.

[2025-02-08 21:54]
So a little bit about my last session... 
To ensure that passwords are never logged, and that history allows users to select past commands to be reused via a menu instead of just displaying them outright. I made the PASSWORD command to be logged generically (without storing the actual password). Users can select a previous PASSWORD, ENCRYPT, or DECRYPT command by index number instead of retyping them. All encrypted/decrypted outputs are stored in history and logged in the log file. I also finally fixed QUIT logging errors, to ensure that QUIT is logged properly written to the log before terminating the program by forcing it to be logged using flush(), close the log writer, encryption writer, reader, and scanner, and then using waitFor() the writer to finish logging QUIT into the log file, and finally destroying all processes before terminating the driver program. I made sure that the logger detects EOF (end-of-file) and exits, and other resources are closed properly before destroying processes, resolving the program's frozen issues and allowing the program to exit properly when QUIT is entered. I also managed to make the program checking errors properly by implementing containsNonAlphabetic() boolean functions in both "Encryption.java" and "Driver.java" and making sure that non-alphabetical characters are not allowed when encrypting passwords using Vigenere cypher. All functions seem to work well for now.
Well, I made a minor change. I made the program to store all passwords in the form of stars by replacing all characters in the password to be "*". That way, passwords are hidden in the log file. 
Also, I'm thinking, should I allow users to reuse passwords from history? Even if they are hidden? I don't know yet...
Maybe for security purposes, I made the program preventing users from reusing the old password and that they must set up a new password (edge case).

[2025-03-04 15:08]
So, my overall thoughts about this first project...
The Logger program has to be the easiest part since all we have to do is printing messages in a correct format to a logging text file with timestamps. Other than some syntax errors I encountered along the way, I had no troubles completing this part of the program.
The Encryption program is harder since this is the first time I am introduced to Vigenere cypher. I have done a lot of research about this concept and even looking in some samples of how others produce/implement Vigenere cypher and encrypt messages, passwords, etc. Initially, the program does not produce the correct outputs (like "HELLO" should be encrypted to "OIWWC"), but after I made changes to the encryption formula like instead of "(char) ((current_char + passkey_char) % 128)", I used "(char) ((currentChar - passkeyChar + 128) % 128)" and do the same (but backwards) for the decryption function, I was finally able to produce the correct outputs for all types of strings.
The Driver program must be the most difficult. This project is also the first time I was introduced to Process class in Java. First, I created a simple template (copying some of the necessary logic from Logger and Encryption), then built processes for both programs (executing Logger first, then Encryption), built the menu for users to choose and interact, and implemented other necessary logic (encrypting the strings correctly, writing to log file, and outputting the results of commands, etc.) I would say the biggest challenge is to make sure that every program can communicate with each other perfectly because a lot of time, because programs miscommunicated, the outputs become unexpectedly incorrect because logic does not match. So the important thing I learn is to make sure logic in all programs can link to each other and communicate well. I also learned new functions and methods that came along with Process class like flush() (to force the writing of any buffered output to the stream) and waitFor() (causes a thread to wait until the process terminated). These things are very helpful in helping me solving problems with Process (like above, I wrote that I kept struggling with why the EXIT/QUIT command is not logged to the log file), but I solved that problem by forcing the log writer to log the command, then close the writer, wait until the log writer is done, clean up/close every writer, scanner,... to avoid leak and destroy processes... 
This project helped me gain hands-on experience and knowledge on process scheduling, drivers, and thread programming for the first time.

[2025-03-04 16:17]
Just go back to my programs and handle the cases when passwords or arguments are empty... But they are done.
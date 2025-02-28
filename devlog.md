CS 4348.501 Project 1 [2025-02-26 16:17]

The logger program seems to be the easiest since all we do is printing out the log message with time frames.
First, I wrote code to create a log file if it does not exist yet. Then I implemented the function to print the message to the console after every time it has been logged and write the log message to the log file as long as the user does not quit the program. When everything works, I implemented a new function to clear the log file if the command is clear. This is not indicated in the project description but I feel like it is one of the essential commands for this logger program. Finally, I utilised the Process class to execute the process to cmd.exe on Windows.

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
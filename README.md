# CS 4348 Project 1: Logger, Encryption, Driver (JAVA)

**Logger.java**: writes log messages to a log file (text file). The log messages are recorded, with a timestamp in 24-hour notation.
Each line represents a log message.

**Encryption.java**: encrypts an argument (messages, sentences, words, phrases, etc.) using Vigenere cypher and a passkey/password, and then decrypts them.

**Driver.java**: controls the executions/processes of Logger and Encryption programs.

How to compile and test these programs:

1. Open a new terminal on an IDE (I used Visual Studio Code) or Command Prompt or PowerShell (to test on cs1 or cs2 machines) and compile all Java code files using:
```
javac Logger.java
javac Encryption.java
javac Driver.java
```
2. Test the driver program. The driver will create two processes to execute the logger (first) and then the encryption program (second).
```
java Driver logger.txt
```
The text file `logger.txt` can have any names. If the file does not exist yet, it will be created.
3. Enjoy the program!

Notes:
* The inputs and outputs are not exactly worded like in the project descriptions. 
* For security purposes, passwords cannot be reused. Please create new passwords if desired.


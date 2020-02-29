# Tic-Tac-Toe

Tic-Tac-Toe game that supports multiple games simultaneously. The communication between players is managed via sockets. 
The game is to be played on your local machine. The server was deployed onto AWS EC2, but currently the server is not online. 

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Java 8.x SDK and above

### Installing
Option 1:
1. Download the two jar files called Client and Server.

2. Run the server by opening the Server jar file.

3. Each player will run the Client jar file.

Option 2:
1. Download all of the src(.java) files into a folder. Compile the files by opening a shell to the directory of the
   folder where you saved the files. Enter the following command:
   ```
   javac *.java
   ```
   Files of the type .class will be created.
   
2. Run the command:
   ```
   java Server
   ```

3. Each player must run the command:
   ```
   java Client
   ```

## Built With

Java

## Authors

Thomas Yee
Shenghan Zhang (https://github.com/pp5685)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

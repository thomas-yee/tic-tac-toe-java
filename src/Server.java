/**
 * A java data type that has data fields and methods,
 * representing the server for the game.
 * Uses a thread pool and handles client's requests.
 * @author Thomas Yee & Shenghan Zhang
 * @version 1.0
 * @since February 12, 2020
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

////////////////////////////////////////////////////
////Instance Variables
    private Socket aSocket;
    private ServerSocket serverSocket;
    private PrintWriter socketOut;
    private BufferedReader socketIn;
    private ExecutorService pool;
    static ArrayList<Game> users = new ArrayList<Game>();
    static int i = 0;

//////////////////////////////////////////////////
////Constructor
    public Server() {
        try {
            serverSocket = new ServerSocket(9898);
            pool = Executors.newCachedThreadPool();
            System.out.println("Server is running...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

///////////////////////////////////////////////////////////////
////Instance methods
    public void runServer() {
        try {
            while (true) {
                //Accepting the connection for players
                aSocket = serverSocket.accept();
                socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
                socketOut = new PrintWriter(aSocket.getOutputStream(), true);
                //Makes sure only 2 players can play at one time
                if(users.size()%2==0) {
                	System.out.println("A new game is running!");
                }
                //Handles client's request
                Game game = new Game(aSocket, "Player " + ((users.size()%2)+1) ,socketIn, socketOut,i);
                //Add client to client list
                users.add(game);
                //Start thread
                pool.execute(game);

                //i is incremented for new player
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socketIn.close();
            socketOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

////////////////////////////////////////////////////////
////Main
    public static void main (String [] args) {
        Server myServer = new Server();
        myServer.runServer();
    }
}

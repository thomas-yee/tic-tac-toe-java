import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;

/**
 * A java data type file that has data fields and
 * methods, representing a player of the game.
 * Has methods to show how the game is played and
 * allows players to take a turn.
 * @author Thomas Yee
 * @version 1.0
 * @since October 4, 2019
 */

public class Player {
/////////////////////////////////////////////////////////////////////
////Instance Variables

    /**
     * The name of the player.
     */
    private String name;

    /**
     * The Tic-Tac-Toe board.
     * The board is a Board data type.
     */
    private Board board;

    /**
     * The opponent of the player. Has a name and
     * an "O" or "X" marker.
     */
    private Player opponent;

    /**
     * The marker the player or opponent
     * uses to mark the board. Either "X" or
     * "O".
     */
    private char mark;

    private Socket aSocket;
    private PrintWriter socketOut;
    private BufferedReader socketIn;


///////////////////////////////////////////////////////////////////////////
////Constructors
    /**
     * Constructs a Person object with the specified values
     * for the name and mark. Some of the values of the data fields
     * are supplied by the parameters.
     * @param name The name of the Person object.
     * @param mark The mark of the Person object.
     */
    public Player(String name, char mark, Socket aSocket) throws IOException {
        this.name = name;
        this.mark = mark;
        this.aSocket = aSocket;
        socketOut = new PrintWriter(aSocket.getOutputStream(), true);
        socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
    }
//////////////////////////////////////////////////////////////////////////////////////
////Instance Methods
    /**
     * The player and the opponent take turns putting their mark
     * on the board until either one of them win, or the
     * board becomes full. X-Player goes first.
     * @throws IOException
     */
    public void play() throws IOException {
        //Sends names to GUI
        //Player x name
        try {
    	String xPlayerName = this.getName();
        socketOut.println("PlayerX " + xPlayerName);
        opponent.socketOut.println("PlayerX " + xPlayerName);

        //player o name
        String oPlayerName = opponent.getName();
        socketOut.println("PlayerO " + oPlayerName);
        opponent.socketOut.println("PlayerX " + xPlayerName);

        while (!board.xWins() && !board.oWins() && !board.isFull()) {
                //X-Player turn if O-Player hasn't won and the board is not full
                if (!board.oWins() && !board.isFull()) {
                    socketOut.println("It is your turn, " + name);
                    //Used to turn off O players buttons
                    opponent.socketOut.println("Waiting for opponent to make a move...");
                    makeMove();
                    board.checkWinner(mark);
                }
                //O-Player turn if X-Player hasn't won and the board is not full
                if (!board.xWins() && !board.isFull()) {
                    opponent.socketOut.println("It is your turn, " + opponent.name);
                    //Used to turn off X players buttons
                    socketOut.println("Waiting for opponent to make a move...");
                    opponent.makeMove();
                    board.checkWinner(mark);
                }
            }
        if (board.xWins()) {
        	socketOut.println("THE GAME IS OVER: " + name + " is the winner!");
        	opponent.socketOut.println("THE GAME IS OVER: " + name + " is the winner!");
        }
        if (board.oWins()) {
        	socketOut.println("THE GAME IS OVER: " + opponent.name + " is the winner!");
            opponent.socketOut.println("THE GAME IS OVER: " + opponent.name + " is the winner!");
        }
        if (!board.oWins() && !board.xWins()) {
            socketOut.println("It is a tie!");
            opponent.socketOut.println("It is a tie!");
        }
        socketOut.println("Game Ended...");
        opponent.socketOut.println("Game Ended...");
    }catch(Exception e) {
    	socketOut.println("Player has left");
    	opponent.socketOut.println("Player has left");
    }
    }

    /**
     * Performs the action of a turn by
     * placing a mark on the board.
     * Synchronized since only one player is allowed to access it
     * @throws IOException
     */
    public void makeMove() throws IOException {
        while (true) {
            //Reads position from socket
            String response = socketIn.readLine();
            //Splits string into row and column
            String[] spot = response.split("\\s+");
            //Checks to see if their is a mark in that position already
            if (board.getMark(Integer.parseInt(spot[0]), Integer.parseInt(spot[1])) != mark &&
                    board.getMark(Integer.parseInt(spot[0]), Integer.parseInt(spot[1])) != opponent.mark) {
                board.addMark(Integer.parseInt(spot[0]), Integer.parseInt(spot[1]), mark);
                opponent.board.addMark(Integer.parseInt(spot[0]), Integer.parseInt(spot[1]), mark);
                socketOut.println("MARKING "+ spot[0]+" "+spot[1]+" "+ mark);
                opponent.socketOut.println("MARKING "+ spot[0]+" "+spot[1]+" "+ mark);
                break;
            } else {
                socketOut.println("That position has already been chosen. " +
                        "Please choose a different position.");
            }
        }
    }


//////////////////////////////////////////////////////////////////////////////////////
////Getters and Setters

    /**
     * Sets the value of opponent to the specified value.
     * @param opponent Value is the new Player object for opponent.
     */
    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    /**
     * Sets the value of board to the specified value.
     * @param board Value is the new Board object for board.
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    public String getName() {
        return name;
    }

}

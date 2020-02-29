import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * A java data type file that contains data fields
 * and methods, representing the game of Tic-Tac-Toe.
 * The overall purpose of this class is to assign players
 * and begin the game.
 * @author Thomas Yee & Shenghan Zhang
 * @version 1.0
 * @since February 12, 2020
 */
public class Game implements Constants, Runnable {
//////////////////////////////////////////////////////////////////////////
////Instance Variables
	/**
	 * The Tic-Tac-Toe board. This is
	 * a Board data type.
	 */
	private Board theBoard;

	/**
	 * The referee that controls the game. This is
	 * a Referee data type.
	 */
	private Referee theRef;
	private Socket aSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private String name;
	private Player xPlayer = null, oPlayer = null;
	private int indexInServer;

////////////////////////////////////////////////////////////////////////////
////Constructors
	/**
	 *Constructs a Game object.
     *Creates a Board object.
	 */
    public Game( ) {
        theBoard  = new Board();
	}

	public Game(Socket aSocket, String name, BufferedReader socketIn, PrintWriter socketOut,int i) {
    	this.aSocket = aSocket;
    	this.name = name;
    	this.socketIn = socketIn;
    	this.socketOut = socketOut;
    	theBoard  = new Board();
    	indexInServer = i;
	}

	/**
	 * Appoints a referee to the game and begins the game.
	 * @param r A Referee data type.
	 * @throws IOException
	 */
    public void appointReferee(Referee r) throws IOException {
        theRef = r;
    	theRef.runTheGame();
    }
    public void run() {
		//Initializes the variables
		setup();
	}
	private void setup() {
		try {
			
			//User input for X-Player			
			if (name.equals("Player 1")) {
				socketOut.println("Message: WELCOME TO THE GAME!!!");
				socketOut.println("This is player X.");
				socketOut.println("Please enter the name of the 'X' player:");
				//User input for X-Player
				name = socketIn.readLine();
				//Checks to see if a name has been provided
				while (name == null) {
					socketOut.println("Please try again: ");
					name = socketIn.readLine();
				}
				//Creates a new Player object for X-Player
				xPlayer = new Player(name, LETTER_X,aSocket);
				xPlayer.setBoard(theBoard);
				//Creates a new Referee object
				theRef = new Referee();
				theRef.setBoard(theBoard); //Sets the board to Referee
				theRef.setxPlayer(xPlayer); //Sets player x to Referee
				
				Game game = Server.users.get(indexInServer + 1);
				if (game.oPlayer != null ) {
					theRef.setoPlayer(game.getoPlayer()); //Sets o player for thread 1
					this.appointReferee(theRef); // Start game
				}
			}
			
			//User input for O-Player
			if (name.equals("Player 2")) {
				socketOut.println("Message: WELCOME TO THE GAME!!!");
				socketOut.println("This is player O.");
				socketOut.println("Please enter the name of the 'O' player:");
				name = socketIn.readLine();
				Game game1 = Server.users.get(indexInServer-1);
				//Checks to see if a name has been provided
				while (name == null) {
					System.out.print("Please try again: ");
					name = socketIn.readLine();
				}

				//Creates a new Player object for O-Player
				oPlayer = new Player(name, LETTER_O,aSocket);
				oPlayer.setBoard(game1.getTheBoard());
				if (game1.xPlayer != null ) {
					theRef = game1.theRef;//Sets ref for thread 2
					theRef.setxPlayer(game1.getxPlayer());//Sets x player for thread 2
					theRef.setoPlayer(oPlayer); //Sets o player for thread 2
					theRef.setBoard(game1.getTheBoard()); //Sets the board for thread 2
					game1.theRef.setoPlayer(oPlayer);//Sets o player for thread 1
					this.appointReferee(theRef); // Start game
				}
			}
		}catch (IOException e){
				e.printStackTrace();
			}
	}

	public Player getxPlayer() {
		return xPlayer;
	}

	public void setxPlayer(Player xPlayer) {
		this.xPlayer = xPlayer;
	}

	public Player getoPlayer() {
		return oPlayer;
	}

	public void setoPlayer(Player oPlayer) {
		this.oPlayer = oPlayer;
	}

	public void setaSocket(Socket aSocket) {
		this.aSocket = aSocket;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Board getTheBoard() {
		return theBoard;
	}
}

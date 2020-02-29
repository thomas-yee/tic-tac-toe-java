import java.io.IOException;

/**
 * A java data type that has data fields and methods,
 * representing a referee for the game.
 * Assigns the opponents of X-Player and O-Player. Also
 * displays the board and runs the game.
 * @author Thomas Yee & Shenghan Zhang
 * @version 1.0
 * @since February 12, 2020
 */
public class Referee {
//////////////////////////////////////////////////////////////////////////
////Instance Variables
    /**
     * The X-Player. A Player data type.
     */
    private Player xPlayer;

    /**
     * The O-Player. A Player data type.
     */
    private Player oPlayer;

    /**
     * The Tic-Tac-Toe board. A Board data type.
     */
    private Board board;

///////////////////////////////////////////////////////////////////////////////
////Constructors

    /**
     * Constructs a Referee object
     */
    public Referee() {
    }

///////////////////////////////////////////////////////////////////////////////
////Instance Methods

    /**
     * Sets the opponent of X-Player and O-Player. Displays the board
     * and plays the game.
     * @throws IOException
     */
    public void runTheGame() throws IOException {
        xPlayer.setOpponent(oPlayer);
        oPlayer.setOpponent(xPlayer);
        xPlayer.play();
    }
///////////////////////////////////////////////////////////////////////////////
////Getters and Setters

    /**
     * Sets the value of board with the specified value.
     * @param board Value is the new Board for board.
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Sets the value of Player with the specified value.
     * @param xPlayer Value is the new Player for X-Player.
     */
    public void setxPlayer(Player xPlayer) {
        this.xPlayer = xPlayer;
    }

    /**
     * Sets the value of Player with the specified value.
     * @param oPlayer Value is the new Player for O-Player
     */
    public void setoPlayer(Player oPlayer) {
        this.oPlayer = oPlayer;
    }


}

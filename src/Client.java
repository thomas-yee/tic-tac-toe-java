/**
 * This class acts as the client and the GUI. Receives messages from the server
 * and responds accordingly.
 * @author Thomas Yee & Shenghan Zhang
 * @version 1.0
 * @since February 12, 2020
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends JFrame {
    private PrintWriter socketOut;
    private Socket palinSocket;
    private BufferedReader stdIn;
    private BufferedReader socketIn;
    ////////////////////////////////////////////////////////////////////////
////Instance Variables
    /**
     * Container for the game details. JTextArea data type.
     */
    private JTextArea box;
    /**
     * Container for our buttons. JPanel data type.
     */
    private JPanel buttonsPanel, inputPanel, game;
    /**
     * Scroll bar for the text area. JScrollPane data type.
     */
    private JScrollPane scroll;
    /**
     * Container for the labels of the players. JLabel data type.
     */
    private JLabel labelXType, labelOType, labelXUser, labelOUser;
    /**
     * Text fields for the user input text boxes. JTextField data type.
     */
    private JTextField nameXType, nameOType, userXInput,userOInput;
    /**
     * The array of buttons that act as the board. JButton[][] data type.
     */
    private JButton [][] buttons;
    /**
     * Buttons to save player name
     */
    private JButton save, save1;

///////////////////////////////////////////////////////////////////////
////Constructor
    public Client(String serverName, int portNumber, String s) {
        super(s);
        //Get access to the container object
        Container c = getContentPane();
        //set the size of the GUI
        setSize(600, 400);
        setLayout(new BorderLayout());
        //Stops the program when GUI closes
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Creates the text area with a titled border
        box = new JTextArea("");
        box.setEditable(false);
        box.setBorder(BorderFactory.createTitledBorder("Message Window:"));

        //Create a new panel with a grid layout and place it in the center
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2));
        c.add("Center", buttonsPanel);

        //Create game screen
        game = new JPanel(new GridLayout(3, 3));
        buttonsPanel.add(game);

        //Create the buttons on the GUI and add to the GUI
        buttons = new JButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton(" ");
                buttons[i][j].setEnabled(false);
                game.add(buttons[i][j]);
            }
        }
        //Creates the scroll bar and sets the text box on the side
        scroll = new JScrollPane(box);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        buttonsPanel.add(scroll);

        //Create a new panel with a grid layout and place it in the south
        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 5));
        c.add("South", inputPanel);

        //Creates a label for the X player symbol
        labelXType = new JLabel();
        labelXType.setText("Player Symbol:");
        inputPanel.add(labelXType);

        //Displays the marker for X player
        nameXType = new JTextField("X");
        nameXType.setEditable(false);
        inputPanel.add(nameXType);

        //Creates label for X player name
        labelXUser = new JLabel();
        labelXUser.setText("Player X Name:");
        inputPanel.add(labelXUser);

        //Field to get the name of the X player
        userXInput = new JTextField();
        inputPanel.add(userXInput);

        //Add button to save X player
        save = new JButton("Save");
        inputPanel.add(save);

        //Creates a label for the O player symbol
        labelOType = new JLabel();
        labelOType.setText("Player Symbol:");
        inputPanel.add(labelOType);

        //Displays the marker for O player
        nameOType = new JTextField("O");
        nameOType.setEditable(false);
        inputPanel.add(nameOType);

        //Creates label for O player name
        labelOUser = new JLabel();
        labelOUser.setText("Player O Name:");
        inputPanel.add(labelOUser);

        //Field to get the name of the O player
        userOInput = new JTextField();
        inputPanel.add(userOInput);

        //Add button to save O player
        save1 = new JButton("Save");
        inputPanel.add(save1);

        //Registers an ActionListener to each button on the board
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int finalJ = j;
                int finalI = i;
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                            //User clicks position and it is sent through socket
                            socketOut.println(finalI +" "+ finalJ);
                            //Stops user from pressing the same button
                            buttons[finalI][finalJ].setEnabled(false);
                    }
                });
            }
        }

        //Registers an ActionListener to the player X button
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!userXInput.getText().trim().isEmpty()) {
                    userXInput.setEditable(false);
                    socketOut.println(userXInput.getText());
                }
                else {
                    box.append("\nPlease type a name for Player X\n");
                }
            }
        });

        //Registers an ActionListener to the player O button
        save1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!userOInput.getText().trim().isEmpty()) {
                    userOInput.setEditable(false);
                    socketOut.println(userOInput.getText());
                }
                else {
                    box.append("Please type a name for Player O\n");
                }
            }
        });
        try {
            palinSocket = new Socket(serverName, portNumber);
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            socketIn = new BufferedReader(new InputStreamReader(palinSocket.getInputStream()));
            socketOut = new PrintWriter(palinSocket.getOutputStream(), true);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
////////////////////////////////////////////////////////////////////
////Instance Methods
    private void turnOffButtons() {
        //Turn off all buttons for player
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }
    private void turnOnButtons(){
    	//Turn on buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
            	if(buttons[i][j].getText().equals(" ")) {
            		buttons[i][j].setEnabled(true);
                }
            }
        }
    }
    
    public void communicate() {
        String line = "abc";
        String response = "";

        while (!line.equals("QUIT")) {
            try {
                //Read response from socket
                response = socketIn.readLine();

                if (response.equals("This is player X.")) {
                    userOInput.setEditable(false);
                }
                if (response.equals("This is player O.")) {
                    userXInput.setEditable(false);
                }
                //Sets name for Player's X textbox
                if (response.startsWith("PlayerX")) {
                    String[] name = response.split("\\s+");
                    userXInput.setText(name[1]);
                }

                //Sets name for Player's O textbox
                if (response.startsWith("PlayerO")) {
                    String[] name = response.split("\\s+");
                    userOInput.setText(name[1]);
                }
                //Adding one client's move to another client
                if (response.startsWith("MARKING")) {
                	String[] coordinate = response.split("\\s+");
                	buttons[Integer.parseInt(coordinate[1])][Integer.parseInt(coordinate[2])].setText(coordinate[3]);
                }
                //Turn off buttons here
                if (response.startsWith("Waiting for")) {
                   turnOffButtons();
                }
                //Turn on buttons here
                if (response.startsWith("It is your turn")) {
                    turnOnButtons();
                 }

                if (response.startsWith("THE GAME IS OVER")) {
                    box.append(response);
                    turnOffButtons();
                    break;
                }
                if (response.equals("It is a tie!")) {
                    box.append(response);
                    turnOffButtons();
                    break;
                }
                // If the other player has left
                if (response.equals("Player has left")) {
                    box.append("The other player has left.");
                    break;
                }
                else {
                    //Prints responses to the textbox
                    final String finalResponse = response + "\n";
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                        	if(!finalResponse.startsWith("MARKING") && !finalResponse.startsWith("PlayerX ")
                                    && !finalResponse.startsWith("PlayerO ")) {
                                box.append(finalResponse);
                            }
                        }
                    });
                     //causes it to crash
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            stdIn.close();
            socketIn.close();
            socketOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client aClient = new Client ("localhost", 9898, "Tic-Tac-Toe");
        //This is for AWS
        //Client aClient = new Client("3.135.18.180", 9898, "Tic-Tac-Toe");
        aClient.setVisible(true);
        aClient.communicate();
    }
}

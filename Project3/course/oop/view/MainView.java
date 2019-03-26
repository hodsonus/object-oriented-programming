package course.oop.view;

import java.io.File;

import course.oop.controller.InvalidAIOperation;
import course.oop.controller.InvalidPersonOperation;
import course.oop.controller.TTTControllerImpl;
import course.oop.other.Person;
import course.oop.other.Player;
import course.oop.other.ExistingPlayers;
import course.oop.other.exceptions.InvalidMarkerException;
import course.oop.other.exceptions.TurnTimeoutException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;

public class MainView {
	//view attributes
	private BorderPane root;
	private Scene scene; 
    private final int windowWidth = 700;
    private final int windowHeight = 700;
    
    //basic control attributes (mirrors TTTDriver from Project2)
    private String player1Username;
    private String player2Username;
    private boolean player1Turn;
    private TTTControllerImpl ticTacToe;
    private ExistingPlayers exP;
	
    //public constructor
	public MainView() {
		this.root = new BorderPane();
		this.scene = new Scene(root, windowWidth, windowHeight);
		this.root.setTop(buildIntro());
		this.root.setCenter(buildSetupPane());
		this.ticTacToe = new TTTControllerImpl();
		this.player1Turn = true;
		this.exP = ExistingPlayers.getInstance();
	}
	
	public Scene getMainScene() {
		return this.scene;
	}
	
	//basic formatting for our grid pane
	private GridPane formatGridPane(GridPane gridPane, Pos pos) {
        gridPane.setMinSize(windowWidth/2, windowHeight/2); 
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        gridPane.setVgap(10);
        gridPane.setHgap(10);       
        gridPane.setAlignment(pos); 
        return gridPane;
	}
		
	private GridPane buildIntro() {
		Text title = new Text("BASIC TIC-TAC-TOE");
		GridPane topBar = new GridPane();
		//TODO, make a pretty header
		title.setStyle(
					  "-fx-font-size: 50px;"
//				    + "-fx-font-color: #CD6155;"
//					+ "-fx-font-family: Notoadasdfasdss;"
//					+ "-fx-text-shadow: -1px -1px 0 #000, 1px -1px 0 #000,-1px 1px 0 #000, 1px 1px 0 #000;"
					+ "-fx-font-weight: bold;");
//					+ "-fx-font-variant: small-caps;");
		
		topBar.add(title, 0, 0);
		topBar.setAlignment(Pos.CENTER);
		topBar.setPadding(new Insets(10, 10, 10, 10)); 
	
		return topBar;
	}
	
	private GridPane buildSetupPane() {
        Button startButton = new Button("Start!");
        
        //handler for start button, moves us to the input of user attribures
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
           @Override 
           public void handle(MouseEvent e) { 
               buildPlayerObjects(new Text("Please input player attributes."));
           } 
        };  
        startButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);   

        //main GridPane to start the game, consists of the start button only
        GridPane gridPane = new GridPane();      
        gridPane.add(startButton, 0, 2); 
        formatGridPane(gridPane, Pos.CENTER);
        gridPane.setVgap(50);
              
        return gridPane;
	}
	
	private void buildPlayerObjects(Text topLabel) {
	
		//format and set the top label
		GridPane textPane = new GridPane();
		textPane.add(topLabel, 0, 0);
		textPane.setAlignment(Pos.CENTER);
		topLabel.setTextAlignment(TextAlignment.CENTER);
		textPane.setPadding(new Insets(10, 10, 10, 10)); 
		root.setTop(textPane);
		
		//instantiate playerScreen, the node to be placed at center holding the forms for the player objects
		GridPane playerScreen = new GridPane();
		
		//make player labels
		Text player1Label = new Text("Player 1");
		player1Label.setUnderline(true);
		
		Text player2Label = new Text("Player 2");
		player2Label.setUnderline(true);
				
		//build checkbox and text for the use existing player checkboxes
		GridPane doesPlayer1Exist = new GridPane();
			doesPlayer1Exist.add(new Text("Use an existing player?"), 0, 0);
			CheckBox doesPlayer1ExistCheckbox = new CheckBox();
			doesPlayer1Exist.add(doesPlayer1ExistCheckbox, 1, 0);
			doesPlayer1Exist.setHgap(5);
		GridPane doesPlayer2Exist = new GridPane();
			doesPlayer2Exist.add(new Text("Use an existing player?"), 0, 0);
			CheckBox doesPlayer2ExistCheckbox = new CheckBox();
			doesPlayer2Exist.add(doesPlayer2ExistCheckbox, 1, 0);
			doesPlayer2Exist.setHgap(5);
		
		//GridPane to show if player1 is going to be a brand new player
		GridPane p1DoesntExist = new GridPane();
			Text player1NameLabel = new Text("Username:");
			TextField player1NameTextField = new TextField();
			Text player1MarkerLabel = new Text("Marker:");
			TextField player1MarkerField = new TextField();
			GridPane isPlayer1AI = new GridPane();
			isPlayer1AI.add(new Text("Is an AI?"), 0, 0);
			CheckBox isPlayer1AICheckbox = new CheckBox();
			isPlayer1AI.add(isPlayer1AICheckbox, 1, 0);
			isPlayer1AI.setHgap(5);
			p1DoesntExist.add(isPlayer1AI,          0, 2);
			p1DoesntExist.add(player1NameLabel,     0, 4);
			p1DoesntExist.add(player1NameTextField, 0, 5);
			p1DoesntExist.add(player1MarkerLabel,   0, 6);
			p1DoesntExist.add(player1MarkerField,   0, 7);
		//GridPane to show if player1 is going to be an existing player
		GridPane p1Exists = new GridPane();
			ListView<String> existingOptionsP1 = new ListView<String>();
			populateListView(existingOptionsP1);
			p1Exists.add(existingOptionsP1, 0, 0);
		//by default, have users input new player
		p1Exists.setVisible(false);		
	
		//similar to above, instantiate GridPane to show to users when they want to create a brand new player
		GridPane p2DoesntExist = new GridPane();
			Text player2NameLabel = new Text("Username:");
			TextField player2NameTextField = new TextField();
			Text player2MarkerLabel = new Text("Marker:");
			TextField player2MarkerField = new TextField();
			GridPane isPlayer2AI = new GridPane();
			isPlayer2AI.add(new Text("Is an AI?"), 0, 0);
			CheckBox isPlayer2AICheckbox = new CheckBox();
			isPlayer2AI.add(isPlayer2AICheckbox, 1, 0);
			isPlayer2AI.setHgap(5);
			p2DoesntExist.add(isPlayer2AI,          2, 2);
			p2DoesntExist.add(player2NameLabel,     2, 4);
			p2DoesntExist.add(player2NameTextField, 2, 5);
			p2DoesntExist.add(player2MarkerLabel,   2, 6);
			p2DoesntExist.add(player2MarkerField,   2, 7);
		//similar to above, instantiate GridPane to show to users when they want to retrieve an existing player
		GridPane p2Exists = new GridPane();
			ListView<String> existingOptionsP2 = new ListView<String>();
			populateListView(existingOptionsP2);
			p2Exists.add(existingOptionsP2, 0, 0);
		p2Exists.setVisible(false);
		
		//playerScreen is the top level node in the center
		//add the p1 stuff to the playerScreen
		playerScreen.add(player1Label,      0, 0);	
		playerScreen.add(doesPlayer1Exist,  0, 1);
		playerScreen.add(p1Exists,          0, 2);
		playerScreen.add(p1DoesntExist,     0, 2);
		//add the p2 stuff to the playerScreen
		playerScreen.add(player2Label,      2, 0);
		playerScreen.add(doesPlayer2Exist,  2, 1);
		playerScreen.add(p2Exists,          2, 2);
		playerScreen.add(p2DoesntExist,     2,2);

		//add the submit button and label
		playerScreen.add(new Text(""),         0, 8);
			Button submitButton = new Button("Submit Players");
		playerScreen.add(submitButton,         0, 9);
		
		//handlers for the checkboxes that switch between instantiating a brand new player and retrieving an existing player
        EventHandler<MouseEvent> p1ExistsHandler = new EventHandler<MouseEvent>() { 
            @Override 
            public void handle(MouseEvent e) { 
            	p1Exists.setVisible(!p1Exists.isVisible());
            	p1DoesntExist.setVisible(!p1DoesntExist.isVisible());
            } 
         };
         doesPlayer1ExistCheckbox.addEventFilter(MouseEvent.MOUSE_CLICKED, p1ExistsHandler);
        EventHandler<MouseEvent> p2ExistsHandler = new EventHandler<MouseEvent>() { 
            @Override 
            public void handle(MouseEvent e) { 
            	p2Exists.setVisible(!p2Exists.isVisible());
            	p2DoesntExist.setVisible(!p2DoesntExist.isVisible());
            } 
         };
         doesPlayer2ExistCheckbox.addEventFilter(MouseEvent.MOUSE_CLICKED, p2ExistsHandler);

		//handler for the submission box
         //TODO, extract logic into two distinct parts, retrieval of existing objects and instantiation of old objects
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
           @Override 
           public void handle(MouseEvent e) { 
        	   
        	   // isInvalid if the name field is empty or the marker field is empty AND we are creating a new player
        	   // isInvalid if we are choosing an existing character and no player is selected
        	   boolean player1SelectionIsInvalid = (("".equals(player1NameTextField.getText()) || "".equals(player1MarkerField.getText())) && !doesPlayer1ExistCheckbox.isSelected()) || (doesPlayer1ExistCheckbox.isSelected() && existingOptionsP2.getSelectionModel().getSelectedItem() == null);
        	   boolean player2SelectionIsInvalid = (  ("".equals(player2NameTextField.getText()) || "".equals(player2MarkerField.getText())) && !doesPlayer2ExistCheckbox.isSelected() || (doesPlayer2ExistCheckbox.isSelected() && existingOptionsP2.getSelectionModel().getSelectedItem() == null));
        	   
        	   if ( player1SelectionIsInvalid || player2SelectionIsInvalid) {
        		   buildPlayerObjects(new Text("Invalid Input, please specify text to all fields."));
        		   return;
        	   }
        	   
        	   try {
        		   System.out.println("craeting objects...");
	        	   if (isPlayer1AICheckbox.isSelected()) {
						ticTacToe.createAI(player1NameTextField.getText(), player1MarkerField.getText(), 1);
	        	   } else {
						ticTacToe.createPlayer(player1NameTextField.getText(), player1MarkerField.getText(), 1);
	        	   }
	        	   
	        	   if (isPlayer2AICheckbox.isSelected()) {
						ticTacToe.createAI(player2NameTextField.getText(), player2MarkerField.getText(), 2);
		       	   } else {
						ticTacToe.createPlayer(player2NameTextField.getText(), player2MarkerField.getText(), 2);
		       	   }
        	   }
        	   catch (InvalidMarkerException ime) {
        		   buildPlayerObjects(new Text("Invalid marker supplied, please input a valid marker."));
        		   return;
        	   }
        	   
        	   player1Username = player1NameTextField.getText();
        	   player2Username = player2NameTextField.getText();
        	   
               startNewGame(new Text("Please input as to whether you want a timer or not."));
           } 
        };  
        submitButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);  
        
		root.setCenter(formatGridPane(playerScreen, Pos.CENTER));
	}
	
	private void populateListView(ListView<String> existingOptions) {
		ObservableList<String> players = exP.getRepresentations();
		existingOptions.setItems(players);
	}

	//instantiate the game and the timer if desired
	private void startNewGame(Text topLabel) {
		//put the top label on the top with the instructions that were passed in
		GridPane textPane = new GridPane();
		textPane.add(topLabel, 0, 0);
		textPane.setAlignment(Pos.CENTER);
		topLabel.setTextAlignment(TextAlignment.CENTER);
		root.setTop(textPane);
		textPane.setPadding(new Insets(10, 10, 10, 10)); 
		
		//instantiate the main center GridPane
		GridPane timerScreen = new GridPane();
		
		//timer label and field
		Text timerLengthLabel = new Text("Timer length:");
		TextField timerLengthField = new TextField();
		Button submitButton = new Button("Submit");
		
		//user timer control box is selected by default
		Text useTimerLabel = new Text("Use timer?");
		CheckBox useTimerField = new CheckBox();
		useTimerField.setSelected(true);
		
		//add nodes to the timerScreen GridPane
		timerScreen.add(useTimerLabel,    0, 0);
		timerScreen.add(useTimerField,    1, 0);
		timerScreen.add(timerLengthLabel, 0, 1);
		timerScreen.add(timerLengthField, 1, 1);
		timerScreen.add(submitButton,     0, 2);

		//handler that handles the submission of this page
        EventHandler<MouseEvent> submitHandler = new EventHandler<MouseEvent>() { 
           @Override 
           public void handle(MouseEvent e) { 
        	   int timerLength = -1;
        	   if (useTimerField.isSelected()) {
        		   try {
        			   timerLength = Integer.parseInt( timerLengthField.getText() );
        		   }
        		   catch (NumberFormatException nfe) {
        			   startNewGame(new Text("Input to timer not a valid integer."));
        			   return;
        		   }
        		   if (timerLength <= 0) {
        			   startNewGame(new Text("Input to timer must be greater than 0."));
        			   return;
        		   }
        	   }
        	   System.out.println("Game has been started.");
        	   ticTacToe.startNewGame(2, timerLength);
        	   updateView();
        	   addTopButtons();
        	   return;
           } 
        };  
        submitButton.addEventFilter(MouseEvent.MOUSE_CLICKED, submitHandler);
        
        //handler that deals with the toggling of the timer form
        EventHandler<MouseEvent> timerHandler = new EventHandler<MouseEvent>() { 
            @Override 
            public void handle(MouseEvent e) { 
         	   if (!useTimerField.isSelected()) {
         		  timerLengthField.setVisible(false);
         		  timerLengthLabel.setVisible(false);
         	   }
         	   else {
         		  timerLengthField.setVisible(true);
         		  timerLengthLabel.setVisible(true);
         	   }
            } 
         };
        useTimerField.addEventFilter(MouseEvent.MOUSE_CLICKED, timerHandler);
        
        //set the root and be done
		root.setCenter(formatGridPane(timerScreen, Pos.CENTER));
	}
	
	//called many times throughout the program, this resets the buttons and clears the root's top of text
	private void addTopButtons() {
		
		// reset/quit buttons and their appropriate handlers
		Button resetButton = new Button("Reset");
        EventHandler<MouseEvent> resetEventHandler = new EventHandler<MouseEvent>() { 
           @Override 
           public void handle(MouseEvent e) { 
        	   player1Turn = true;
        	   ticTacToe.resetGame();
        	   addTopButtons();
        	   updateView();
        	   System.out.println("Game has been reset.");
        	   return;
           } 
        };  
        resetButton.addEventFilter(MouseEvent.MOUSE_CLICKED, resetEventHandler);
  		Button quitButton = new Button("Quit");
          EventHandler<MouseEvent> quitEventHandler = new EventHandler<MouseEvent>() { 
             @Override 
             public void handle(MouseEvent e) { 
            	 System.out.println("Game has been quit.");
            	 exP.savePlayers();
            	 System.exit(0);
            	 return;
             }
          };  
        quitButton.addEventFilter(MouseEvent.MOUSE_CLICKED, quitEventHandler);
        
        //label for viewing the current players turn
        //TODO, make it prettier
        GridPane playerXTurn = new GridPane();
        playerXTurn.setAlignment(Pos.CENTER_RIGHT);
        playerXTurn.setPadding(new Insets(10, 10, 10, 10));
        String currentPlayer = player1Turn ? player1Username : player2Username;
        playerXTurn.add(new Text(currentPlayer + "'s turn"), 0, 0);    
        
        //setup the controlButtons node (GridPane that just contains the two buttons and their labels)
  		GridPane controlButtons = new GridPane();
  		controlButtons.add(new Text(""), 0, 1);
  		controlButtons.add(quitButton, 1, 1);
  		controlButtons.add(new Text(""),  2, 1);
  		controlButtons.add(resetButton,  3, 1);
  		controlButtons.setVgap(20);
  		controlButtons.setHgap(1);
  		controlButtons.setAlignment(Pos.CENTER_LEFT);
  		controlButtons.setPadding(new Insets(10, 10, 10, 10));
  		  		
  		//the topBar consists of the current player's turn label and the controlbuttons
  		GridPane topBar = new GridPane();
  		topBar.add(controlButtons, 0,0);
  		topBar.add(playerXTurn,    1,0);
  		
  		//set the top
  		root.setTop(topBar);
	}
	
	//refresh the view
	private void updateView() {
		//make a call to the game board to retrieve it's display
		//this trickles all the way down, from MainView -> BasicTicTacToe -> BottomBoard -> Square
		GridPane display = ticTacToe.getGUIDisplay();
		        
		//align it in the center
		display.setAlignment(Pos.CENTER); 
        root.setCenter(display);
        
        //setup buttons on the bottom
        setupUserMoveInputScreen();
	}
	
	//this method is responsible for taking in user input and updating the board object
	private void setupUserMoveInputScreen() {		
		//instantiate out primary grid
		GridPane userInputScreen = new GridPane();
		
		//setup user input fields
		Text rowInputLabel = new Text("Desired Row:");
		TextField rowInputField = new TextField();
		Text colInputLabel = new Text("Desired Col:");
		TextField colInputField = new TextField();
		Button submitUserInputButton = new Button("Submit User Move");
		
		userInputScreen.setVgap(5);
		userInputScreen.setHgap(8);
		
		userInputScreen.add(rowInputLabel,         0, 2);
		userInputScreen.add(rowInputField,         1, 2);
		userInputScreen.add(colInputLabel,         0, 3);
		userInputScreen.add(colInputField,         1, 3);
		userInputScreen.add(submitUserInputButton, 0, 4);
		
		//TODO, refactor into clicking on the individual squares?
		//event handler for the submission of the user input
        EventHandler<MouseEvent> submitEventHandler = new EventHandler<MouseEvent>() { 
        	@Override 
        	public void handle(MouseEvent e) { 
        		int row;
        		int col;
        		//try to parse them, and if they arent valid, then dont do anything
        		try {
        			row = Integer.parseInt(rowInputField.getText());
        			col = Integer.parseInt(colInputField.getText());
        		}
        		catch (NumberFormatException nfe) {
        			//TODO print a message about invalid input to the locator selector
        			return;
        		}
        		//if the numbers were valid, make a call to the board and print to our logging terminal
        		supplyMove(row,col);
        		System.out.println("User supplied a move. Row: " + row + ". Col: " + col);
        		
        		/* checkWinner() */
        		int winner = ticTacToe.determineWinner();
        		String resultString;
        		if (winner == 1) {
        			resultString = player1Username + " won the game!!";
        			System.out.println(resultString);
        			endGame(new Text(resultString));
        			return;
        		}
        		else if (winner == 2) {
        			resultString = player2Username + " won the game!!";
        			System.out.println(resultString);
        			endGame(new Text(resultString));
        			return;
        		} 
        		else if (winner == 3) {
        			resultString = player1Username + " and " + player2Username + "'s game resulted in a draw!!";
        			System.out.println(resultString);
        			endGame(new Text(resultString));
        			return;
        		}
        		/* */
     
        		//update the view after a successful move
        		updateView();
        		//write the start time for the next move
                ticTacToe.writeStartTime();
        		return;
           } 
        };
        submitUserInputButton.addEventFilter(MouseEvent.MOUSE_CLICKED, submitEventHandler);
        
        //handler for submitting an AI move to the board
		Button requestAIButton = new Button("Request AI Move");
		userInputScreen.add(requestAIButton, 0, 0);
        EventHandler<MouseEvent> requestAIHandler = new EventHandler<MouseEvent>() { 
        	@Override 
        	public void handle(MouseEvent e) { 
        		requestAIMove();
        		/* checkWinner() */
        		int winner = ticTacToe.determineWinner();
        		String resultString;
        		if (winner == 1) {
        			resultString = player1Username + " won the game!!";
        			System.out.println(resultString);
        			endGame(new Text(resultString));
        			return;
        		}
        		else if (winner == 2) {
        			resultString = player2Username + " won the game!!";
        			System.out.println(resultString);
        			endGame(new Text(resultString));
        			return;
        		} 
        		else if (winner == 3) {
        			resultString = player1Username + " and " + player2Username + "'s game resulted in a draw!!";
        			System.out.println(resultString);
        			endGame(new Text(resultString));
        			return;
        		}
        		/* */
        		
        		//update the view after a successful move
        		updateView();
        		//write the start time for the next move
                ticTacToe.writeStartTime();
        		System.out.println("AI move requested.");
        		return;
           } 
        };  
        requestAIButton.addEventFilter(MouseEvent.MOUSE_CLICKED, requestAIHandler);
                
        //set some padding and then put it on the bottom
        userInputScreen.setPadding(new Insets(10, 10, 10, 10));
		root.setBottom(userInputScreen);
		
		//write the start time for the next move
        ticTacToe.writeStartTime();
	}
	
	private void endGame(Text topLabel) {
   	 	exP.savePlayers();
		
		//put the topLabel on root's top with the appropriate end game message
		GridPane textPane = new GridPane();
		textPane.add(topLabel, 0, 0);
		textPane.setAlignment(Pos.CENTER);
		topLabel.setTextAlignment(TextAlignment.CENTER);
		root.setTop(textPane);
		textPane.setPadding(new Insets(10, 10, 10, 10)); 
 
		//instantiate buttons to choose your next action
		Button quitButton = new Button("Quit");
		Button playAgainButton = new Button("Play Again");
		Button choosePlayersButton = new Button("Choose new players");
		
		//handlers for quitting the game, playing the game again, and choosing new players
		EventHandler<MouseEvent> quitHandler = new EventHandler<MouseEvent>() { 
        	@Override 
        	public void handle(MouseEvent e) { 
        		System.out.println("Game has been quit.");
           	 	exP.savePlayers();
        		System.exit(0);
           } 
        };  
        quitButton.addEventFilter(MouseEvent.MOUSE_CLICKED, quitHandler);
        EventHandler<MouseEvent> playAgainHandler = new EventHandler<MouseEvent>() { 
        	@Override 
        	public void handle(MouseEvent e) { 
        		System.out.println("Playing game again with same players.");
         	   	player1Turn = true;
        		ticTacToe.resetGame();
         	   	updateView();
         	   	addTopButtons();
         	   	ticTacToe.writeStartTime();
         	   	System.out.println("Game has been reset.");
           } 
        };  
        playAgainButton.addEventFilter(MouseEvent.MOUSE_CLICKED, playAgainHandler);
        EventHandler<MouseEvent> chooseAgainHandler = new EventHandler<MouseEvent>() { 
        	@Override 
        	public void handle(MouseEvent e) { 
        		System.out.println("User indicated to choose players again.");
        		buildPlayerObjects(new Text("Please input player attributes."));
           } 
        };  
        choosePlayersButton.addEventFilter(MouseEvent.MOUSE_CLICKED, chooseAgainHandler);
		
        //instantiate the GridPane and set it up
        GridPane buttonPane = new GridPane();
        buttonPane.add(quitButton, 0, 0);
        buttonPane.add(playAgainButton, 1, 0);
        buttonPane.add(choosePlayersButton, 2, 0);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setHgap(5);
        buttonPane.setPadding(new Insets(10, 10, 10, 10));
        
        //update the view for the user to view the end game state
        updateView();
        
        //play the end game sound
	    File clip = new File("cheering.aiff");
	    AudioClip victorySound = new AudioClip(clip.toURI().toString());
	    victorySound.play();
        
	    //display the buttons
		root.setBottom(buttonPane);
	}
	
	private void supplyMove(int row, int col) {
		boolean valid;
		//attempt a user move, using the current user
		try {
			if (player1Turn) {
				valid = ticTacToe.setSelection(row, col, 1);
			}
			else {
				valid = ticTacToe.setSelection(row, col, 2);
			}
		}
		//handle invalid player attempting move
		catch (InvalidAIOperation iaio) {
			addTopButtons();
			GridPane y = ((GridPane)root.getTop());
			y.add(new Text("Please supply an AI's move, current player is not an AI."), 0, 1);
			return;
		}
		//handle timeouts and print errors
		catch (TurnTimeoutException tte) {
			player1Turn = !player1Turn;
			addTopButtons();
			GridPane y = ((GridPane)root.getTop());
			y.add(new Text("User took too long to provide input. Forfeiting turn..."), 0, 1);
			return;
		}
		//if it was valid, indicate so
		if (valid) {
			GridPane y = ((GridPane)root.getTop());
			y.add(new Text("Successful placement!"), 0, 1);
		}
		//if it was invalid, indicate so
		else {
			GridPane y = ((GridPane)root.getTop());
			y.add(new Text("Failure to place move on grid."), 0, 1);
		}
		//switch turns and then clear the messages
		player1Turn = !player1Turn;
		addTopButtons();
	}
	
	private void requestAIMove() {
		//similar to above, attempt a move as an AI with the current player
		try {
			if (player1Turn) {
				ticTacToe.makeAISelection(1);
			}
			else {
				ticTacToe.makeAISelection(2);
			}
		}
		//catch invalid users attempting AI moves
		catch (InvalidPersonOperation ipo) {
			addTopButtons();
			GridPane y = ((GridPane)root.getTop());
			y.add(new Text("Please supply a user's move, current player is not an AI."), 0, 1);
			return;
		}
		//catch timeouts and skip their turn
		catch (TurnTimeoutException tte) {
			player1Turn = !player1Turn;
			addTopButtons();
			GridPane y = ((GridPane)root.getTop());
			y.add(new Text("User took too long to provide input. Forfeiting turn..."), 0, 1);
			return;
		}
		
		//switch turns
		player1Turn = !player1Turn;

		//print message to topPane (AI moves are always successful if there are available spaces)
		addTopButtons();
		GridPane y = ((GridPane)root.getTop());
		y.add(new Text("Successful placement!"), 0, 1);
	}
}
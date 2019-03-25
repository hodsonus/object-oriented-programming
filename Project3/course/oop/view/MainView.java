package course.oop.view;

import course.oop.controller.InvalidAIOperation;
import course.oop.controller.InvalidPersonOperation;
import course.oop.controller.TTTControllerImpl;
import course.oop.other.exceptions.InvalidMarkerException;
import course.oop.other.exceptions.TurnTimeoutException;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.CheckBox;

public class MainView {
	private BorderPane root;
	private Scene scene; 
    private final int windowWidth = 800;
    private final int windowHeight = 600;
    private String player1Username;
    private String player2Username;
    private boolean player1Turn;
    
    private TTTControllerImpl ticTacToe;
	
	public MainView() {
		this.root = new BorderPane();
		this.scene = new Scene(root, windowWidth, windowHeight);
		this.root.setTop(this.buildSetupPane());
		this.ticTacToe = new TTTControllerImpl();
		this.player1Turn = true;
	}
	
	public Scene getMainScene() {
		return this.scene;
	}
	
	/**
	 * The setup pane is where a user can give input
	 * for the initialization of the 2D array.
	 * 
	 * @return
	 */
	private GridPane buildSetupPane() {
		
		Text intro = new Text("Welcome to:");
		Text title = new Text("TIC TAC TOE");
        Button startButton = new Button("Start!");
        
//        Line line = new Line();
//        line.setStartX(0.0f); 
//        line.setStartY(0.0f);         
//        line.setEndX((float) windowWidth); 
//        line.setEndY(0.0f);
        
        //Creating the mouse event handler 
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
           @Override 
           public void handle(MouseEvent e) { 
               buildPlayerObjects(new Text("Please input player attributes."));
           } 
        };  
        
        //Registering the event filter 
        startButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);   

        //Creating a Grid Pane 
        GridPane gridPane = new GridPane();    
               
        gridPane.add(intro, 0, 0);
        gridPane.add(title, 0, 1);
        gridPane.add(startButton, 0, 2); 
        //gridPane.add(line, 0, 2, 3, 1); 
        formatGridPane(gridPane, Pos.CENTER);
              
        return gridPane;
	}
	
	private void buildPlayerObjects(Text topLabel) {
		//TODO
		GridPane textPane = new GridPane();
		textPane.add(topLabel, 0, 0);
		textPane.setAlignment(Pos.CENTER);
		topLabel.setTextAlignment(TextAlignment.CENTER);
		root.setTop(textPane);

		
		GridPane playerScreen = new GridPane();

		Text player1Label = new Text("Player 1");
		Text player1NameLabel = new Text("Username:");
		TextField player1NameTextField = new TextField();
		Text player1MarkerLabel = new Text("Marker:");
		TextField player1MarkerField = new TextField();
		Text isPlayer1AI = new Text("Is AI?");
		CheckBox isPlayer1AICheckbox = new CheckBox();
		
		Text player2Label = new Text("Player 2");
		Text player2NameLabel = new Text("Username:");
		TextField player2NameTextField = new TextField();
		Text player2MarkerLabel = new Text("Marker:");
		TextField player2MarkerField = new TextField();
		Text isPlayer2AI = new Text("Is AI?");
		CheckBox isPlayer2AICheckbox = new CheckBox();

		Button submitButton = new Button("Submit Players");
		
		playerScreen.add(player1Label,         0, 0);	
		playerScreen.add(new Text(""),         0, 1);
		playerScreen.add(isPlayer1AI,          0, 2);
		playerScreen.add(isPlayer1AICheckbox,  0, 3);		
		playerScreen.add(player1NameLabel,     0, 4);
		playerScreen.add(player1NameTextField, 0, 5);
		playerScreen.add(player1MarkerLabel,   0, 6);
		playerScreen.add(player1MarkerField,   0, 7);
		
		playerScreen.add(player2Label,         2, 0);
		playerScreen.add(new Text(""),         2, 1);
		playerScreen.add(isPlayer2AI,          2, 2);
		playerScreen.add(isPlayer2AICheckbox,  2, 3);
		playerScreen.add(player2NameLabel,     2, 4);
		playerScreen.add(player2NameTextField, 2, 5);
		playerScreen.add(player2MarkerLabel,   2, 6);
		playerScreen.add(player2MarkerField,   2, 7);
		
		playerScreen.add(new Text(""),         0, 8);
		
		playerScreen.add(submitButton,         0, 9);
		
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
           @Override 
           public void handle(MouseEvent e) { 
        	   
        	   if ( player1NameTextField.getText().equals("") || "".equals(player1MarkerField.getText()) || "".equals(player2NameTextField.getText()) || "".equals(player2MarkerField.getText()) ) {
        		   buildPlayerObjects(new Text("Invalid Input, please specify text to all fields."));
        		   return;
        	   }
        	   
        	   try {
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
	
	private GridPane formatGridPane(GridPane gridPane, Pos pos) {
        //Setting size for the pane 
        gridPane.setMinSize(windowWidth, windowHeight); 
        
        //Setting the padding  
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        
        //Setting the vertical and horizontal gaps between the columns 
        gridPane.setVgap(5); 
        gridPane.setHgap(5);       
        
        //Setting the Grid alignment 
        gridPane.setAlignment(pos); 
        
        return gridPane;
	}
	
	
	private void startNewGame(Text topLabel) {
		//TODO
		GridPane textPane = new GridPane();
		textPane.add(topLabel, 0, 0);
		textPane.setAlignment(Pos.CENTER);
		topLabel.setTextAlignment(TextAlignment.CENTER);
		root.setTop(textPane);

//		root.setTop(topLabel);
		
		GridPane timerScreen = new GridPane();
		
		Text timerLengthLabel = new Text("Timer length:");
		TextField timerLengthField = new TextField();
		Button submitButton = new Button("Submit");
		
		Text useTimerLabel = new Text("Use timer?");
		CheckBox useTimerField = new CheckBox();
		
		useTimerField.setSelected(true);
		
		timerScreen.add(useTimerLabel,    0, 0);
		timerScreen.add(useTimerField,    1, 0);
		
		timerScreen.add(timerLengthLabel, 0, 1);
		timerScreen.add(timerLengthField, 1, 1);
		
		timerScreen.add(submitButton,     0, 2);

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
        	   
        	   addTopButtons();
        	   updateView();
        	   return;
           } 
        };  
        submitButton.addEventFilter(MouseEvent.MOUSE_CLICKED, submitHandler);
        
        
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
        
		
		root.setCenter(formatGridPane(timerScreen, Pos.CENTER));
	}
	
	public void addTopButtons() {
		
		Button resetButton = new Button("Reset");
        EventHandler<MouseEvent> resetEventHandler = new EventHandler<MouseEvent>() { 
           @Override 
           public void handle(MouseEvent e) { 
        	   ticTacToe.resetGame();
        	   addTopButtons();
        	   updateView();
        	   player1Turn = true;
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
            	 System.exit(0);
            	 return;
             } 
          };  
        quitButton.addEventFilter(MouseEvent.MOUSE_CLICKED, quitEventHandler);
        
  		GridPane controlButtons = new GridPane();
  		controlButtons.add(new Text(""), 0, 1);
  		controlButtons.add(quitButton, 1, 1);
  		controlButtons.add(new Text(""),  2, 1);
  		controlButtons.add(resetButton,  3, 1);
  		controlButtons.setVgap(1);
  		controlButtons.setHgap(1);
  		controlButtons.setAlignment(Pos.CENTER_LEFT);
  		  		
  		GridPane topBar = new GridPane();
  		topBar.add(controlButtons,0,0);
  		
  		root.setTop(topBar);
	}
	
	public void updateView() {
		GridPane display = ticTacToe.getGUIDisplay();
		
//		display.setMinSize(windowWidth, windowHeight); 
		
		display.setHgap(-1);
		display.setVgap(-1);
        
        //Setting the Grid alignment 
		display.setAlignment(Pos.CENTER); 
        root.setCenter(display);
        setupUserMoveInputScreen();
	}
	
	public void setupUserMoveInputScreen() {
		GridPane userInputScreen = new GridPane();
		
		Text rowInputLabel = new Text("Desired Row:");
		TextField rowInputField = new TextField();
		
		Text colInputLabel = new Text("Desired Col:");
		TextField colInputField = new TextField();
		
		Button submitUserInputButton = new Button("Submit User Move");
				
		userInputScreen.add(rowInputLabel,    0, 2);
		userInputScreen.add(rowInputField,    1, 2);
		
		
		userInputScreen.add(colInputLabel,    0, 3);
		userInputScreen.add(colInputField,    1, 3);
		userInputScreen.setVgap(5);
		userInputScreen.setHgap(8);
		userInputScreen.add(submitUserInputButton,     1, 4);
        EventHandler<MouseEvent> submitEventHandler = new EventHandler<MouseEvent>() { 
        	@Override 
        	public void handle(MouseEvent e) { 
        		
        		int row;
        		int col;
        		
        		try {
        			row = Integer.parseInt(rowInputField.getText());
        			col = Integer.parseInt(colInputField.getText());
        		}
        		catch (NumberFormatException nfe) {
        			return;
        		}
        			
        		supplyMove(row,col);
        		System.out.println("User supplied a move. Row: " + row + ". Col: " + col);
        		
        		/* checkWinner() */
        		int winner = ticTacToe.determineWinner();
        		String resultString;
        		if (winner == 1) {
        			resultString = player1Username + " won the game!!";
        			System.out.println(resultString);
        			buildPlayerObjects(new Text(resultString));
        			return;
        		}
        		else if (winner == 2) {
        			resultString = player2Username + " won the game!!";
        			System.out.println(resultString);
        			buildPlayerObjects(new Text(resultString));
        			return;
        		} 
        		else if (winner == 3) {
        			
        			resultString = player1Username + " and " + player2Username + "'s game resulted in a draw!!";
        			System.out.println(resultString);
        			buildPlayerObjects(new Text(resultString));
        			return;
        		}
        		/* */
     
        		updateView();
        		return;
           } 
        };  
        submitUserInputButton.addEventFilter(MouseEvent.MOUSE_CLICKED, submitEventHandler);
        
		Button requestAIButton = new Button("Request AI Move");
		userInputScreen.add(requestAIButton,     2, 4);
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
        			buildPlayerObjects(new Text(resultString));
        			return;
        		}
        		else if (winner == 2) {
        			resultString = player2Username + " won the game!!";
        			System.out.println(resultString);
        			buildPlayerObjects(new Text(resultString));
        			return;
        		} 
        		else if (winner == 3) {
        			
        			resultString = player1Username + " and " + player2Username + "'s game resulted in a draw!!";
        			System.out.println(resultString);
        			buildPlayerObjects(new Text(resultString));
        			return;
        		}
        		/* */
        		updateView();

        		System.out.println("AI move requested.");
        		return;
           } 
        };  
        requestAIButton.addEventFilter(MouseEvent.MOUSE_CLICKED, requestAIHandler);
        
        //WRITE THE START TIME FOR THE TIMER
        ticTacToe.writeStartTime();
		
		root.setBottom(userInputScreen);
	}
	
	private void supplyMove(int row, int col) {
		
		boolean valid;
		
		try {
			if (player1Turn) {
				valid = ticTacToe.setSelection(row, col, 1);
			}
			else {
				valid = ticTacToe.setSelection(row, col, 2);
			}
		}
		catch (InvalidAIOperation iaio) {
			addTopButtons();
			GridPane y = ((GridPane)root.getTop());
			y.add(new Text("Please supply an AI's move, current player is not an AI."), 0, 1);
			return;
		}
		catch (TurnTimeoutException tte) {
			addTopButtons();
			GridPane y = ((GridPane)root.getTop());
			y.add(new Text("User took too long to provide input. Forfeiting turn..."), 0, 1);
			player1Turn = !player1Turn;
			return;
		}
		
		addTopButtons();
		if (valid) {
			GridPane y = ((GridPane)root.getTop());
			y.add(new Text("Successful placement!"), 0, 1);
		}
		else {
			GridPane y = ((GridPane)root.getTop());
			y.add(new Text("Failure to place move on grid."), 0, 1);
		}
		
		player1Turn = !player1Turn;
        ticTacToe.writeStartTime();
	}
	
	private void requestAIMove() {
		
		try {
			if (player1Turn) {
				ticTacToe.makeAISelection(1);
			}
			else {
				ticTacToe.makeAISelection(2);
			}
		}
		catch (InvalidPersonOperation ipo) {
			addTopButtons();
			GridPane y = ((GridPane)root.getTop());
			y.add(new Text("Please supply a user's move, current player is not an AI."), 0, 1);
			return;
		}
		catch (TurnTimeoutException tte) {
			addTopButtons();
			GridPane y = ((GridPane)root.getTop());
			y.add(new Text("User took too long to provide input. Forfeiting turn..."), 0, 1);
			player1Turn = !player1Turn;
			return;
		}
		
		addTopButtons();
		GridPane y = ((GridPane)root.getTop());
		y.add(new Text("Successful placement!"), 0, 1);
		
		player1Turn = !player1Turn;
        ticTacToe.writeStartTime();
	}
}
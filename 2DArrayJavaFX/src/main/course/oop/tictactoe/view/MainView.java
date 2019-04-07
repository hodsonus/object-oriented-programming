package main.course.oop.tictactoe.view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.MotionBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import main.course.oop.tictactoe.util.TwoDArray;

public class MainView {
	private BorderPane root;
	private Scene scene; 
    private TwoDArray twoDArr;
	private Text statusNode;
    private final int windowWidth = 800;
    private final int windowHeight = 600;
	
	public MainView() {
		this.root = new BorderPane();
		this.scene = new Scene(root, windowWidth, windowHeight);
		this.statusNode = new Text("no status");
		this.root.setTop(this.buildSetupPane());
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
	public GridPane buildSetupPane() {
	    Text sizeLabel = new Text("Number rows & columns:");  
	    Text defaultValLabel = new Text("Default value:");       
        TextField sizeTextField = new TextField();
        TextField defValueTextField = new TextField();
        
        Button button1 = new Button("Submit"); 
        Line line = new Line();
        
        
        line.setStartX(0.0f); 
        line.setStartY(0.0f);         
        line.setEndX((float) windowWidth); 
        line.setEndY(0.0f);
        
        //Creating the mouse event handler 
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
           @Override 
           public void handle(MouseEvent e) { 
               String size = sizeTextField.getText();
               String defaultVal = defValueTextField.getText();

               build2DArrayPane(size, defaultVal);
           } 
        };  
        //Registering the event filter 
        button1.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);   

        //Creating a Grid Pane 
        GridPane gridPane = new GridPane();    
        
        //Setting size for the pane 
        gridPane.setMinSize(windowWidth, (int) windowHeight/4); 
        
        //Setting the padding  
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        
        //Setting the vertical and horizontal gaps between the columns 
        gridPane.setVgap(5); 
        gridPane.setHgap(5);       
        
        //Setting the Grid alignment 
        gridPane.setAlignment(Pos.CENTER); 
        
        gridPane.add(sizeLabel, 0, 0); 
        gridPane.add(defaultValLabel, 1, 0); 
        
        gridPane.add(sizeTextField, 0, 1); 
        gridPane.add(defValueTextField, 1, 1); 
        
        gridPane.add(button1, 2, 1); 
        gridPane.add(line, 0, 2, 3, 1); 
              
        return gridPane;
	}
	
	public void build2DArrayPane(String size, String defaultVal) {
		String text = "";
		//Clear other panes
		root.setLeft(new Text());
		root.setRight(new Text());
		
		try {
			int intSize =Integer.parseInt(size);
			int intDefaultVal = Integer.parseInt(defaultVal);
	        twoDArr = new TwoDArray(intSize,intSize, intDefaultVal);
	        text = twoDArr.getArrayDisplay();
	        System.out.println("Hello World " + intSize +", "+intDefaultVal); //this will print out on the command line, not the GUI
			root.setLeft(build2DArrayInputPane());
		}catch(NumberFormatException nfe) {
			text = "Please enter integer values!";
			
		}
		Text arrDisplay = new Text(text);
		MotionBlur mb = new MotionBlur();
		mb.setRadius(5.0f);
		mb.setAngle(15.0f);
		arrDisplay.setEffect(mb);
		
		Rotate rotation = new Rotate();
		rotation.setAngle(20);
		arrDisplay.getTransforms().addAll(rotation);
		root.setCenter(arrDisplay);
	}
	
	public GridPane build2DArrayInputPane() {
        Button button1 = new Button("Random Insert"); 
            	
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
            @Override 
            public void handle(MouseEvent e) { 
            	
				boolean notValid = true;
				while (notValid) {
					int col = (int)Math.floor((Math.random() * 10));		
					int row = (int)Math.floor((Math.random() * 10));
					int val = (int)(Math.random() * 99);
					try {
						updateArrayValue(Integer.toString(row), Integer.toString(col), Integer.toString(val));
						notValid = false;
					}
					catch (IllegalArgumentException i) {
						notValid = true;
					}
				}
	                
            } 
         };  
		
			
			
        //Registering the event filter 
        button1.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);   

        //Creating a Grid Pane 
        GridPane gridPane = new GridPane();    
        
        //Setting size for the pane 
        gridPane.setMinSize(400, 200); 
        
        //Setting the padding  
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        
        //Setting the vertical and horizontal gaps between the columns 
        gridPane.setVgap(5); 
        gridPane.setHgap(5);       
        
        //Setting the Grid alignment 
        gridPane.setAlignment(Pos.CENTER); 
               
        gridPane.add(button1, 0, 6); 
        gridPane.add(statusNode, 0, 8); 
             
        return gridPane;

	}
	
	public void updateArrayValue(String row, String col, String val) {
			String text = "";
			String status = "";
			try {
				int intRow =Integer.parseInt(row);
				int intCol = Integer.parseInt(col);
				int intVal = Integer.parseInt(val);
		        status = twoDArr.insertInt(intRow, intCol, intVal);
		        text = twoDArr.getArrayDisplay();
			}catch(NumberFormatException nfe) {
				text = "Please enter integer values!";
				
			}
			Text textNode = new Text(text);
			textNode.setTextAlignment(TextAlignment.CENTER);
			
			Rotate rotation = new Rotate();
			rotation.setAngle(20);
			textNode.getTransforms().addAll(rotation);


			root.setCenter(textNode);
			statusNode.setText(status);
	}
}

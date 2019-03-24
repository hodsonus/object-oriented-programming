package main.course.oop.tictactoe.application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import main.course.oop.tictactoe.util.TwoDArray;
import main.course.oop.tictactoe.view.MainView;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			MainView mView = new MainView();
			Scene scene = mView.getMainScene();
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("2D Array GUI Example - John Hodson");
			primaryStage.setScene(scene);primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

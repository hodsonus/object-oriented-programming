package course.oop.application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import course.oop.view.MainView;
import javafx.scene.Scene;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			MainView mView = new MainView();
			Scene scene = mView.getMainScene();

			
			//TODO
//			Font.loadFont(getClass().getResourceAsStream("NotoColorEmoji.ttf"), 16);
//			Font.loadFont(getClass().getResourceAsStream("OpenSansEmoji.ttf"), 16);
			
			
//			Font.loadFont(
//					  getClass().getResource("NotoColorEmoji.ttf").toExternalForm(), 
//				      10
//				    );


			
			scene.getStylesheets().add(getClass().getResource("./application.css").toExternalForm());


			primaryStage.setTitle("Tic Tac Toe Project #3 - John Hodson");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
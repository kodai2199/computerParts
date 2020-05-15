package app;
	
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	private double xOffset = 0;
    private double yOffset = 0;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Login.fxml"));			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
			primaryStage.setMinWidth(654);
			primaryStage.setMinHeight(440);
			primaryStage.setScene(scene);
			//primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

package Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ClientMain extends Application{

	@Override
	public void start(Stage primaryStage) {
		try {
			
			Scene scene = new Scene(new StackPane());
			
			ClientGUI_Manager gui_manager = new ClientGUI_Manager(scene, primaryStage);
			
			gui_manager.showLoginPage();
			
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

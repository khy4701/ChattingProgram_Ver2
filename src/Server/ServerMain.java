package Server;

import Client.ClientGUI.ClientGUI_Manager;
import Server.GUI.ServerGUI_Manager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ServerMain extends Application{

	@Override
	public void start(Stage primaryStage) {
		try {
			
			Scene scene = new Scene(new StackPane());
			
			ServerGUI_Manager gui_manager = new ServerGUI_Manager(scene, primaryStage);
			
			gui_manager.showMainPage();
			
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
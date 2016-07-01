package Client;

import java.io.IOException;

import Util.DataBase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ClientGUI_Manager {

	ClientGUI_Chatting ChatGUI;
	ClientGUI_Login loginGUI;
	ClientBackground clientBackground;
	
	private Stage primaryStage;
	private Scene scene ;
	
	// For Database
	private DataBase db;
	
	public ClientGUI_Manager(Scene scene, Stage primaryStage)
	{
		this.scene = scene;		
		this.primaryStage = primaryStage;
		db = new DataBase();
	}
	
	public void showLoginPage()
	{		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../Client/ClientGUI/LoginScreen.fxml"));
			Parent root = loader.load();
			AnchorPane archonpane = (AnchorPane)root;
						
			// ClientMain에서 가져온 Scene의 root Container만 변경.
			scene.setRoot(root);
			
			// 해당 화면 크기를 조정.
			primaryStage.setWidth(archonpane.getPrefWidth()+20);            // root.getWidth() 로 하지 말것.
			primaryStage.setHeight(archonpane.getPrefHeight()+20);
			
			// ClientGUI_Login 			
			loginGUI = loader.getController();
			loginGUI.StartLoginListener(this);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
				
	}
	
	public void showMainPage()
	{		
		try {
			// 메인 대화창 불러오기
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../Client/ClientGUI/ChattingScreen.fxml"));
			
			Parent root= loader.load();
			AnchorPane archonpane = (AnchorPane)root;
			
			// 창 변환 
			scene.setRoot(root);			
			primaryStage.setWidth(archonpane.getPrefWidth()+20);            // root.getWidth() 로 하지 말것.
			primaryStage.setHeight(archonpane.getPrefHeight()+20);
			
			ChatGUI = loader.getController();				
			ChatGUI.StartChatListener(this);		
			
			clientBackground = new ClientBackground(ChatGUI);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}
	
	public ClientBackground getBackgroundControl()
	{
		return clientBackground;
	}
}

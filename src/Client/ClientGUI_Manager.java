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
						
			// ClientMain���� ������ Scene�� root Container�� ����.
			scene.setRoot(root);
			
			// �ش� ȭ�� ũ�⸦ ����.
			primaryStage.setWidth(archonpane.getPrefWidth()+20);            // root.getWidth() �� ���� ����.
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
			// ���� ��ȭâ �ҷ�����
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../Client/ClientGUI/ChattingScreen.fxml"));
			
			Parent root= loader.load();
			AnchorPane archonpane = (AnchorPane)root;
			
			// â ��ȯ 
			scene.setRoot(root);			
			primaryStage.setWidth(archonpane.getPrefWidth()+20);            // root.getWidth() �� ���� ����.
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

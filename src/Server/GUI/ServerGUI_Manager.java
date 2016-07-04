package Server.GUI;

import java.io.IOException;

import Server.ServerBackground;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ServerGUI_Manager {

	private Scene scene;
	private Stage primaryStage;
	
	private ServerGUI_Main mainGUI;
	private ServerBackground background;
	
	public ServerGUI_Manager(Scene scene, Stage primaryStage)
	{
		this.scene = scene;		
		this.primaryStage = primaryStage;
	}
	
	public void showMainPage()
	{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
			Parent root = loader.load();
			AnchorPane archonpane = (AnchorPane)root;
						
			// ClientMain���� ������ Scene�� root Container�� ����.
			scene.setRoot(root);
			
			// �ش� ȭ�� ũ�⸦ ����.
			primaryStage.setWidth(archonpane.getPrefWidth()+20);            // root.getWidth() �� ���� ����.
			primaryStage.setHeight(archonpane.getPrefHeight()+20);
			
			// ClientGUI_Login 			
			mainGUI = loader.getController();
			mainGUI.StartListener(this);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void BackgroundStart()
	{
		background = new ServerBackground(mainGUI);		
		background.start();
	}
	
	public void BackgroundStop()
	{
		background.interrupt();
	}
	
	public void ServerToClient_BroadCasting(String str)
	{
		background.ServerToClient_BroadCasting(str);
	}
	
}

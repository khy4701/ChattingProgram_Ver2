package Server.GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.ClientBackground;
import Util.Message;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class ServerGUI_Main implements Initializable{

	ObservableList<String> list = FXCollections.observableArrayList("");
	ArrayList<String> userName = null;
	boolean start_status = false;
	// For GUI fxml
	@FXML
	private ToggleGroup listgroup;
	@FXML
	private Button btnExit;
	@FXML
	private Button btnStart;
	@FXML
	private ListView<String> txtUserList;
	@FXML
	private TextField txtChatField;
	@FXML
	private TextArea txtChatArea;
	@FXML
	private Label totalNum;
		
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		txtUserList.setItems(list);		
		txtChatField.setDisable(true);
	}
	
	public void AddTextArea(String str)
	{
		txtChatArea.appendText(str + "\n");
	}
	
	public void StartListener(final ServerGUI_Manager manager)
	{
		btnStart.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				AddTextArea("서버를 시작하겠습니다.");
				manager.BackgroundStart();
				btnStart.setDisable(true);
				btnExit.setDisable(false);
				txtChatField.setDisable(false);
				
				start_status = true;

			}
		});
		
		btnExit.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				AddTextArea("서버를 종료하겠습니다.");
				manager.BackgroundStop();
				btnExit.setDisable(true);
				btnStart.setDisable(false);
				txtChatField.setDisable(true);
				
				start_status = false;
			}
		});

		txtChatField.setOnAction(event -> {

			// TextArea에 추가.
			String str = txtChatField.getText();
		
			if (start_status) {
				
				manager.ServerToClient_BroadCasting("[Server] :" +str);
				txtChatArea.appendText("[Server] :" +str);
			}
			txtChatField.clear();
		});
	}
	
	public void setNewPeople(ArrayList<String> userName, String str)
	{
		this.userName = userName;	
		Platform.runLater(() -> {
			
			list.clear();				  // 기존 List 삭제		
			for(String i : userName)     // Client 이름 ArrayList 추가
			{
				list.add(i);
			}			
			totalNum.setText(Integer.toString(userName.size()));
			
			AddTextArea(str);
		});		
	}
	
	public void setExitPeople(String id, String str)
	{
		int index;
		for (index = 0 ; index< userName.size(); index++) 
		{			
			if(userName.get(index).equals(id))
				break;
		}
		userName.remove(index);
		
		Platform.runLater(() -> {

			list.clear(); // 기존 List 삭제
			for (String i : userName) // Client 이름 ArrayList 추가
			{
				list.add(i);
			}

			totalNum.setText(Integer.toString(userName.size()));
			AddTextArea(str);
		});
		
	}

}

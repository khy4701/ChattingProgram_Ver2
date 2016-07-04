package Client.ClientGUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.ClientBackground;
import Util.Message;
import Util.PrintMessage;
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

public class ClientGUI_Chatting implements Initializable{

	ObservableList<String> list = FXCollections.observableArrayList("");

	// For GUI fxml
	@FXML
	private ToggleGroup listgroup;
	@FXML
	private Button btnExit;
	@FXML
	private ListView<String> txtUserList;
	@FXML
	private TextField txtChatField;
	@FXML
	private TextArea txtChatArea;
	@FXML
	private RadioButton RadioWhisper;
	@FXML
	private Label clientName;	
	@FXML
	private Label totalNum;
	
	private boolean connect_status = false;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		connect_status = true;
		txtUserList.setItems(list);
	}
	
	public void Add_TextData(String data)
	{		
		Platform.runLater(() -> {

		txtChatArea.appendText(data+"\n");
		});
	}
	
	public void setTotalNum(String total_num)
	{
		Platform.runLater(() -> {
			totalNum.setText(total_num);
			
			if(Integer.parseInt(total_num) >= 2)
			{
				RadioWhisper.setDisable(false);
			}else RadioWhisper.setDisable(true);
		});
	}
	
	public void setClientName(String name)
	{
		Platform.runLater(() -> {
		clientName.setText(name);
		});
	}
	
	public void setTextChatField(boolean status)
	{
		// ChatField 상태 false로.
		if(status == false ) 
			txtChatField.setDisable(true);
		else 
			txtChatField.setDisable(false);
	}
	public void setConnectStatus()
	{
		txtChatField.setDisable(true);
	}
	
	public void setListView(ArrayList<String> userName)
	{
		Platform.runLater(() -> {
			
			list.clear();				  // 기존 List 삭제		
			for(String i : userName)     // Client 이름 ArrayList 추가
			{
				list.add(i);
			}
		});
	}
	
	public void StartChatListener( final ClientGUI_Manager gui_manager )
	{
		btnExit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
								
				//clientBackground.disconnect();
				PrintMessage.printUserAndTimeOrder("Disconnect");
				connect_status = false;
				
				// Background Disconnect 처리
				gui_manager.getBackgroundControl().disconnect();
				gui_manager.showLoginPage();
			
			}
		});

		txtChatField.setOnAction(event -> {

			// TextArea에 추가.
			String str = txtChatField.getText();
		
			if (connect_status) {
				try {				
					String toggle = listgroup.getSelectedToggle().getUserData().toString();					
					ClientBackground control = gui_manager.getBackgroundControl();
					
					if( toggle.equals("BroadCast") )
					{
						control.sendMsg(new Message(Message.CS_TOTAL_MSG, str));//						
					}
					else if( toggle.equals("Whisper"))
					{
						String listText = txtUserList.getSelectionModel().getSelectedItem();
						// 자기 자신에게는 Whisper하지 않는다.
						// 선택이 되어 있을 경우
						if (listText != null ) {				
							String client_id = control.getClientName();
							if (!listText.equals(client_id)) {
								// MessageType, Sender, Receiver
								System.out.println(client_id + " to " + listText);								
								control.sendMsg(new Message(Message.CS_SECRET_MSG, str, client_id, listText));	
								
							}else
								txtChatArea.appendText("자기 자신에겐 귓속말을 보낼 수 없습니다.\n");
						}else
							txtChatArea.appendText("귓속말할 대상을 선택하세요.\n");
					}										
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			txtChatField.clear();
		});
		
		
		txtUserList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				System.out.println("changed");
				System.out.println(txtUserList.getSelectionModel().getSelectedItem());

			}
		});

		listgroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				// TODO Auto-generated method stub
				if (listgroup.getSelectedToggle() != null) {
					System.out.println(listgroup.getSelectedToggle().getUserData().toString());
				}

			}
		});
	}
	
}

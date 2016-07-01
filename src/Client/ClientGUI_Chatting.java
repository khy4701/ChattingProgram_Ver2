package Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Util.PrintMessage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ClientGUI_Chatting implements Initializable{

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
	private Label labelIp;
	@FXML
	private Label labelPort;
	
	// For Background
	private ClientInfo userInfo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	
	public void Add_TextData(String data)
	{		
		//txtChatArea.appendText(data+"\n");
	}
	
	public void StartChatListener( final ClientGUI_Manager gui_manager )
	{
		btnExit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
								
				//clientBackground.disconnect();
				PrintMessage.printUserAndTimeOrder("Disconnect");

				// Background Disconnect Ã³¸®
				gui_manager.getBackgroundControl().disconnect();
				gui_manager.showLoginPage();
			
			}
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

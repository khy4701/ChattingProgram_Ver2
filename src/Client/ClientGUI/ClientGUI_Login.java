package Client.ClientGUI;

import java.net.URL;
import java.util.ResourceBundle;

import Client.ClientInfo;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClientGUI_Login implements Initializable{
	
	// For GUI FXML Login
	@FXML
	private Button btnLogin;
	@FXML
	private Button btnCancel;
	@FXML
	private Button btnAssign;
	@FXML
	private TextField id_txtField;
	@FXML
	private PasswordField pw_PwField;
	
	// For GUI Java
	private Stage primaryStage;
		
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub				
	}
		
	public void StartLoginListener(final ClientGUI_Manager gui_manager)
	{
		btnLogin.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				String id = id_txtField.getText();
				String pw = pw_PwField.getText();
				
				if(! ( id.equals("") || pw.equals("")))
				{
					ClientInfo clientInfo = new ClientInfo(id, pw);				
					gui_manager.showMainPage(clientInfo);
				}
				else
					ShowPopup();
			}			
		});
		
		btnCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Platform.exit();
			}
		});
		
		btnAssign.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				gui_manager.showAssignPage();
			}
			
		});
	}
	
	public void ShowPopup()
	{
		System.out.println("아이디 비번을 입력하세요.");								

	}
	
}

package Client.ClientGUI;

import java.net.URL;
import java.util.ResourceBundle;

import Util.Database;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ClientGUI_Assign implements Initializable{

	@FXML private TextField txtId;
	@FXML private TextField txtIp;
	@FXML private TextField txtPort;
	@FXML private PasswordField txtPw;
	@FXML private Button btnOk;
	@FXML private Button btnCancel;
	
	private Database db;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		db = new Database();
	}
	
	public void StartAssignListener(ClientGUI_Manager manager)
	{
		btnOk.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				String id = txtId.getText();
				String ip = txtIp.getText();
				String pw = txtPw.getText();
				int port = Integer.parseInt(txtPort.getText());
					
				if( !id.equals("") && !pw.equals("") && !ip.equals("") && port != 0)
				{
					if( db.AddUserInfo(id, pw, ip, port) )
						manager.showLoginPage();										
				}
				else
					System.out.println("회원 가입 정보 미입력.");		
			}
		});
		
		btnCancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				manager.showLoginPage();

			}
		});
		
	}

}

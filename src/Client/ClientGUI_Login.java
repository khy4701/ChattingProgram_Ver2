package Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Util.DataBase;
import Util.PrintMessage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ClientGUI_Login implements Initializable{
	
	// For GUI FXML Login
	@FXML
	private Button btnLogin;
	@FXML
	private Button btnCancel;
	@FXML
	private TextField id_txtField;
	@FXML
	private PasswordField pw_PwField;
	
	// For GUI Java
	private Stage primaryStage;
		
	// For Database
	private DataBase db;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub				
		db = new DataBase();
	}
		
	public void StartLoginListener(final ClientGUI_Manager gui_manager)
	{
		btnLogin.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				String id = id_txtField.getText();
				String pw = pw_PwField.getText();

				if (db != null && db.Connection(id, pw)) {
					PrintMessage.printUserAndTimeOrder(" 연결에 성공 하였습니다. ");
					gui_manager.showMainPage();
				}

			}
		});
	}
	
}

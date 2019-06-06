package grocery_pal.ui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;

import grocery_pal.db.DBClient;
import grocery_pal.model.Session;
import grocery_pal.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class LoginController extends Controller {

	//Variables for manipulating the controls in the current scene
	@FXML BorderPane rootPane;
	@FXML TextField txtUName;
	@FXML TextField txtPass;
	@FXML Label lblErr;
	private boolean allow = false;
	
	//For managing the button_click action
	@FXML
	private void btnLoginClick(){
		String username = txtUName.getText();
		String password = txtPass.getText();
		int UID = DBClient.logIn(username, password); //first verify the user against database entries.
		if(UID != -1)
		{
			Timer timer = new Timer();
			timer.schedule(new Session(new User(UID, username, password)), 10000);
			allow = true;
			closeWindow(rootPane, false);
		}
		else
			lblErr.setVisible(true);
	}
	
	public boolean allowContinue() {
		return allow;
	}
	
	//For initializing the scene
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
}

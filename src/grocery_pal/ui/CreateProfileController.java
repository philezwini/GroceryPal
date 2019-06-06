package grocery_pal.ui;

import java.net.URL;
import java.util.ResourceBundle;

import grocery_pal.db.DBClient;
import grocery_pal.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class CreateProfileController extends Controller {

	@FXML private BorderPane rootPane; //We are going to use this to keep track of the current scene.
	@FXML private TextField txtUName, txtPass, txtCPass;
	@FXML private Label lblErr; //for displaying error messages in exception cases.
	private boolean allowContinue = false;
	
	//function for handling the user's attempt to create a new profile.
	@FXML
	private void btnConfirmClick() {
		if(!(txtPass.getText().equals(txtCPass.getText())))
			lblErr.setVisible(true);
		else {
			String username = txtUName.getText();
			String password = txtPass.getText();
			User user = new User(username, password);
			DBClient.addUser(user);
			allowContinue = true;
			closeWindow(rootPane, false);
		}
	}
	
	public boolean allowContinue() {
		return allowContinue;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

}

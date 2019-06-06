package grocery_pal.ui;

import java.net.URL;
import java.util.ResourceBundle;

import grocery_pal.db.DBClient;
import grocery_pal.model.Merchant;
import grocery_pal.model.Session;
import grocery_pal.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class PrefMerchController extends Controller{
	@FXML BorderPane rootPane;
	@FXML TextField txtName, txtURL;
	@FXML RadioButton rdCh1, rdCh2, rdCh3;
	
	@FXML
	private void btnConfirmClick() {
		String name = txtName.getText();
		int rank = 3;
		if(rdCh1.isSelected())
			rank = 1;
		else if(rdCh2.isSelected())
			rank = 2;
	
		String url = txtURL.getText();
		User user = Session.User();
		Merchant m = new Merchant(user.getId(), name, url, Integer.toString(rank), "preferred");
		DBClient.addPrefMerch(m);
		closeWindow(rootPane, false);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
}

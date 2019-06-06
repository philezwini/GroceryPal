package grocery_pal.ui;

import java.net.URL;
import java.util.ResourceBundle;

import grocery_pal.db.DBClient;
import grocery_pal.model.GroceryItem;
import grocery_pal.model.Session;
import grocery_pal.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class GroceryItemController extends Controller{
	@FXML BorderPane rootPane;
	@FXML TextField txtName, txtQuantity;
	@FXML TextArea txtDescription;
	@FXML Label lblErr;
	
	@FXML
	private void btnConfirmClick() {
		String name = txtName.getText();
		String description = txtDescription.getText();
		User user = Session.User();
		GroceryItem item = new GroceryItem(user.getId(), name, description, "current", "Unknown");
		DBClient.addGrocItem(item);
		closeWindow(rootPane, false);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

}

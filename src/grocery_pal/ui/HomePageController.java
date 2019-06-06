package grocery_pal.ui;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;

public class HomePageController extends Controller{
	@FXML private BorderPane rootPane;
	
	@FXML
	private void loginMenuItemClick() {
		attemptLogin();
	}
	
	private void attemptLogin() {
		LoginController c = (LoginController)showSecondary("LoginDesign.fxml");
		if(c.allowContinue())
			switchScene("Settings.fxml", rootPane);
	}
	
	@FXML
	private void cProfMenuItemClick() {	
		CreateProfileController c = (CreateProfileController)showSecondary("CProfile.fxml");
		if(c.allowContinue())
			attemptLogin();
	}
	
	@FXML
	private void closeMenuItemClick() {		
		closeWindow(rootPane, true);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Display the soccer field as a background image for the VBox defined in the FXML document.
		File imageFile = new File("img/S-1.png"); //Extract image file.
		Image image = new Image(imageFile.toURI().toString()); //Get the image using its URI.
		BackgroundSize size = new BackgroundSize(100, 100, true, true, true, true); //Create background size;
		//Create a background image.
		BackgroundImage bImg = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size);
		rootPane.setBackground(new Background(bImg)); //Set the background for the VBox as the background image.
	}	
}

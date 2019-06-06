import java.io.IOException;
import java.util.Optional;

import grocery_pal.ui.UIHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class Main extends Application {
	private Stage window;
	
	@Override
	public void start(Stage primaryStage) {
		this.window = primaryStage;
		loadFXML();
	}
	
	private void loadFXML() {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("grocery_pal/ui/Design.fxml"));
			window.setScene(new Scene(parent));
			window.setTitle("GroceryPal - At Your Service");
			window.centerOnScreen();
			window.setOnCloseRequest(e -> {
				e.consume(); //Consume the event so we can handle it manually.
				closeWindow();
			});
			window.show();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void closeWindow() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Close GroceryPal");
		alert.setHeaderText("You are about to exit GroceryPal.");
		alert.setContentText("Are you sure you want to exit?");
		UIHelper.findCenter(alert, window);
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK)
			window.close();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

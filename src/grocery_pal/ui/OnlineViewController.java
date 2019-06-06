package grocery_pal.ui;

import java.net.URL;
import java.util.ResourceBundle;

import grocery_pal.model.Session;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class OnlineViewController extends Controller {
	@FXML private WebView webView;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(Session.User() != null) {
			String url = (String)Session.get("URL");
			WebEngine engine = webView.getEngine();
			engine.load(url);
		}
	}
}

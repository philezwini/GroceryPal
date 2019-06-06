package grocery_pal.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import grocery_pal.db.DBClient;
import grocery_pal.model.GroceryItem;
import grocery_pal.model.Merchant;
import grocery_pal.model.Session;
import grocery_pal.model.User;
import grocery_pal.web.Dispatcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ShoppingController extends Controller {
	@FXML BorderPane rootPane;
	//All the control variables needed to use the tables.
	@FXML TableView<GroceryItem> tGrocList;
	@FXML TableView<GroceryItem> tHGrocList;
	@FXML TableView<GroceryItem> tFGrocList;
	//Columns for tGrocList
	@FXML private TableColumn<GroceryItem, String> colName, colDescription, colQuantity, colPrice, colSName, colSURL;
	//Columns for tHGrocList
	@FXML private TableColumn<GroceryItem, String> colHName, colHDescription, colHQuantity, colHPrice, colHStoreName, colHStoreURL, colHStatus;
	//Columns for tFGrocList
	@FXML private TableColumn<GroceryItem, String> colFName, colFDescription, colFQuantity, colFPrice, colFStoreName, colFStoreURL, colFStatus;
	
	ArrayList<GroceryItem> fItems; //List for keeping storing grocery items found on the web.
	
	private void updateTGrocList(int userId) {
		//Fetch grocery items from the database.
		ArrayList<GroceryItem> grocList = DBClient.getGrocItems(userId);
		
		if(grocList != null) {
			//Define the attribute names the columns need to look for in the GroceryItem class.
			colName.setCellValueFactory(new PropertyValueFactory<>("name"));
			colName.setCellFactory(TextFieldTableCell.<GroceryItem>forTableColumn()); //Make the columns editable.
			
			colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
			colDescription.setCellFactory(TextFieldTableCell.<GroceryItem>forTableColumn());
			
			colSURL.setCellValueFactory(new PropertyValueFactory<>("storeURL"));
			colSURL.setCellFactory(TextFieldTableCell.<GroceryItem>forTableColumn());

			ObservableList<GroceryItem> grocOList = FXCollections.observableArrayList(grocList);
			tGrocList.setItems(grocOList);
			
			//Add a context menu from removing a grocery item from the list.
			ContextMenu c = new ContextMenu();
			tGrocList.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				if(e.getButton() == MouseButton.SECONDARY) {
					c.show(tGrocList, e.getScreenX(), e.getScreenY());
				}
			});
			
			MenuItem rMenuItem = new MenuItem("Remove item");
			rMenuItem.setOnAction(e -> {
				GroceryItem item = tGrocList.getSelectionModel().getSelectedItem();
				DBClient.removeGrocItem(item);
				tGrocList.getItems().remove(item);
				Stage s = (Stage)rootPane.getScene().getWindow();
				UIHelper.makeCustomAlert(s, "Changes saved.");
			});
			c.getItems().add(rMenuItem);
		}
	}
	
	private void updateTHGrocList(int userId) {
		//Fetch grocery items from the database.
		ArrayList<GroceryItem> hGrocList = DBClient.getHGrocItems(userId);
		
		if(hGrocList != null) {
			//Define the attribute names the columns need to look for in the GroceryItem class.
			colHName.setCellValueFactory(new PropertyValueFactory<>("name"));
			colHName.setCellFactory(TextFieldTableCell.<GroceryItem>forTableColumn()); //Make the columns editable.
			
			colHDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
			colHDescription.setCellFactory(TextFieldTableCell.<GroceryItem>forTableColumn());
		
			colHStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
			colHStatus.setCellFactory(TextFieldTableCell.<GroceryItem>forTableColumn());
			
			colHStoreURL.setCellValueFactory(new PropertyValueFactory<>("storeURL"));
			colHStoreURL.setCellFactory(TextFieldTableCell.<GroceryItem>forTableColumn());
			
			ObservableList<GroceryItem> fGrocOList = FXCollections.observableArrayList(hGrocList);
			tHGrocList.setItems(fGrocOList);
		}
	}
	
	private void updateTFGrocList(ArrayList<GroceryItem> items) {
		if(items != null) {
			//Define the attribute names the columns need to look for in the GroceryItem class.
			colFName.setCellValueFactory(new PropertyValueFactory<>("name"));
			colFName.setCellFactory(TextFieldTableCell.<GroceryItem>forTableColumn()); //Make the columns editable.
			
			colFStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
			colFStatus.setCellFactory(TextFieldTableCell.<GroceryItem>forTableColumn());
			
			colFStoreURL.setCellValueFactory(new PropertyValueFactory<>("storeURL"));
			colFStoreURL.setCellFactory(TextFieldTableCell.<GroceryItem>forTableColumn());
			
			ObservableList<GroceryItem> oItems = FXCollections.observableArrayList(items);
			tFGrocList.setItems(oItems);
			
			//Add a context menu for visiting the web page that contains the selected grocery item.
			ContextMenu c = new ContextMenu();
			tFGrocList.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				if(e.getButton() == MouseButton.SECONDARY) {
					c.show(tFGrocList, e.getScreenX(), e.getScreenY());
				}
			});
			
			MenuItem visitMenuItem = new MenuItem("Visit store");
			visitMenuItem.setOnAction(e -> {
				GroceryItem item = tFGrocList.getSelectionModel().getSelectedItem();
				String url = item.getStoreURL();
				Session.add("URL", url);
				showSecondary("OnlineView.fxml");
			});
			
			MenuItem acceptMenuItem = new MenuItem("Accept Item");
			acceptMenuItem.setOnAction(e -> {
				GroceryItem item = tFGrocList.getSelectionModel().getSelectedItem();
				item.setStatus("Accepted");
				DBClient.addHGrocItem(item);
				tFGrocList.getItems().remove(item);
				Stage s = (Stage)rootPane.getScene().getWindow();
				UIHelper.makeCustomAlert(s, "Changes saved.");
				updateTHGrocList(Session.User().getId());
			});
			
			MenuItem rejectMenuItem = new MenuItem("Reject Item");
			rejectMenuItem.setOnAction(e -> {
				GroceryItem item = tFGrocList.getSelectionModel().getSelectedItem();
				item.setStatus("Rejected");
				DBClient.addHGrocItem(item);
				tFGrocList.getItems().remove(item);
				Stage s = (Stage)rootPane.getScene().getWindow();
				UIHelper.makeCustomAlert(s, "Changes saved.");
				updateTHGrocList(Session.User().getId());
			});
			c.getItems().add(visitMenuItem);
			c.getItems().add(acceptMenuItem);
			c.getItems().add(rejectMenuItem);
		}
	}
	
	@FXML
	private void settingsMenuItemClick() {
		if(Session.User() != null) {
			switchScene("Settings.fxml", rootPane);
		}
	}
	
	protected Controller showSecondary(String fxmlURL) {
		FXMLLoader loader = new FXMLLoader();
		try {
			loader.setLocation(getClass().getResource(fxmlURL));
			Parent parent = loader.load();
			Stage secStage = new Stage();
			secStage.setScene(new Scene(parent));
			secStage.initModality(Modality.APPLICATION_MODAL);
			secStage.setMaximized(true);
			secStage.showAndWait();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}
	
	@FXML
	private void closeMenuItemClick() {
		closeWindow(rootPane, true);
	}
	
	@FXML
	private void logoutMenuItemClick() {
		Session.destroy();
		switchScene("Design.fxml", rootPane);
	}
		
	@FXML
	private void btnStartClick() {
		if(Session.User() != null) {
			int userId = Session.User().getId();
			ArrayList<Merchant> merchList = DBClient.getPrefMerchs(userId);
			ArrayList<GroceryItem> items = DBClient.getGrocItems(userId);
			if(items == null) {
				Stage thisStage = (Stage)rootPane.getScene().getWindow();
				UIHelper.makeCustomAlert(thisStage, "Please add grocery items first.");
				return;
			}
			Dispatcher dispatcher = new Dispatcher(merchList);
			for(GroceryItem item : items) {
				GroceryItem foundItem = dispatcher.findItem(item);
				if(foundItem != null) {
					fItems.add(foundItem);
					System.out.println(foundItem.getName() + " found.");
				}
				else {
					System.out.println(item.getName() + " not found.");
				}
			}
			System.out.println("Done.");
			updateTFGrocList(fItems);
			return;
		}
		switchScene("LoginDesign.fxml", rootPane);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(Session.User() != null) {
			fItems = new ArrayList<>();
			User user = Session.User();
			updateTGrocList(user.getId());
			updateTHGrocList(user.getId());
		}
		
	}
}

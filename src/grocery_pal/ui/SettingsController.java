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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SettingsController extends Controller {
	@FXML private BorderPane rootPane;
	@FXML private Label lblErr;
	
	//All the control variables needed to use the tables.
	@FXML private TableView<GroceryItem> grocTable;
	@FXML private TableView<Merchant> merchTable;
	@FXML private TableColumn<GroceryItem, String> colName, colDescription, colStoreURL;
	@FXML private TableColumn<Merchant, String> colMerchName, colURL, colRank;
	
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
	private void newGrocMenuItemCick() {
		if(Session.isActive()) {
			super.showSecondary("GroceryItem.fxml");
			updateGrocTable(Session.User());
		}
		else
			showSecondary("LoginDesign.fxml");
	}
	
	@FXML
	private void newMerchMenuItemCick() {
		if(Session.isActive()) {
			super.showSecondary("PrefMerchant.fxml");
			updateMerchTable(Session.User());
		}
		else
			showSecondary("LoginDesign.fxml");
	}
	
	@FXML
	private void stMenuItemClick(){
		if(Session.isActive()) {
			switchScene("Shopping.fxml");
		}
		else
			showSecondary("Login.fxml");
	}
	
	private void updateGrocTable(User user) {
		//Fetch grocery items from the database.
		ArrayList<GroceryItem> grocList = DBClient.getGrocItems(user.getId());
		if(grocList != null) {
			//Define the attribute names the columns need to look for in the GroceryItem class.
			colName.setCellValueFactory(new PropertyValueFactory<>("name"));
			colName.setCellFactory(TextFieldTableCell.<GroceryItem>forTableColumn()); //Make the columns editable.
			
			colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
			colDescription.setCellFactory(TextFieldTableCell.<GroceryItem>forTableColumn());
			
			colStoreURL.setCellValueFactory(new PropertyValueFactory<>("storeURL"));
			colStoreURL.setCellFactory(TextFieldTableCell.<GroceryItem>forTableColumn());
			
			ObservableList<GroceryItem> grocOList = FXCollections.observableArrayList(grocList);
			grocTable.setItems(grocOList);
			
			//Add a context menu from removing a grocery item from the list.
			ContextMenu c = new ContextMenu();
			grocTable.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				if(e.getButton() == MouseButton.SECONDARY) {
					c.show(grocTable, e.getScreenX(), e.getScreenY());
				}
			});
			
			MenuItem rMenuItem = new MenuItem("Remove item");
			rMenuItem.setOnAction(e -> {
				GroceryItem item = grocTable.getSelectionModel().getSelectedItem();
				DBClient.removeGrocItem(item);
				grocTable.getItems().remove(item);
				Stage s = (Stage)rootPane.getScene().getWindow();
				UIHelper.makeCustomAlert(s, "Changes saved.");
			});
			c.getItems().add(rMenuItem);
		}
	}
	
	private void updateMerchTable(User user) {
		//Fetch preferred merchants from database.
		ArrayList<Merchant> merchList = DBClient.getPrefMerchs(user.getId());
		if(merchList != null) {
			//Define the attribute names the columns need to look for in the Merchant class.
			colMerchName.setCellValueFactory(new PropertyValueFactory<>("name"));
			colMerchName.setCellFactory(TextFieldTableCell.<Merchant>forTableColumn());

			colURL.setCellValueFactory(new PropertyValueFactory<>("URL"));
			colURL.setCellFactory(TextFieldTableCell.<Merchant>forTableColumn());

			colRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
			colRank.setCellFactory(TextFieldTableCell.<Merchant>forTableColumn());

			ObservableList<Merchant> merchOLIst = FXCollections.observableArrayList(merchList);
			merchTable.setItems(merchOLIst);
			
			//Add a context menu from removing a preferred merchant from the list.
			ContextMenu c = new ContextMenu();
			merchTable.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				if(e.getButton() == MouseButton.SECONDARY) {
					c.show(merchTable, e.getScreenX(), e.getScreenY());
				}
			});
			
			MenuItem rMenuItem = new MenuItem("Remove item");
			rMenuItem.setOnAction(e -> {
				Merchant merchant = merchTable.getSelectionModel().getSelectedItem();
				DBClient.removePrefMerchant(merchant); //remove preferred merchant from database.
				merchTable.getItems().remove(merchant); //remove grocery item from database.
				Stage s = (Stage)rootPane.getScene().getWindow();
				UIHelper.makeCustomAlert(s, "Changes saved.");
			});
			c.getItems().add(rMenuItem);
		}
	}
	
	//Handling column edit events by storing the newly committed value(s) to the database.
	@FXML
	private void grocColEditCommit(CellEditEvent<GroceryItem, String> e) {
		if(Session.isActive()) {
			GroceryItem item = e.getRowValue();
			String newValue = e.getNewValue();
			
			@SuppressWarnings("unchecked")
			TableColumn<GroceryItem, String> c = (TableColumn<GroceryItem, String>)e.getSource();
			String colName = c.getText();
			
			if(colName.equals("Item Name"))
				item.setName(newValue);
			else if(colName.equals("Description"))
				item.setDescription(newValue);
			else if(colName.equals("Store Web Address"))
				item.setStoreURL(newValue);
			DBClient.updateGrocItem(item);
			updateGrocTable(Session.User());
			Stage s = (Stage)rootPane.getScene().getWindow();
			UIHelper.makeCustomAlert(s, "Changes saved.");
		}
		else
			showSecondary("LoginDesign.fxml");
	}
	
	@FXML
	private void merchColEditCommit(CellEditEvent<Merchant, String> e) {
		if(Session.isActive()) {
			Merchant merch = e.getRowValue();
			String newValue = e.getNewValue();
		
			@SuppressWarnings("unchecked")
			TableColumn<Merchant, String> c = (TableColumn<Merchant, String>)e.getSource();
			String colName = c.getText();
			
			if(colName.equals("Merchant Name"))
				merch.setName(newValue);
			else if(colName.equals("URL"))
				merch.setURL(newValue);
			else if(colName.equals("Rank")) {
				if(!newValue.matches("\\d+")){
					alertUser("Please enter a valid number (1, 2, or 3) for the Rank.");
					return;
				}
				merch.setRank(newValue);
			}
			DBClient.updatePrefMerch(merch);
			updateGrocTable(Session.User());
			Stage s = (Stage)rootPane.getScene().getWindow();
			UIHelper.makeCustomAlert(s, "Changes saved.");
		}
		else
			showSecondary("LoginDesign.fxml");
	}
	
	private void alertUser(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Invalid input.");
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	protected void switchScene(String fxmlURL) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource(fxmlURL));
			Stage stage = (Stage) rootPane.getScene().getWindow();
			stage.setScene(new Scene(parent));
			stage.setMaximized(true);
			stage.show();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(!Session.isActive()) {
			showSecondary("LoginDesign.fxml");
			return;
		}
		updateGrocTable(Session.User());
		updateMerchTable(Session.User());
	}
}

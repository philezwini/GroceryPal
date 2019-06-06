package grocery_pal.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import grocery_pal.model.GroceryItem;
import grocery_pal.model.Merchant;
import grocery_pal.model.User;

public class DBClient{
	private static Connection con;
	public static int logIn(String username, String password){
		try {
			return validateUser(username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return - 1;
	}
	
	private static int validateUser(String username, String password)throws SQLException{
		String query = "select * from user where Username = '" + username + "' and Password = '" + password + "' and isActive = 1";
		ResultSet rs = getResults(query);
		int id = -1;
		if(!rs.isBeforeFirst())
			return id; 
		
		while(rs.next())
			id = (rs.getInt("Id"));
			
		if(!con.isClosed())
			con.close();
			
		return id;
	}
	
	public static ArrayList<GroceryItem> getGrocItems(int userId){
		ArrayList<GroceryItem> data = new ArrayList<>();
		try {
			ResultSet rs = getResults("select * from grocery_item where User_Id = " + userId + " and isActive = 1 and Status = 'current'");
			if(!rs.isBeforeFirst())
				return null;
	
			//rs.beforeFirst(); //Make sure the reader always starts before the first element of the set.
			while(rs.next())
			{
				int id = Integer.parseInt(rs.getString("Id"));
				String name = rs.getString("Name");
				String description = rs.getString("Description");
				String status = rs.getString("Status");
				String storeURL = rs.getString("Store_URL");
				GroceryItem item = new GroceryItem(id, userId, name, description, status, storeURL);
				data.add(item);
			}
			return data;
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<GroceryItem> getHGrocItems(int userId){
		ArrayList<GroceryItem> data = new ArrayList<>();
		try {
			ResultSet rs = getResults("select * from grocery_item where User_Id = " + userId + " and isActive = 1 and Status != 'current'");
			if(!rs.isBeforeFirst()) //Make sure the reader did not return an empty list.
				return null;
	
			while(rs.next())
			{
				int id = Integer.parseInt(rs.getString("Id"));
				String name = rs.getString("Name");
				String description = rs.getString("Description");
				String status = rs.getString("Status");
				String storeURL = rs.getString("Store_URL");
				GroceryItem item = new GroceryItem(id, userId, name, description, status, storeURL);
				data.add(item);
			}
			return data;
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}


	public static ArrayList<Merchant> getPrefMerchs(int userId)
	{
		ArrayList<Merchant> data = new ArrayList<>();
		try {
			ResultSet rs = getResults("select * from merchant where User_Id = " + userId + " and isActive = 1 and Status = 'preferred'");
			if(!rs.isBeforeFirst())
				return null;
			
			while(rs.next())
			{
				int id = Integer.parseInt(rs.getString("Id"));
				String name = rs.getString("Name");
				String URL = rs.getString("URL");
				String rank = rs.getString("Rank");
				String status = rs.getString("Status");
				Merchant m = new Merchant(id, userId, name, URL, rank, status);
				data.add(m);
			}
			return data;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public static void updateUser(User user) {
		String query = "update user set Username = '" + user.getUsername() + "', Password = '" + user.getPassword() + "' where Id = " + user.getId();
		makeUpdate(query);
	}
	
	public static void updateGrocItem(GroceryItem item) {
		String query = "update grocery_item set Name = '" + item.getName() + "', Description = '" + item.getDescription() + "', Status = '" + item.getStatus() + "', Store_URL = '" + item.getStoreURL() + "' where Id = " + item.getId();
		makeUpdate(query);
	}
	
	public static void updatePrefMerch(Merchant merchant) {
		String query = "update merchant set Name = '" + merchant.getName() + "', URL = '" + merchant.getURL() + "', Rank = '" + merchant.getRank() + "', Status = '" + merchant.getStatus() + "' where Id = " + merchant.getId();
		makeUpdate(query);
	}
	
	public static void addUser(User user)
	{	
		String query = "insert into user (Username, Password) values ('" + user.getUsername() + "', '" + user.getPassword() + "')";
		makeUpdate(query);
	}
	
	public static void addGrocItem(GroceryItem item)
	{
		String query = "insert into grocery_item (Name, Description, User_Id, Status, Store_URL) values ('" + item.getName() + "', '" + item.getDescription() + "', '" + item.getUID() + "', '" + item.getStatus() + "', '" + item.getStoreURL() + "')";
		makeUpdate(query);
	}
	
	public static void addPrefMerch(Merchant merchant)
	{
		String query = "insert into merchant (User_Id, URL, Name, Rank, Status) values ('" + merchant.getUID() + "', '" + merchant.getURL() + "', '" + merchant.getName() + "', '" + merchant.getRank() + "', '" + merchant.getStatus() + "')";
		makeUpdate(query);
	}
	
	public static void removeUser(User user) {
		String query = "update user set isActive = 0 where Id = " + user.getId();
		makeUpdate(query);
	}
	
	public static void removeGrocItem(GroceryItem item) {
		String query = "update grocery_item set isActive = 0 where Id = " + item.getId();
		makeUpdate(query);
	}
	
	public static void removePrefMerchant(Merchant merchant) {
		String query = "update merchant set isActive = 0 where Id = " + merchant.getId();
		makeUpdate(query);
	}
	
	public static void addHGrocItem(GroceryItem item) {
		String query = "update grocery_item set Store_URL = '" + item.getStoreURL() + "', Status = '" + item.getStatus() + "' where Name = '" + item.getName() + "'";
		makeUpdate(query);
	}
	
	private static void makeUpdate(String query)
	{
		Connection con;
		try 
		{
			con = DriverManager.getConnection(DBConnection.connectionString(), DBConnection.username(), DBConnection.password());
			Statement stmnt = con.createStatement();
			stmnt.executeUpdate(query);
			con.close();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	private static ResultSet getResults(String query) throws SQLException
	{
			con = DriverManager.getConnection(DBConnection.connectionString(), DBConnection.username(), DBConnection.password());
			Statement stmnt = con.createStatement();
			ResultSet rs = stmnt.executeQuery(query);
			return rs;
	}
}

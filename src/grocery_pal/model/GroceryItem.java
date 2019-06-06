package grocery_pal.model;

public class GroceryItem {
	private int id;
	private int UID;
	private String name;
	private String description;
	private String status;
	private String storeURL;
	
	public GroceryItem(int UID, String name, String description, String status, String storeURL) {
		this.UID = UID;
		this.name = name;
		this.description = description;
		this.storeURL = storeURL;
		this.status = status;
	}
	
	public GroceryItem(int id, int UID, String name, String description, String status, String storeURL) {
		this.id = id;
		this.UID = UID;
		this.name = name;
		this.description = description;
		this.storeURL = storeURL;
		this.status = status;
	}
	
	public GroceryItem() {}

	public int getUID() {
		return UID;
	}

	public void setUID(int uID) {
		UID = uID;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStoreURL() {
		return storeURL;
	}

	public void setStoreURL(String storeURL) {
		this.storeURL = storeURL;
	}

	@Override
	public String toString() {
		return "GroceryItem [id=" + id + ", UID=" + UID + ", name=" + name + ", description=" + description
				+ ", status=" + status + ", storeURL=" + storeURL + "]";
	}
	
}

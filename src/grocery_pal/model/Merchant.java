package grocery_pal.model;

public class Merchant{
	private int id;
	private int UID;
	private String name;
	private String URL;
	private String status;
	private String rank;
	
	public Merchant(int UID, String name, String URL, String rank, String status) {
		this.UID = UID;
		this.name = name;
		this.URL = URL;
		this.rank = rank;
		this.status = status;
	}
	
	public Merchant(int id, int UID, String name, String URL, String rank, String status) {
		this.id = id;
		this.UID = UID;
		this.name = name;
		this.URL = URL;
		this.rank = rank;
		this.status = status;
	}
	
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

	public String getURL() {
		return URL;
	}

	public void setURL(String URL) {
		this.URL = URL;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Merchant [id=" + id + ", UID=" + UID + ", name=" + name + ", URL=" + URL + "]";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}
}

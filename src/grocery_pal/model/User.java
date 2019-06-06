package grocery_pal.model;

public class User {
	private String username;
	private String password;
	private int id;
	public User(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
	
	public User(int id, String username, String password)
	{
		this.id = id;
		this.username = username;
		this.password = password;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public void setUsername(String name)
	{
		this.username = name;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
}

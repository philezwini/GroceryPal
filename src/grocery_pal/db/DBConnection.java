package grocery_pal.db;

public final class DBConnection {
	private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/gp_db?useSSL=false";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "jesusjesusjesus";
	
	public static String username()
	{
		return USER_NAME;
	}
	
	public static String password()
	{
		return PASSWORD;
	}
	
	public static String connectionString()
	{
		return CONNECTION_STRING;
	}
}

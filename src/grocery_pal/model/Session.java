package grocery_pal.model;

import java.util.Hashtable;
import java.util.TimerTask;

public class Session extends TimerTask {
	private static final long MAX_SESSION_TIMEOUT = 3600000;
	private long startTime;
	private static User user;
	private static Hashtable<String, Object> variables;
	public Session(User u) {
		user = u;
		variables = new Hashtable<>();
		start();
	}
	
	public void start(){
		startTime = System.currentTimeMillis();
		System.out.println("Session started.");
	}
	
	public static void destroy() {
		user = null;
		System.out.println("Session destroyed.");
	}
	
	public static boolean isActive() {
		return user != null;
	}
	
	public static User User() {
		return user;
	}
	
	public static void add(String key, Object value) {
		variables.put(key, value);
	}
	
	public static Object get(String key) {
		return variables.get(key);
	}

	@Override
	public void run() {
		long currentTime = System.currentTimeMillis();
		if((currentTime - startTime) >= MAX_SESSION_TIMEOUT) {
			destroy();
			System.out.println("Session timed out.");
		}
	}
}

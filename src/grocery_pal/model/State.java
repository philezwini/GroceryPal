package grocery_pal.model;

import java.util.ArrayList;

public class State {
	private String URL;
	private int depthLevel;
	private ArrayList<String> text;
	public State(String URL, int depthLevel, ArrayList<String> text) {
		this.URL = URL;
		this.depthLevel = depthLevel;
		this.setText(text);
	}

	public String getURL() {
		return URL;
	}
	
	public void setURL(String uRL) {
		URL = uRL;
	}
	public int getDepthLevel() {
		return depthLevel;
	}
	
	public void setDepthLevel(int depthLevel) {
		this.depthLevel = depthLevel;
	}

	public ArrayList<String> getText() {
		return text;
	}

	public void setText(ArrayList<String> text) {
		this.text = text;
	}
}
package grocery_pal.adt;

import java.util.ArrayList;
import grocery_pal.model.State;

public class GraphNode {
	private GraphNode parent;
	private ArrayList<GraphNode> children;
	private State state;
	
	public GraphNode(GraphNode parent, State state) {
		this.parent = parent;
		this.children = new ArrayList<>();
		this.state = state;
	}

	public GraphNode getParent() {
		return parent;
	}

	public void setParent(GraphNode parent) {
		this.parent = parent;
	}

	public ArrayList<GraphNode> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<GraphNode> children) {
		this.children = children;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}

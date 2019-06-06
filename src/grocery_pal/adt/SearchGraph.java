package grocery_pal.adt;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import grocery_pal.exception.MaxDepthReachedException;
import grocery_pal.model.GroceryItem;
import grocery_pal.model.State;
import grocery_pal.web.WebCrawler;

public class SearchGraph {
	private GraphNode root;
	private GroceryItem item;
	private static final int MAX_DEPTH_LEVEL = 3;
	
	private Queue<GraphNode> frontier;
	private ArrayList<GraphNode> explored;
	
	public SearchGraph(String URL, GroceryItem item) {
		State initState = new State(URL, 0, new ArrayList<>());
		GraphNode root = new GraphNode(null, initState);
		this.setRoot(root);
		this.item = item;
		frontier = new LinkedList<>();
		explored = new ArrayList<>();
		frontier.add(root);
	}
	
	public GroceryItem BFSSearch() {
		try {
			return BFSSearch(root);
		} catch (MaxDepthReachedException e) {
			return null;
		}
	}
	
	private GroceryItem BFSSearch(GraphNode node) throws MaxDepthReachedException {
		State s = node.getState();
		if(s.getDepthLevel() == MAX_DEPTH_LEVEL) {
			throw new MaxDepthReachedException(MAX_DEPTH_LEVEL);
		}
		if(frontier.isEmpty()) {
			return null;
		}
		frontier.remove();
		explored.add(node);
		if(goalTest(s)) {
			frontier.clear();
			return IDFSSearch(node);
		}
		expandNode(node);
		if(!frontier.isEmpty())
			return BFSSearch(frontier.remove());
		
		return null;
	}
	
	private boolean isNewURL(String url) {
		for(GraphNode n : frontier) {
			if(n.getState().getURL().equals(url))
				return false;
		}
		for(GraphNode n : explored) {
			if(n.getState().getURL().equals(url))
				return false;
		}
		return true;
	}

	private void expandNode(GraphNode node) throws MaxDepthReachedException {
		Queue<String> links =  WebCrawler.getLinks();
		if(links.isEmpty()) {
			return;
		}
		while(!links.isEmpty()) {
			String url = links.remove();
			if(!isNewURL(url)) {
				continue;
			}
			makeNewNode(url, node);
		}
	}
	
	private void makeNewNode(String url, GraphNode node) throws MaxDepthReachedException {
		ArrayList<String> text = WebCrawler.getFilteredText(item.getName());
		State state = new State(url, node.getState().getDepthLevel() + 1, text);
		
		if(state.getDepthLevel() == MAX_DEPTH_LEVEL)
			throw new MaxDepthReachedException(MAX_DEPTH_LEVEL);
		
		GraphNode child = new GraphNode(node, state);
		addChild(node, child);
		frontier.add(child);
	}
	
	private void addChild(GraphNode node, GraphNode child) {
		node.getChildren().add(child);
		//System.out.println("Child [URL: " + child.getState().getURL() + ", Depth: " + child.getState().getDepthLevel() + "] added to: " + child.getParent().getState().getURL());
	}

	private GroceryItem IDFSSearch(GraphNode node) throws MaxDepthReachedException {
		Queue<String> fLinks;
		fLinks = filterLinks(WebCrawler.getLinks());
		
		if(fLinks.isEmpty()) {
			return constructSolution(node);
		}
		for(String url : fLinks) {
			if(isNewURL(url)) {
				makeNewNode(url, node);
			}
		}
		if(!frontier.isEmpty())
			return IDFSSearch(frontier.remove());
		
		return null;
	}

	private Queue<String> filterLinks(Queue<String> links) {
		String word = item.getName();
		String[] tokens = item.getDescription().split("\\.\\s+");
		Queue<String> fLinks = new LinkedList<>();
		while(!links.isEmpty()) {
			String url = links.remove();
			if(!url.contains("#")) {
				if(url.contains(word)) {
					fLinks.add(url);
				}
				for(String s : tokens) {
					if(url.contains(s))
						fLinks.add(url);
				}
			}
		}
		return fLinks;
	}

	private GroceryItem constructSolution(GraphNode node) {
		GroceryItem newItem = new GroceryItem();
		newItem.setStoreURL(node.getState().getURL());
		newItem.setName(item.getName());
		newItem.setDescription(node.getState().getText().toString());
		return newItem;
	}

	private boolean goalTest(State state) {
		if(WebCrawler.crawl(state.getURL())) {
			String[] tokens = item.getDescription().split("\\.\\s+");
			if(WebCrawler.searchForWord(item.getName()))
				return true;
			else{
				for(String s : tokens) {
					if(WebCrawler.searchForWord(s))
						return true;
				}
			}
		}
		return false;
	}

	public GraphNode getRoot() {
		return root;
	}

	public void setRoot(GraphNode root) {
		this.root = root;
	}
}

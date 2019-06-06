package grocery_pal.web;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import grocery_pal.adt.SearchGraph;
import grocery_pal.model.GroceryItem;
import grocery_pal.model.Merchant;

public class Dispatcher {
	private SearchGraph graph;
	private ArrayList<Merchant> merchants;
	private static final String DEFAULT_URL = "http://www.pnp.co.za";
	
	public Dispatcher(ArrayList<Merchant> merchants) {
		this.merchants = merchants;
	}

	public GroceryItem findItem(GroceryItem item) {
		if((merchants != null) && !merchants.isEmpty()) {
			PriorityQueue<Merchant> mQueue = prepareData();
			while(!mQueue.isEmpty()) {
				Merchant m = mQueue.remove();
				System.out.println("Current Merchant URL: " + m.getURL());
				graph = new SearchGraph(m.getURL(), item);
				GroceryItem foundItem = graph.BFSSearch();
				if(foundItem != null) {
					foundItem.setStatus("Pending");
					return foundItem;
				}
				System.out.println("Item not found.");
			}
		}
		else {
			System.out.println("Current Merchant URL: " + DEFAULT_URL);
			graph = new SearchGraph(DEFAULT_URL, item);
			GroceryItem foundItem = graph.BFSSearch();
			if(foundItem != null) {
				foundItem.setStatus("Pending");
				return foundItem;
			}
		}
		System.out.println("Returnin null.");
		return null;
	}
	
	private PriorityQueue<Merchant> prepareData() {
		Comparator<Merchant> c = new Comparator<Merchant>() {
			@Override
			public int compare(Merchant arg0, Merchant arg1) {
				int r1 = Integer.parseInt(arg0.getRank());
				int r2 = Integer.parseInt(arg1.getRank());
				return r2 - r1;
			}
		};
		
		PriorityQueue<Merchant> queue = new PriorityQueue<>(c);
		for(Merchant m : merchants)
			queue.add(m);
		
		return queue;
	}
}

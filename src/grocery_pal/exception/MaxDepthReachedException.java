package grocery_pal.exception;

public class MaxDepthReachedException extends Throwable {
	private static final long serialVersionUID = 1L;
	private int maxDepth;
	public MaxDepthReachedException(int maxDepth) {
		this.maxDepth = maxDepth;
	}
	public String getMessage() {
		return "Maximum depth of search graph reached: " + maxDepth;
	}
}

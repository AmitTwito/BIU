public class Region {

	private double left;
	private double right;

	public Region(double left, double right) {
		this.left = left;
		this.right = right;
	}

	public double left() {
		return this.left;
	}

	public double right() {
		return this.right;
	}

	public boolean isContains(double x) {
		return x >= this.left && x < this.right;
	}



}

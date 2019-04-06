/**
 * The Region class represents a region object.
 * A Region object is built by a left and right values/points,
 * those are the edges of a zone (on the x-axis);
 *
 * @author Amit Twito
 * @since 5.4.19
 */
public class Region {

	//Members.
    private double left; //Left edge of the Region.
    private double right; //Right edge of the Region.

    //Constructors.

    /**
     * Constructor for the Region class.
     * Build a region from left and right values.
     *
     * @param left  Left edge of the Region.
     * @param right Right edge of the Region.
     */
    public Region(double left, double right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Returns the left edge value of the region.
     *
     * @return Left edge value of the region.
     */
    public double left() {
        return this.left;
    }

    /**
     * Returns the right edge value of the region.
     *
     * @return right edge value of the region.
     */
    public double right() {
        return this.right;
    }

    /**
     * Checks if the this region contains the given number between the left and right edges.
     *
     * @param x The number to check if its between the left and the right edges of the region.
     */
    public boolean isContains(double x) {

        //
        return this.left <= x && x < this.right;
    }


}

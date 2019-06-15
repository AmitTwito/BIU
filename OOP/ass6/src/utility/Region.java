package utility;

/**
 * The utility.Region class represents a region object.
 * A utility.Region object is built by a first and second values/points,
 * the first is the smaller than the second,
 * those are the edges of a zone (on the x-axis).
 * This object also helps to determine the hit place of the ball on the paddle.
 *
 * @author Amit Twito
 * @since 5.4.19
 */
public class Region {

    //Members.
    private double first; //Left edge of the utility.Region.
    private double second; //Right edge of the utility.Region.

    //Constructors.

    /**
     * Constructor for the utility.Region class.
     * Build a region from left and right values.
     *
     * @param first  Left edge of the utility.Region.
     * @param second Right edge of the utility.Region.
     * @throws Exception if both edges are equal.
     */
    public Region(double first, double second) throws Exception {
        if (first == second) {
            throw new Exception("The region edges can't be equal.");
        }
        this.first = first < second ? first : second;
        this.second = first > second ? first : second;
    }

    /**
     * Returns the left edge value of the region.
     *
     * @return Left edge value of the region.
     */
    public double first() {
        return this.first;
    }

    /**
     * Returns the right edge value of the region.
     *
     * @return right edge value of the region.
     */
    public double second() {
        return this.second;
    }

    /**
     * Checks if the this region contains the given number between the left and right edges.
     *
     * @param x The number to check if its between the left and the right edges of the region.
     * @return True if the region contains x, else false.
     */
    public boolean isContains(double x) {

        //Check if x is smaller (not equal) than the right edge because
        // otherwise the right edge ill be contained in 2 regions,
        // (except for the right-most region of the paddle).
        return this.first <= x && x < this.second;
    }


}

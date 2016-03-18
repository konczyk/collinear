import java.util.Comparator;

/**
 * Represent a point in the plane
 */
public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
    }

    public void drawTo(Point that) {
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public int compareTo(Point that) {
        if (that == null) {
            throw new NullPointerException("argument null");
        }

        if (this.y == that.y && this.x == that.x) {
            return 0;
        } else if (this.y < that.y || (this.y == that.y && this.x < that.x)) {
            return -1;
        } else {
            return 1;
        }
    }

    public double slopeTo(Point that) {
        if (that == null) {
            throw new NullPointerException("argument is null");
        }

        if (this.compareTo(that) == 0) {
            return Double.NEGATIVE_INFINITY;
        } else if (this.x == that.x) {
            return Double.POSITIVE_INFINITY;
        } else if (this.y == that.y) {
            return +0.0;
        } else {
            return (double) (that.y - this.y)/(that.x - this.x);
        }
    }

    public Comparator<Point> slopeOrder() {
        return new PointComparator();
    }

    private class PointComparator implements Comparator<Point> {

        @Override
        public int compare(Point p1, Point p2) {
            if (p1 == null || p2 == null) {
                throw new NullPointerException("argument is null");
            }

            double slope1 = slopeTo(p1);
            double slope2 = slopeTo(p2);

            if (slope1 > slope2) {
                return 1;
            } else if (slope1 < slope2) {
                return -1;
            } else {
                return 0;
            }
        }
    }

}
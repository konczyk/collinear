import java.util.Comparator;
import java.util.Objects;

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

        int xCompare = Double.compare(this.x, that.x);
        int yCompare = Double.compare(this.y, that.y);

        if (xCompare == 0 && yCompare == 0) {
            return 0;
        } else if (yCompare == -1 || (yCompare == 0 && xCompare == -1)) {
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

            int slopeCompare = Double.compare(slopeTo(p1), slopeTo(p2));
            if (slopeCompare == 1) {
                return 1;
            } else if (slopeCompare == -1) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point point = (Point) o;

        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}

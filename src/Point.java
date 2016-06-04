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

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public int compareTo(Point other) {
        if (other == null) {
            throw new NullPointerException("other point is null");
        }

        int xCompare = Double.compare(x, other.x);
        int yCompare = Double.compare(y, other.y);

        if (xCompare == 0 && yCompare == 0) {
            return 0;
        } else if (yCompare < 0 || (yCompare == 0 && xCompare < 0)) {
            return -1;
        } else {
            return 1;
        }
    }

    public double slopeTo(Point other) {
        if (other == null) {
            throw new NullPointerException("other point is null");
        }

        if (compareTo(other) == 0) {
            return Double.NEGATIVE_INFINITY;
        } else if (x == other.x) {
            return Double.POSITIVE_INFINITY;
        } else if (y == other.y) {
            return +0.0;
        } else {
            return (double) (other.y - y)/(other.x - x);
        }
    }

    public Comparator<Point> slopeOrder() {
        return new PointComparator();
    }

    private class PointComparator implements Comparator<Point> {

        @Override
        public int compare(Point p, Point q) {
            if (p == null) {
                throw new NullPointerException("first point is null");
            } else if (q == null) {
                throw new NullPointerException("second point is null");
            }

            int slopeCompare = Double.compare(slopeTo(p), slopeTo(q));
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

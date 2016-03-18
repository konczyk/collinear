import java.util.Comparator;

/**
 * Represent a point in the plane
 */
public class Point implements Comparable<Point> {

    public Point(int x, int y) {

    }

    public void draw() {
    }

    public void drawTo(Point that) {
    }

    public String toString() {
        return "";
    }

    @Override
    public int compareTo(Point that) {
        return 0;
    }

    public double slopeTo(Point that) {
        return 0.0;
    }

    public Comparator<Point> slopeOrder() {
        return new PointComparator();
    }

    private static class PointComparator implements Comparator<Point> {

        @Override
        public int compare(Point a, Point b) {
            return 0;
        }
    }

}

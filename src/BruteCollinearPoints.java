import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Examine four points at a time and check if the all lie
 * on the same line segment.
 */
public class BruteCollinearPoints {

    private final List<LineSegment> lineSegments = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException("argument is null");
        } else if (containsNull(points)) {
            throw new NullPointerException("argument contains null");
        }

        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);

        if (containsDuplicate(sortedPoints)) {
            throw new IllegalArgumentException("argument contains duplicates");
        }

        findLineSegments(sortedPoints);
    }

    private boolean containsNull(Point[] points) {
        for (Point p: points) {
            if (p == null) {
                return true;
            }
        }

        return false;
    }

    private boolean containsDuplicate(Point[] sortedPoints) {
        for (int i = 0; i < sortedPoints.length - 1; i++) {
            if (sortedPoints[i].compareTo(sortedPoints[i+1]) == 0) {
                return true;
            }
        }

        return false;
    }

    private void findLineSegments(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            Point p0 = points[i];
            Comparator<Point> slopeOrder = p0.slopeOrder();
            for (int j = i + 1; j < points.length; j++) {
                Point p1 = points[j];
                for (int k = j + 1; k < points.length; k++) {
                    Point p2 = points[k];
                    if (slopeOrder.compare(p1, p2) == 0) {
                        for (int l = k + 1; l < points.length; l++) {
                            Point p3 = points[l];
                            if (slopeOrder.compare(p2, p3) == 0) {
                                lineSegments.add(new LineSegment(p0, p3));
                            }
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }

}

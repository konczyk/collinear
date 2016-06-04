import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Examine four points at a time and check if they all lie
 * on the same line segment
 */
public class BruteCollinearPoints {

    private final List<LineSegment> lineSegments = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException("points are null");
        } else if (containsNull(points)) {
            throw new NullPointerException("points contain null");
        }

        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);

        if (containsDuplicate(sortedPoints)) {
            throw new IllegalArgumentException("points contain duplicates");
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

    private boolean interrupted() {
        return Thread.currentThread().isInterrupted();
    }

    private void findLineSegments(Point[] points) {
        int len = points.length;
        for (int i = 0; i < len && !interrupted(); i++) {
            Point p0 = points[i];
            Comparator<Point> slopeOrder = p0.slopeOrder();
            for (int j = i + 1; j < len && !interrupted(); j++) {
                Point p1 = points[j];
                for (int k = j + 1; k < len && !interrupted(); k++) {
                    Point p2 = points[k];
                    if (slopeOrder.compare(p1, p2) == 0) {
                        for (int l = k + 1; l < len && !interrupted(); l++) {
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

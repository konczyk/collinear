import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Examine four or more points at a time and check if they all lie
 * on the same line segment using sort-based solution
 */
public class FastCollinearPoints {

    private final List<LineSegment> lineSegments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException("points are null");
        } else if (containsNull(points)) {
            throw new NullPointerException("points contain nulls");
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

    private void findLineSegments(Point[] points) {

        for (int i = 0; i < points.length && !interrupted(); i++) {

            Comparator<Point> slopeOrder = points[i].slopeOrder();
            Arrays.sort(points, slopeOrder);

            int j = 2;
            while (j < points.length) {
                // find segments with a given slope with origin at points[0]
                int start = j-1;
                while (j < points.length
                        && slopeOrder.compare(points[j-1], points[j]) == 0) {
                    j++;
                }
                // add segments with >= 4 points, but avoid duplicates
                if (j-start >= 3 && points[0].compareTo(points[start]) < 0) {
                    lineSegments.add(new LineSegment(points[0], points[j-1]));
                }
                j++;
            }

            Arrays.sort(points);
        }
    }

    private boolean interrupted() {
        return Thread.currentThread().isInterrupted();
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }

}

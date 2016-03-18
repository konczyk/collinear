import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Examine four points at a time and check if the all lie
 * on the same line segment.
 */
public class BruteCollinearPoints {

    private final Point[] points;
    private List<LineSegment> lineSegments = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException("argument is null");
        }
        for (Point p: points) {
            if (p == null) {
                throw new NullPointerException("argument contains null");
            }
        }
        this.points = points;
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        lineSegments.clear();
        for (int i = 0; i < points.length; i++) {
            Point p0 = points[i];
            Comparator<Point> slopeCompare = p0.slopeOrder();
            for (int j = i + 1; j < points.length; j++) {
                Point p1 = points[j];
                for (int k = j + 1; k < points.length; k++) {
                    Point p2 = points[k];
                    if (slopeCompare.compare(p1, p2) == 0) {
                        for (int l = k + 1; l < points.length; l++) {
                            Point p3 = points[l];
                            if (slopeCompare.compare(p2, p3) == 0) {
                                Point[] collinear = new Point[]{
                                    p0, p1, p2, p3
                                };
                                Arrays.sort(collinear);
                                lineSegments.add(
                                    new LineSegment(collinear[0], collinear[3]));
                            }
                        }
                    }
                }
            }
        }

        return toArray();
    }

    private LineSegment[] toArray() {
        LineSegment[] segmentArray = new LineSegment[lineSegments.size()];
        for (int i = 0; i < lineSegments.size(); i++) {
            segmentArray[i] = lineSegments.get(i);
        }

        return segmentArray;
    }

}

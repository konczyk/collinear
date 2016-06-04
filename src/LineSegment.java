import java.util.Objects;

/**
 * Represent line segment in the plane
 */
public class LineSegment {

    private final Point p;
    private final Point q;

    public LineSegment(Point p, Point q) {
        if (p == null) {
            throw new NullPointerException("first point is null");
        } else if (q == null) {
            throw new NullPointerException("second point is null");
        }
        this.p = p;
        this.q = q;
    }

    public Point start() {
        return p;
    }

    public Point end() {
        return q;
    }

    public String toString() {
        return p + " -> " + q;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LineSegment that = (LineSegment) o;

        return Objects.equals(p, that.p) && Objects.equals(q, that.q);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p, q);
    }
}

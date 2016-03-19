import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class FastCollinearPointsTest {

    @Test(expected = NullPointerException.class)
    public void throwsExceptionWhenConstructorArgumentIsNull() {
        new FastCollinearPoints(null);
    }

    @Test(expected = NullPointerException.class)
    public void throwsExceptionWhenConstructorArgumentContainsNull() {
        new FastCollinearPoints(new Point[]{new Point(1, 1), null});
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenConstructorArgumentContainsDuplicate() {
        new FastCollinearPoints(new Point[]{new Point(1, 1), new Point(1, 1)});
    }

    @Test
    public void noSegments() {
        Point[] points = new Point[]{
            new Point(1, 1), new Point(1, 3), new Point(3, 1), new Point(3, 3),
            new Point(3, 6), new Point(4, 4), new Point(6, 1), new Point(6, 3)
        };
        FastCollinearPoints fcp = new FastCollinearPoints(points);

        assertThat(fcp.segments(), is(equalTo(new LineSegment[0])));
        assertThat(fcp.numberOfSegments(), is(equalTo(0)));
    }

    @Test
    public void oneSegment() {
        // (1,1) -> (3,3) -> (4,4) -> (5,5)
        Point[] points = new Point[]{
            new Point(3, 6), new Point(4, 4), new Point(6, 1), new Point(5, 5),
            new Point(1, 1), new Point(1, 3), new Point(3, 1), new Point(3, 3)};
        FastCollinearPoints fcp = new FastCollinearPoints(points);

        LineSegment ls = new LineSegment(new Point(1, 1), new Point(5, 5));
        assertThat(fcp.segments(), is(arrayContaining(ls)));
        assertThat(fcp.numberOfSegments(), is(equalTo(1)));
    }

    @Test
    public void perpendicularSegments() {
        // (3,1) -> (3,3) -> (3,4) -> (3,6)
        // (1,3) -> (3,3) -> (6,3) -> (8,3)
        Point[] points = new Point[]{
            new Point(8, 3), new Point(1, 3), new Point(3, 1), new Point(3, 3),
            new Point(3, 4), new Point(3, 6), new Point(6, 3), new Point(1, 1)};
        FastCollinearPoints fcp = new FastCollinearPoints(points);

        LineSegment l1 = new LineSegment(new Point(3, 1), new Point(3, 6));
        LineSegment l2 = new LineSegment(new Point(1, 3), new Point(8, 3));
        assertThat(fcp.segments(), is(arrayContaining(l1, l2)));
        assertThat(fcp.numberOfSegments(), is(equalTo(2)));
    }

}

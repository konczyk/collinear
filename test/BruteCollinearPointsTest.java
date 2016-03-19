import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class BruteCollinearPointsTest {

    @Test(expected = NullPointerException.class)
    public void throwsExceptionWhenConstructorArgumentIsNull() {
        new BruteCollinearPoints(null);
    }

    @Test(expected = NullPointerException.class)
    public void throwsExceptionWhenConstructorArgumentContainsNull() {
        new BruteCollinearPoints(new Point[]{new Point(1, 1), null});
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenConstructorArgumentContainsDuplicate() {
        new BruteCollinearPoints(new Point[]{new Point(1, 1), new Point(1, 1)});
    }

    @Test
    public void noSegments() {
        Point[] points = new Point[]{
            new Point(1, 1), new Point(1, 3), new Point(3, 1), new Point(3, 3),
            new Point(3, 6), new Point(4, 4), new Point(6, 1), new Point(6, 3)
        };
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);

        assertThat(bcp.segments(), is(equalTo(new LineSegment[0])));
        assertThat(bcp.numberOfSegments(), is(equalTo(0)));
    }

    @Test
    public void oneSegment() {
        // (1,1) -> (3,3) -> (4,4) -> (5,5)
        Point[] points = new Point[]{
            new Point(3, 6), new Point(4, 4), new Point(6, 1), new Point(5, 5),
            new Point(1, 1), new Point(1, 3), new Point(3, 1), new Point(3, 3)};
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);

        LineSegment ls = new LineSegment(new Point(1, 1), new Point(5, 5));
        assertThat(bcp.segments(), is(arrayContaining(ls)));
        assertThat(bcp.numberOfSegments(), is(equalTo(1)));
    }

    @Test
    public void perpendicularSegments() {
        // (3,1) -> (3,3) -> (3,4) -> (3,6)
        // (1,3) -> (3,3) -> (6,3) -> (8,3)
        Point[] points = new Point[]{
            new Point(8, 3), new Point(1, 3), new Point(3, 1), new Point(3, 3),
            new Point(3, 4), new Point(3, 6), new Point(6, 3), new Point(1, 1)};
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);

        LineSegment l1 = new LineSegment(new Point(3, 1), new Point(3, 6));
        LineSegment l2 = new LineSegment(new Point(1, 3), new Point(8, 3));
        assertThat(bcp.segments(), is(arrayContaining(l1, l2)));
        assertThat(bcp.numberOfSegments(), is(equalTo(2)));
    }

}

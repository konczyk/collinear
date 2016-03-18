import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.junit.Assert.assertThat;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class BruteCollinearPointsTest {

    @Test(expected = NullPointerException.class)
    public void throwsExceptionWhenConstructorArgumentIsNull() {
        new BruteCollinearPoints(null);
    }

    @Test(expected = NullPointerException.class)
    public void throwsExceptionWhenConstructorArgumentContainsNull() {
        new BruteCollinearPoints(new Point[]{new Point(1, 1), null});
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
        Point p, q;
        Point[] points = new Point[]{
            new Point(3, 6), new Point(4, 4), new Point(6, 1),
            q = new Point(5, 5), p = new Point(1, 1), new Point(1, 3),
            new Point(3, 1), new Point(3, 3)
        };
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);

        assertThat(bcp.segments(), is(arrayContaining(new LineSegment(p, q))));
        assertThat(bcp.numberOfSegments(), is(equalTo(1)));
    }

    @Test
    public void perpendicularSegments() {
        Point p1, q1, p2, q2;
        Point[] points = new Point[]{
            new Point(1, 1), p1 = new Point(1, 3), p2 = new Point(3, 1),
            new Point(3, 3), new Point(3, 4), q2 = new Point(3, 6),
            new Point(6, 3), q1 = new Point(8, 3)
        };
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);

        LineSegment l1 = new LineSegment(p1, q1);
        LineSegment l2 = new LineSegment(p2, q2);
        assertThat(bcp.segments(), is(arrayContaining(l1, l2)));
        assertThat(bcp.numberOfSegments(), is(equalTo(2)));
    }

}

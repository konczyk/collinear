import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class PointTest {

    @Test
    public void createPoint() {
        Point p = new Point(1, 2);

        assertThat(p.toString(), is("(1, 2)"));
    }

    @Test(expected = NullPointerException.class)
    public void throwsExceptionWhenComparingToNull() {
        new Point(1, 1).compareTo(null);
    }

    @Test
    @Parameters(method = "compareToParams")
    public void compareTo(int x0, int y0, int x1, int y1, int comparison) {
        Point p0 = new Point(x0, y0);
        Point p1 = new Point(x1, y1);

        assertThat(p0.compareTo(p1), is(comparison));
    }
    private Object[] compareToParams() {
        return new Object[]{
            new Object[]{1, 3, 1, 3, 0},
            // y0 < y1
            new Object[]{0, 2, 0, 3, -1},
            // y0 == y1, x0 < x1
            new Object[]{0, 2, 1, 2, -1},
            // y0 > y1
            new Object[]{0, 2, 1, 1, 1},
            // y0 == y1, x0 > x1
            new Object[]{3, 2, 1, 2, 1},
        };
    }

    @Test(expected = NullPointerException.class)
    public void throwsExceptionWhenCreatingSlopeWithNull() {
        new Point(1, 1).slopeTo(null);
    }

    @Test
    @Parameters(method = "slopeToParams")
    public void slopeTo(int x0, int y0, int x1, int y1, double slope) {
        Point p0 = new Point(x0, y0);
        Point p1 = new Point(x1, y1);

        assertThat(p0.slopeTo(p1), is(slope));
    }
    private Object[] slopeToParams() {
        return new Object[]{
            new Object[]{1, 3, 3, 1, -1},
            new Object[]{0, 0, 2, 1, 0.5},
            // horizontal line
            new Object[]{1, 2, 5, 2, +0.0},
            // vertical line
            new Object[]{1, 1, 1, 5, Double.POSITIVE_INFINITY},
            // same points
            new Object[]{1, 2, 1, 2, Double.NEGATIVE_INFINITY}
        };
    }

    @Test(expected = NullPointerException.class)
    @Parameters(method = "slopeOrderNullParams")
    public void throwsExceptionWhenSlopeOrderingWithNulls(Point p1, Point p2) {
        new Point(1, 1).slopeOrder().compare(p1, p2);
    }
    private Object[] slopeOrderNullParams() {
        return new Object[]{
            new Point[]{new Point(0,0), null},
            new Point[]{null, new Point(0,0)},
            new Point[]{null, null}
        };
    }

    @Test
    @Parameters(method = "slopeOrderParams")
    public void slopeOrder(int x1, int y1, int x2, int y2, int comparison) {
        Point p0 = new Point(1, 1);
        Point p1 = new Point(x1, y1);
        Point p2 = new Point(x2, y2);

        assertThat(p0.slopeOrder().compare(p1, p2), is(comparison));
    }
    private Object[] slopeOrderParams() {
        return new Object[]{
            new Object[]{1, 3, 1, 3, 0},
            new Object[]{3, 2, 2, 4, -1},
            new Object[]{2, 4, 5, 2, 1},
            // horizontal line vs vertical line
            new Object[]{1, 5, 5, 1, 1},
            new Object[]{5, 1, 1, 2, -1},
            // degenerate segment vs anything
            new Object[]{1, 1, 2, -10, -1},
            new Object[]{2, -5, 1, 1, 1}
        };
    }

}

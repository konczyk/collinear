import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class LineSegmentTest {

    @Test(expected = NullPointerException.class)
    @Parameters(method = "lineSegmentNullParams")
    public void throwsExceptionOnCreationWithNulls(Point p, Point q) {
        new LineSegment(p, q);
    }
    private Object[] lineSegmentNullParams() {
        return new Object[]{
            new Point[]{new Point(1,1), null},
            new Point[]{null, new Point(1,1)},
            new Point[]{null, null}
        };
    }

    @Test
    public void createLineSegment() {
        LineSegment ls = new LineSegment(new Point(0, 0), new Point(1, 1));

        assertThat(ls.toString(), is("(0, 0) -> (1, 1)"));
    }

}

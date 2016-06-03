import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class LineSegmentTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    @Parameters(method = "lineSegmentNullParams")
    public void throwsExceptionOnCreationWithNulls(Point p, Point q, String msg) {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(msg);

        new LineSegment(p, q);
    }
    private Object[] lineSegmentNullParams() {
        return new Object[]{
            new Object[]{new Point(1, 1), null, "second point is null"},
            new Object[]{null, new Point(1, 1), "first point is null"},
            new Object[]{null, null, "first point is null"}
        };
    }

    @Test
    public void constructLineSegment() {
        LineSegment ls = new LineSegment(new Point(0, 0), new Point(1, 1));

        assertThat(ls.toString(), is("(0, 0) -> (1, 1)"));
    }

}

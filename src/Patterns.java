import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import javax.swing.SwingUtilities;

/**
 * Client, that reads in or generates pairs of points and displays the collinear
 * ones
 */
public class Patterns {

    public static final int COMPONENT_MAX = 32768;
    private static final int MAX_GENERATED_POINTS = 100000;

    private final Random rand = new Random();

    @Parameter(
        names = {"--points-num", "-n"},
        description = "Number of generated points " +
                      "(1 - " + MAX_GENERATED_POINTS + ")",
        validateWith = PointsNumValidator.class)
    private int num;

    @Parameter(
        names = {"--brute-force", "-b"},
        description = "Use brute-force algorithm instead of the default fast one")
    private boolean brute = false;

    @Parameter(
        names = {"--help", "-h"},
        description = "Usage help",
        help = true)
    private boolean help = false;

    @Parameter(
        names = {"--stdin", "-"},
        description = "Read strings from stdin ")
    private boolean stdin = false;

    @Parameter(
        names = {"--gui", "-g"},
        description = "Run GUI Visualizer")
    private boolean gui = false;

    public static class PointsNumValidator implements IParameterValidator {
        @Override
        public void validate(String name, String value) throws ParameterException {
            String msg = "Parameter --points-num should be a positive integer " +
                         "between 1 and " + MAX_GENERATED_POINTS +
                         " (found " + value + ")";
            try {
                int n = Integer.parseInt(value);
                if (n < 1 || n > MAX_GENERATED_POINTS) {
                    throw new ParameterException(msg);
                }
            } catch (Exception e) {
                throw new ParameterException(msg);
            }
        }
    }

    public static void main(String[] args) {
        Patterns client = new Patterns();
        JCommander jc = new JCommander(client);
        jc.setProgramName("Patterns");

        try {
            jc.parse(args);
            client.validate();
            if (client.help || args.length == 0) {
                jc.usage();
                return;
            }
            client.run();
        } catch (ParameterException e) {
            System.out.println(e.getMessage());
        }
    }

    private void validate() throws ParameterException {
        if (stdin && num > 0) {
            throw new ParameterException(
                "Parameters --stdin and --points-num are mutually exclusive");
        }
    }

    private void run() {
        Point[] points;
        if (stdin) {
            try (Scanner sc = new Scanner(System.in)) {
                points = loadFromStdIn(sc);
            }
        } else {
            points = loadFromRandom();
        }

        if (gui) {
            final Point[] pts = points.clone();
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new Visualizer(pts, brute);
                }
            });
        } else {
            LineSegment[] segments;
            if (brute) {
                segments = new BruteCollinearPoints(points).segments();
            } else {
                segments = new FastCollinearPoints(points).segments();
            }

            for (LineSegment ls : segments) {
                System.out.println(ls);
            }
        }
    }

    private Point[] loadFromStdIn(Scanner sc) {
        num = sc.nextInt();
        Point[] points = new Point[num];
        for (int i = 0; i < num; i++) {
            points[i] = new Point(sc.nextInt(), sc.nextInt());
        }

        return points;
    }

    private Point[] loadFromRandom() {
        Set<Point> points = new HashSet<>();

        while (points.size() < num) {
            int x = rand.nextInt(COMPONENT_MAX + 1);
            int y = rand.nextInt(COMPONENT_MAX + 1);
            points.add(new Point(x, y));
        }

        return points.toArray(new Point[points.size()]);
    }
}

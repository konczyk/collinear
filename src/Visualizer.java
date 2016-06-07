import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class Visualizer extends JFrame {

    private static final int COLLINEAR_TIMEOUT = 60;
    private static final String TITLE = "collinear";
    private static final String SEARCH_INFO = "Searching for collinear points...";
    private static final String FOUND_INFO = "Found %d line segments";
    private static final String CANCEL_INFO = "Searching cancelled after "
                                                + "%ds timeout expired";

    private final Point[] points;
    private final boolean brute;
    private final Plane plane;
    private final JLabel statusLabel = new JLabel();

    private LineSegment[] segments = null;

    public Visualizer(Point[] points, boolean brute) {
        this.points = points;
        this.brute = brute;

        setTitle(TITLE);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));
        plane = new Plane();
        container.add(plane);
        add(container, BorderLayout.CENTER);
        add(makeStatus(), BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                plane.stopTimer();
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        plane.startTimer();
        new TimedCollinearTask().execute();
    }

    private JPanel makeStatus() {
        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setText(SEARCH_INFO);
        statusPanel.add(statusLabel);

        return statusPanel;
    }

    private class TimedCollinearTask extends SwingWorker<LineSegment[], Void> {
        @Override
        public LineSegment[] doInBackground()
                throws TimeoutException, InterruptedException, ExecutionException {

            SwingWorker<LineSegment[], Void> worker = new CollinearTask();
            worker.execute();

            try {
                return worker.get(COLLINEAR_TIMEOUT, TimeUnit.SECONDS);
            } catch (Exception e) {
                worker.cancel(true);
                throw e;
            }
        }
    }

    private class CollinearTask extends SwingWorker<LineSegment[], Void> {
        @Override
        public LineSegment[] doInBackground() {
            if (brute) {
                return new BruteCollinearPoints(points).segments();
            } else {
                return new FastCollinearPoints(points).segments();
            }
        }

        @Override
        protected void done() {
            try {
                draw(get());
            } catch (CancellationException e) {
                statusLabel.setText(String.format(CANCEL_INFO, COLLINEAR_TIMEOUT));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void draw(final LineSegment[] ls) {
        segments = ls;
        statusLabel.setText(String.format(FOUND_INFO, segments.length));
        plane.stopTimer();
        plane.repaint();
        for (LineSegment segment: segments) {
            System.out.println(segment);
        }
    }

    private class Plane extends JPanel implements ActionListener {
        private static final int DELAY = 3;
        private static final int DIM = 600;

        private int drawnPoints = 0;
        private Timer timer;

        public Plane() {
            setPreferredSize(new Dimension(DIM, DIM));
            setBackground(Color.WHITE);
        }

        public void startTimer() {
            timer = new Timer(DELAY, this);
            timer.start();
        }

        public void stopTimer() {
            if (timer != null) {
                timer.stop();
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (timer != null) {
                if (segments != null) {
                    drawSegments(g);
                    drawPoints(g, points.length);
                } else if (drawnPoints < points.length) {
                    drawPoints(g, drawnPoints++);
                }
            }
        }

        private void drawPoints(Graphics g, int pointsLimit) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setPaint(Color.BLACK);

            Point p;
            for (int i = 0; i < pointsLimit; i++) {
                p = points[i];
                double x = scale(p.x());
                double y = scale(Patterns.COMPONENT_MAX - p.y());
                g2d.fillRect((int) Math.round(x), (int) Math.round(y), 1, 1);
            }
        }

        private void drawSegments(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setPaint(Color.BLUE);

            for (LineSegment ls: segments) {
                double x0 = scale(ls.start().x());
                double y0 = scale(Patterns.COMPONENT_MAX - ls.start().y());
                double x1 = scale(ls.end().x());
                double y1 = scale(Patterns.COMPONENT_MAX - ls.end().y());
                g2d.draw(new Line2D.Double(x0, y0, x1, y1));
            }
        }

        private double scale(double p) {
            return p / Patterns.COMPONENT_MAX * (DIM - 1);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (drawnPoints < points.length) {
                repaint();
            } else {
                stopTimer();
            }
        }
    }
}

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private final SET<Point2D> set;

    public PointSET() {
        set = new SET<>();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public int size() {
        return set.size();
    }

    public void insert(Point2D p) {
        checkForNull(p);
        set.add(p);

    }

    public boolean contains(Point2D p) {
        checkForNull(p);

        return set.contains(p);
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        for (Point2D p : set)
            p.draw();
    }

    public Iterable<Point2D> range(RectHV rect) {
        checkForNull(rect);
        Queue<Point2D> points = new Queue<>();
        for (Point2D point : set) {
            if (rect.contains(point))
                points.enqueue(point);
        }
        return points;
    }

    public Point2D nearest(Point2D p) {
        checkForNull(p);
        Point2D nearest = null;
        double distanceToNearest = Double.POSITIVE_INFINITY;
        for (Point2D point : set) {
            double distance = p.distanceSquaredTo(point);
            if (distance >= distanceToNearest) continue;
            distanceToNearest = distance;
            nearest = point;
        }
        return nearest;
    }

    private void checkForNull(Object object) {
        if (object == null) throw new IllegalArgumentException();
    }

    public static void main(String[] args) {
        PointSET ps = new PointSET();
        ps.insert(new Point2D(0.3, 0.5));
        ps.insert(new Point2D(0.1, 0.4));
        ps.insert(new Point2D(0.2, 0.8));
        ps.draw();
    }
}

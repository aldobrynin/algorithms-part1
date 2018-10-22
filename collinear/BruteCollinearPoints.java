/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> segments = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        if (sortedPoints.length == 0 || sortedPoints[0] == null || hasDoubleOrNull(sortedPoints))
            throw new IllegalArgumentException();

        for (int i = 0; i < sortedPoints.length; i++) {
            Point p = sortedPoints[i];
            for (int j = i + 1; j < sortedPoints.length; j++) {
                Point q = sortedPoints[j];
                double slope1 = p.slopeTo(q);
                for (int k = j + 1; k < sortedPoints.length; k++) {
                    Point r = sortedPoints[k];
                    double slope2 = p.slopeTo(r);
                    if (Double.compare(slope1, slope2) != 0) continue;
                    Point end = null;
                    for (int h = k + 1; h < sortedPoints.length; h++) {
                        Point s = sortedPoints[h];
                        double slope3 = p.slopeTo(s);
                        if (Double.compare(slope1, slope3) != 0) continue;
                        end = s;
                    }
                    if (end != null)
                        segments.add(new LineSegment(p, end));
                }
            }
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        int n = segments.size();
        return segments.toArray(new LineSegment[n]);
    }

    private boolean hasDoubleOrNull(Point[] points) {
        for (int i = 1; i < points.length; i++)
            if (points[i] == null || points[i].compareTo(points[i - 1]) == 0)
                return true;
        return false;
    }
}

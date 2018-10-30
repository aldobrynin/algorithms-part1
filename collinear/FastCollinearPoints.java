/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> segments = new ArrayList<LineSegment>();

    public FastCollinearPoints(Point[] points) {
        if (points == null || points.length == 0 || hasNull(points))
            throw new IllegalArgumentException();

        Point[] copy = points.clone();
        Arrays.sort(copy);
        if (hasDouble(copy))
            throw new IllegalArgumentException();

        for (int i = 0; i < copy.length - 3; i++) {
            Arrays.sort(copy);
            Arrays.sort(copy, copy[i].slopeOrder());

            for (int first = 1, last = 2; last < copy.length; first = last, last++) {
                while (last < copy.length
                        && Double.compare(copy[0].slopeTo(copy[first]), copy[0].slopeTo(copy[last]))
                        == 0)
                    last++;

                if (last - first >= 3 && copy[0].compareTo(copy[first]) < 0)
                    segments.add(new LineSegment(copy[0], copy[last - 1]));
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

    private boolean hasDouble(Point[] points) {
        for (int i = 1; i < points.length; i++)
            if (points[i].compareTo(points[i - 1]) == 0)
                return true;
        return false;
    }

    private boolean hasNull(Point[] points) {
        for (int i = 0; i < points.length; i++)
            if (points[i] == null)
                return true;
        return false;
    }
}

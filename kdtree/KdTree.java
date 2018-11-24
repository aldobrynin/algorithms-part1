import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private Node root;
    private int size = 0;

    public void insert(Point2D point) {
        this.root = insert(root, root == null ? new RectHV(0, 0, 1, 1) : root.rect, point, true);
    }

    private Node insert(Node node, RectHV rect, Point2D value, boolean vertical) {
        if (node == null) {
            size++;
            return new Node(value, rect);
        }

        if (node.key.equals(value)) return node;
        RectHV newRect;
        if (vertical) {
            if (value.x() < node.key.x()) {
                if (node.leftBottom == null)
                    newRect = new RectHV(rect.xmin(), rect.ymin(), node.key.x(), rect.ymax());
                else newRect = node.leftBottom.rect;
                node.leftBottom = insert(node.leftBottom, newRect, value, false);
            }
            else {
                if (node.rightTop == null)
                    newRect = new RectHV(node.key.x(), rect.ymin(), rect.xmax(), rect.ymax());
                else newRect = node.rightTop.rect;
                node.rightTop = insert(node.rightTop, newRect, value, false);
            }
        }
        else {
            if (value.y() < node.key.y()) {
                if (node.leftBottom == null)
                    newRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.key.y());
                else newRect = node.leftBottom.rect;
                node.leftBottom = insert(node.leftBottom, newRect, value, true);
            }
            else {
                if (node.rightTop == null)
                    newRect = new RectHV(rect.xmin(), node.key.y(), rect.xmax(), rect.ymax());
                else newRect = node.rightTop.rect;
                node.rightTop = insert(node.rightTop, newRect, value, true);
            }
        }
        return node;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return this.size;
    }

    public boolean contains(Point2D p) {
        return contains(root, p, true);
    }

    private boolean contains(Node node, Point2D p, boolean vertical) {
        if (node == null) return false;
        int cmp;
        if (vertical) cmp = Double.compare(node.key.x(), p.x());
        else cmp = Double.compare(node.key.y(), p.y());
        if (cmp == 0) {
            if (vertical && Double.compare(node.key.y(), p.y()) == 0)
                return true;

        }
        if (cmp > 0)
            return contains(node.rightTop, p, !vertical);
        return contains(node.leftBottom, p, !vertical);
    }

    public void draw() {
        draw(root, true);
    }

    private void draw(Node node, boolean vertical) {
        if (node == null) return;

        StdDraw.setPenRadius();
        if (!vertical) {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.key.y(), node.rect.xmax(), node.key.y());
        }
        else {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.key.x(), node.rect.ymin(), node.key.x(), node.rect.ymax());
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        node.key.draw();
        draw(node.leftBottom, !vertical);
        draw(node.rightTop, !vertical);
    }

    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> points = new Queue<>();
        range(root, rect, points);
        return points;
    }

    private void range(Node node, RectHV rect, Queue<Point2D> points) {
        if (node == null) return;
        if (!node.rect.intersects(rect)) return;
        if (rect.contains(node.key)) points.enqueue(node.key);
        range(node.leftBottom, rect, points);
        range(node.rightTop, rect, points);
    }

    public Point2D nearest(Point2D p) {

        return nearest(root, p, null, true);
    }

    private Point2D nearest(Node node, Point2D queryPoint, Point2D nearestPoint,
                            boolean vertical) {
        if (node == null) return nearestPoint;

        if (nearestPoint != null
                && nearestPoint
                .distanceSquaredTo(queryPoint) < node.rect.distanceSquaredTo(queryPoint))
            return nearestPoint;

        if (nearestPoint == null ||
                node.key.distanceSquaredTo(queryPoint) < nearestPoint
                        .distanceSquaredTo(queryPoint))
            nearestPoint = node.key;


        if ((vertical && queryPoint.x() < node.key.x()) ||
                (!vertical && queryPoint.y() < node.key.y())
        ) {
            nearestPoint = nearest(node.leftBottom, queryPoint, nearestPoint, !vertical);
            nearestPoint = nearest(node.rightTop, queryPoint, nearestPoint, !vertical);
        }
        else {
            nearestPoint = nearest(node.rightTop, queryPoint, nearestPoint, !vertical);
            nearestPoint = nearest(node.leftBottom, queryPoint, nearestPoint, !vertical);
        }

        return nearestPoint;
    }


    private static class Node {
        private final Point2D key;
        private final RectHV rect;
        private Node leftBottom;
        private Node rightTop;

        public Node(Point2D key, RectHV rect) {
            this.key = key;
            this.rect = rect;
        }
    }

    public static void main(String[] args) {
        KdTree kdtree = new KdTree();
        kdtree.insert(new Point2D(0.7, 0.2));
        kdtree.insert(new Point2D(0.5, 0.4));
        kdtree.insert(new Point2D(0.2, 0.3));
        kdtree.insert(new Point2D(0.4, 0.7));
        kdtree.insert(new Point2D(0.9, 0.6));
        StdDraw.clear();
        kdtree.draw();
        StdDraw.show();
    }
}

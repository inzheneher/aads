import java.util.Comparator;
import java.util.LinkedList;

public class FastCollinearPoints {

    private final LineSegment[] lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        int n = points.length;
        if (n < 4) {
            for (int i = 0; i < n; i++) {
                switch (n) {
                    case 1:
                        if (points[0] == null) throw new IllegalArgumentException();
                        else break;
                    case 2:
                        if (points[0] == null ||
                                points[1] == null ||
                                points[0].compareTo(points[1]) == 0)
                            throw new IllegalArgumentException();
                        else break;
                    case 3:
                        if (points[0] == null ||
                                points[1] == null ||
                                points[2] == null ||
                                points[0].compareTo(points[1]) == 0 ||
                                points[0].compareTo(points[2]) == 0 ||
                                points[1].compareTo(points[2]) == 0)
                            throw new IllegalArgumentException();
                        else break;
                }
            }
            lineSegments = new LineSegment[0];
        } else {
            LinkedList<Point> listPoints = new LinkedList<>();
            LinkedList<LineSegment> listLineSegments = new LinkedList<>();
            outerLoop:
            for (int i = 0; i < n - 2; i++) {
                if (i < 1 && points[i] == null) throw new IllegalArgumentException();
                for (int j = i + 1; j < n - 1; j++) {
                    if (j < 2 && points[j] == null || points[i].compareTo(points[j]) == 0)
                        throw new IllegalArgumentException();
                    for (int k = j + 1; k < n; k++) {
                        if (points[k] == null || points[i].compareTo(points[k]) == 0 || points[j].compareTo(points[k]) == 0)
                            throw new IllegalArgumentException();
                        if (Double.compare(points[i].slopeTo(points[j]), points[i].slopeTo(points[k])) == 0) {
                            listPoints.add(points[k]);
                        }
                    }
                    if (!listPoints.isEmpty()) {
                        listPoints.add(points[i]);
                        listPoints.add(points[j]);
                    }
                    if (listPoints.size() > 3) {
                        listPoints.sort(new SlopeOrder());
                        listLineSegments.add(new LineSegment(listPoints.getFirst(), listPoints.getLast()));
                    }
                    if (listPoints.size() == points.length) break outerLoop;
                    listPoints.clear();
                }
            }
            lineSegments = new LineSegment[listLineSegments.size()];
            for (int i = 0; i < listLineSegments.size(); i++) {
                lineSegments[i] = listLineSegments.get(i);
            }
        }
    }

    public static void main(String[] args) {
        Point[] points = {
                new Point(4000, 30000),
                new Point(3500, 28000),
                new Point(3000, 26000),
                new Point(2000, 22000),
                new Point(1000, 18000),
                new Point(13000, 9000),
                new Point(23000, 16000),
                new Point(28000, 13500),
                new Point(28000, 5000),
                new Point(28000, 1000),
        };

        FastCollinearPoints bcp = new FastCollinearPoints(points);

        for (LineSegment lineSegment : bcp.segments()) {
            System.out.println(lineSegment);
        }
        System.out.println(bcp.numberOfSegments());
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        int n = lineSegments.length;
        LineSegment[] tempArr = new LineSegment[n];
        System.arraycopy(lineSegments, 0, tempArr, 0, n);
        return tempArr;
    }

    private static class SlopeOrder implements Comparator<Point> {
        @Override
        public int compare(Point point1, Point point2) {
            if (point1.compareTo(point2) > 0) return 1;
            else if (point1.compareTo(point2) < 0) return -1;
            return 0;
        }
    }
}
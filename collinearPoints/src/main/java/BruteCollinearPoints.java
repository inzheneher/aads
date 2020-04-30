import java.util.LinkedList;

public class BruteCollinearPoints {

    private final LineSegment[] lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        LinkedList<LineSegment> list = new LinkedList<>();
        if (points.length < 4) throw new IllegalArgumentException();
        for (int i = 0; i < points.length - 3; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            if (points[i].slopeTo(points[i + 1]) == points[i].slopeTo(points[i + 2]) &&
                    points[i].slopeTo(points[i + 1]) == points[i].slopeTo(points[i + 3])) {
                Point[] tempPointsArray = {points[i], points[i + 1], points[i + 2], points[i + 3]};
                Point[] minAndMaxPointsArray = getMinAndMaxPoints(tempPointsArray);
                list.add(new LineSegment(minAndMaxPointsArray[0], minAndMaxPointsArray[1]));
            }
        }
        lineSegments = new LineSegment[list.size()];
        for (int i = 0; i < list.size(); i++) {
            lineSegments[i] = list.get(i);
        }
    }

    public static void main(String[] args) {

        Point[] points = {
                new Point(10000, 0),
                new Point(0, 10000),
                new Point(3000, 7000),
                new Point(7000, 3000),
                new Point(20000, 21000),
                new Point(3000, 4000),
                new Point(14000, 15000),
                new Point(6000, 7000)
        };

        BruteCollinearPoints bcp = new BruteCollinearPoints(points);

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

    private Point[] getMinAndMaxPoints(Point[] points) {
        Point[] tempArr = new Point[points.length];
        System.arraycopy(points, 0, tempArr, 0, points.length);
        Point[] minMaxArray = new Point[2];
        Point min = tempArr[0];
        Point max = tempArr[3];
        for (int i = 1; i < tempArr.length; i++) {
            if (min.compareTo(tempArr[i]) > 0) {
                min = tempArr[i];
                tempArr[i] = tempArr[i - 1];
            }
            if (max.compareTo(tempArr[i]) < 0) max = tempArr[i];
        }
        minMaxArray[0] = min;
        minMaxArray[1] = max;
        return minMaxArray;
    }
}
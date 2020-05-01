import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

public class BruteCollinearPoints {

    private final LineSegment[] lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        if (isArrayHasDuplicates(points)) throw new IllegalArgumentException();
        LinkedList<LineSegment> listLineSegment = new LinkedList<>();
        LinkedList<PointsKeeper> listPoints = new LinkedList<>();
        if (points.length < 4) lineSegments = new LineSegment[0];
        else {
            for (int i = 0; i < points.length - 3; i++) {
                if (points[i] == null) throw new IllegalArgumentException();
                Point[] tempPointsArray = {points[i], points[i + 1], points[i + 2], points[i + 3]};
                if (Double.compare(points[i].slopeTo(points[i + 1]), points[i].slopeTo(points[i + 2])) == 0 &&
                        Double.compare(points[i].slopeTo(points[i + 1]), points[i].slopeTo(points[i + 3])) == 0) {
                    Point[] minAndMaxPointsArray = getMinAndMaxPoints(tempPointsArray);
                    listPoints.add(new PointsKeeper(minAndMaxPointsArray[0], minAndMaxPointsArray[1]));
                    listLineSegment.add(new LineSegment(minAndMaxPointsArray[0], minAndMaxPointsArray[1]));
                }
            }
            Arrays.sort(points);
            for (int i = 0; i < points.length - 3; i++) {
                if (points[i] == null) throw new IllegalArgumentException();
                Point[] tempPointsArray = {points[i], points[i + 1], points[i + 2], points[i + 3]};
                if (Double.compare(points[i].slopeTo(points[i + 1]), points[i].slopeTo(points[i + 2])) == 0 &&
                        Double.compare(points[i].slopeTo(points[i + 1]), points[i].slopeTo(points[i + 3])) == 0) {
                    Point[] minAndMaxPointsArray = getMinAndMaxPoints(tempPointsArray);
                    listPoints.add(new PointsKeeper(minAndMaxPointsArray[0], minAndMaxPointsArray[1]));
                    listLineSegment.add(new LineSegment(minAndMaxPointsArray[0], minAndMaxPointsArray[1]));
                }
            }
            for (int i = 0; i < listLineSegment.size() - 1; i++) {
                if (listPoints.get(i).equals(listPoints.get(i + 1))) {
                    listPoints.remove(i);
                    listLineSegment.remove(i);
                }
            }
            lineSegments = new LineSegment[listLineSegment.size()];
            for (int i = 0; i < listLineSegment.size(); i++) {
                lineSegments[i] = listLineSegment.get(i);
            }
        }
    }

    public static void main(String[] args) {

        Point[] points = {
                new Point(1158, 19504),
                new Point(5496, 19504),
                new Point(9746, 19504),
                new Point(7497, 19504),
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

    private boolean isArrayHasDuplicates(Point[] points) {
        int n = points.length;
        if (n == 0) return false;
        int counter = 0;
        Point[] tempArr = new Point[n];
        System.arraycopy(points, 0, tempArr, 0, n);
        for (Point point : tempArr) if (point == null) throw new IllegalArgumentException();
        Arrays.sort(tempArr);
        for (int i = 0; i < n - 1; i++) {
            if (tempArr[i].compareTo(tempArr[i + 1]) == 0) counter++;
        }
        if (tempArr[n - 1] == null) throw new IllegalArgumentException();
        return counter > 0;
    }

    private static class PointsKeeper {
        private final Point p1;
        private final Point p2;

        public PointsKeeper(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PointsKeeper that = (PointsKeeper) o;
            return p1.equals(that.p1) &&
                    p2.equals(that.p2);
        }

        @Override
        public int hashCode() {
            return Objects.hash(p1, p2);
        }
    }
}
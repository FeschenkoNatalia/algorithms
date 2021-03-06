package geometry.convexhull;

import geometry.dto.Point;
import geometry.dto.Segment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static geometry.utils.VectorOperations.diff;
import static geometry.utils.VectorOperations.vectProduct;

public class GrahamScan {

  /**
   * Return a convex hull of a set of verteses
   * with using Gramah scan.
   *
   * @param points list of verteses
   * @return convex Hull
   */
  public static List<Segment> convexHull(List<Point> points) {

    LinkedList<Segment> convHull = new LinkedList();

    Point minP = Collections.min(points, (Point p1, Point p2) -> Double.compare(p1.y(), p2.y()));

    List<Point> sorted = new ArrayList(points);
    sorted.sort((Point p1, Point p2) ->
        -Double.compare(new Segment(minP, p1).atan(), new Segment(minP, p2).atan()));

    sorted.remove(minP);
    convHull.add(new Segment(minP, sorted.get(0)));

    for (int i = 1; i < sorted.size(); i++) {
      Point top = sorted.get(i);

      while (ccw(convHull.peekLast(), top)) {
        convHull.removeLast();
      }
      convHull.add(new Segment(convHull.peekLast().end(), top));
    }
    convHull.add(new Segment(convHull.peekLast().end(), minP));

    return convHull;
  }

  /**
   * Three verteses are a counter-clockwise turn if ccw > 0, clockwise if
   * ccw < 0, and collinear if ccw = 0 because ccw is a determinant that
   * gives twice the signed  area of the triangle formed by p1, p2 and p3.
   *
   * @param s base segment
   * @param p point
   * @return true if the segment and point make counter-clockwise turn
   */
  private static boolean ccw(Segment s, Point p) {
    return vectProduct(diff(s.start(), s.end()), diff(s.start(), p)) < 0;
  }

}

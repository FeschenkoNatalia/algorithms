package geometry.triangulation;

import geometry.dto.Point;
import geometry.dto.Triangle;

import java.util.ArrayList;
import java.util.List;

import static geometry.utils.VectorOperations.length;
import static geometry.utils.VectorOperations.onSegment;


public class IncrementDelaunay extends AbstractTriangulation {

  public IncrementDelaunay(List<Point> points) {
    super(points);
  }

  @Override
  protected void triangulate(List<Point> points) {

    Triangle base = addBaseTriangle(points);

    Point[] baseVerteses = new Point[]{base.vertex(0), base.vertex(1), base.vertex(2)};

    for (Point p : points) {

      List<Triangle> oldTs = this.pointTriangles(p);
      List<Triangle> newTs = new ArrayList();

      for (Triangle To : oldTs) {

        removeTriangle(To);

        for (int i = 0; i < 3; i++) {
          if (onSegment(To.side(i), p)) continue;
          Triangle Tn = new Triangle(To.vertex(i), To.vertex(i + 1), p);
          addTriangle(Tn);
          newTs.add(Tn);
        }
      }

      for (Triangle T : newTs) improveTriangles(T);
    }

    for (Point v : baseVerteses) {
      List<Triangle> Ts = this.pointTriangles(v);
      for (Triangle T : Ts) {
        this.removeTriangle(T);
      }
    }

  }


  protected Triangle addBaseTriangle(List<Point> points) {

    double max_x = points.get(0).x(), min_x = points.get(0).x(),
        max_y = points.get(0).y(), min_y = points.get(0).y();

    for (Point p : points) {
      max_x = p.x() > max_x ? p.x() : max_x;
      max_y = p.y() > max_y ? p.y() : max_y;
      min_x = p.x() < min_x ? p.x() : min_x;
      min_y = p.y() < min_y ? p.y() : min_y;
    }

    double R = 1.5 * length(new Point(max_x - min_x, max_y - min_y));
    Point c = new Point(0.50d * (max_x + min_x), 0.50d * (max_y + min_y));

    Point A = new Point(c.x() - Math.sqrt(3.00d) * R, c.y() - R),
        B = new Point(c.x() + Math.sqrt(3.00d) * R, c.y() - R),
        C = new Point(c.x(), c.y() + 2.00d * R);

    Triangle T = new Triangle(A, B, C);
    addTriangle(T);

    return T;
  }

  protected void improveTriangles(Triangle T) {

    if (!contains(T)) return;

    for (int i = 0; i < 3; i++) {
      if (canImprove(T, i)) {
        List<Triangle> Ts = flip(T, i);
        improveTriangles(Ts.get(0));
        improveTriangles(Ts.get(1));
        break;
      }
    }
  }
}
package geometry.triangulation;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import geometry.dto.Point;
import geometry.dto.Segment;
import geometry.dto.Triangle;

import java.util.List;

import static geometry.dto.Triangle.triangle;
import static geometry.utils.VectorOperations.length;
import static geometry.utils.VectorOperations.onSegment;


public class IncrementDelaunay extends AbstractTriangulation implements Triangulation {

  public IncrementDelaunay(List<Point> points) {
    super(points);
  }

  @Override
  public Iterable<Triangle> triangulate(Iterable<Point> points) {
    List<Triangle> result = Lists.newArrayList();
    Triangle base = addBaseTriangle(points);

    for (Point p : points) {
      List<Triangle> oldTriangles = pointTriangles(p);
      List<Triangle> newTriangles = Lists.newArrayList();

      for (Triangle oldTriangle : oldTriangles) {
        removeTriangle(oldTriangle);

        for (Segment side : oldTriangle.sides()) {
          if (onSegment(side, p)) continue;
          Triangle newTriangle = triangle(side.start(), side.end(), p);
          addTriangle(newTriangle);
          newTriangles.add(newTriangle);
        }
      }

      newTriangles.forEach(this::improveTriangles);
    }

    for (Point v : base.vertexes()) {
      for (Triangle T : pointTriangles(v)) {
        removeTriangle(T);
      }
    }
    return result;
  }

  @Override
  protected void triangulate(List<Point> points) {

    Triangle base = addBaseTriangle(points);

    for (Point p : points) {
      List<Triangle> oldTriangles = pointTriangles(p);
      List<Triangle> newTriangles = Lists.newArrayList();

      for (Triangle oldTriangle : oldTriangles) {
        removeTriangle(oldTriangle);

        for (Segment side : oldTriangle.sides()) {
          if (onSegment(side, p)) continue;
          Triangle newTriangle = triangle(side.start(), side.end(), p);
          addTriangle(newTriangle);
          newTriangles.add(newTriangle);
        }
      }

      newTriangles.forEach(this::improveTriangles);
    }

    for (Point v : base.vertexes()) {
      for (Triangle T : pointTriangles(v)) {
        removeTriangle(T);
      }
    }
  }


  protected Triangle addBaseTriangle(Iterable<Point> points) {

    Point first = Iterables.getFirst(points, null);
    double max_x = first.x(), min_x = first.x(),
        max_y = first.y(), min_y = first.y();

    for (Point p : points) {
      max_x = p.x() > max_x ? p.x() : max_x;
      max_y = p.y() > max_y ? p.y() : max_y;
      min_x = p.x() < min_x ? p.x() : min_x;
      min_y = p.y() < min_y ? p.y() : min_y;
    }

    double R = 0.50d * length(new Point(max_x - min_x, max_y - min_y));
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
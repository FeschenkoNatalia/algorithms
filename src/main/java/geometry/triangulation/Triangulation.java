package geometry.triangulation;

import geometry.dto.Point;
import geometry.dto.Triangle;

public interface Triangulation {
  Iterable<Triangle> triangulate(Iterable<Point> points);
}

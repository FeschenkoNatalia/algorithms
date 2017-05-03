package geometry.dto;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import static geometry.dto.Segment.segment;
import static geometry.utils.Determinant.det;
import static geometry.utils.VectorOperations.diff;
import static geometry.utils.VectorOperations.vectProduct;


public class Triangle {

  private Point[] vertexes = new Point[3];

  private Triangle[] neighbors = new Triangle[3];

  private Point incenter = null;

  public Triangle(Point p_i, Point p_j, Point p_k) {

    vertexes[0] = p_i;

    if (vectProduct(diff(p_j, p_i), diff(p_k, p_j)) >= 0) {
      vertexes[1] = p_j;
      vertexes[2] = p_k;
    } else {
      vertexes[1] = p_k;
      vertexes[2] = p_j;
    }
  }

  public List<Segment> sides() {
    List<Segment> sides = new ArrayList<>();
    sides.add(segment(vertex(0), vertex(1)));
    sides.add(segment(vertex(1), vertex(2)));
    sides.add(segment(vertex(2), vertex(0)));
    return sides;
  }

  public Segment side(int i) {
    return new Segment(vertex(i), vertex(i + 1));
  }

  public Point vertex(int i) {
    return vertexes[index(i)];
  }

  public void neighbor(int i, Triangle T) {

    neighbors[index(i)] = T;
  }

  public Triangle neighbor(int i) {

    return neighbors[index(i)];
  }

  public boolean contains(Point p) {

    for (int i = 0; i <= vertexes.length; i++) {
      if (vectProduct(diff(vertex(i + 1), vertex(i)), diff(p, vertex(i))) < 0.d) {
        return false;
      }
    }
    return true;
  }


  public Point centroid() {

    if (incenter != null) return incenter;

    double xA = vertexes[0].x(), yA = vertexes[0].y(),
        xB = vertexes[1].x(), yB = vertexes[1].y(),
        xC = vertexes[2].x(), yC = vertexes[2].y();

    double[][] Mx = {{yA, xA * xA + yA * yA, 1.0},
        {yB, xB * xB + yB * yB, 1.0},
        {yC, xC * xC + yC * yC, 1.0}};
    double[][] My = {{xA, xA * xA + yA * yA, 1.0},
        {xB, xB * xB + yB * yB, 1.0},
        {xC, xC * xC + yC * yC, 1.0}};
    double[][] M = {{xA, yA, 1.0},
        {xB, yB, 1.0},
        {xC, yC, 1.0}};
    double Dx = det(Mx, 3), Dy = det(My, 3), D = det(M, 3);

    return new Point(-0.5 * Dx / D, 0.5 * Dy / D);
  }

  private int index(int i) {
    int r = i % 3;
    return (r >= 0) ? r : r + 3;
  }

  public List<Point> vertexes() {
    return Lists.newArrayList(vertexes[0], vertexes[1], vertexes[2]);
  }

  public static Triangle triangle(Point start, Point end, Point p) {
    return new Triangle(start, end , p);
  }
}
package geometry.utils;

import edu.princeton.cs.introcs.Draw;
import geometry.dto.Point;
import geometry.dto.Segment;

import java.util.List;


public class DrawHelper {

  public Draw image;

  public DrawHelper(Draw imgae) {
    this.image = imgae;
  }

  public void draw(Segment segment) {
    image.line(segment.startX(), segment.startY(), segment.endX(), segment.endY());
  }

  public void draw(Iterable<Segment> segments) {

    for (Segment s : segments) {
      draw(s);
    }
  }

  public void draw(Point point, double radius) {

    image.filledCircle(point.x(), point.y(), radius);
  }

  public void draw(List<Point> points, double radius) {

    for (Point p : points) {
      draw(p, radius);
    }
  }

}
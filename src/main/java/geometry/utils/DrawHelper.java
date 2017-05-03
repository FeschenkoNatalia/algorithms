package geometry.utils;

import edu.princeton.cs.introcs.Draw;
import geometry.dto.Point;
import geometry.dto.Segment;

import java.util.List;

public class DrawHelper {

  public static void drawSegment(Draw draw, Segment segment) {
    draw.line(segment.startX(), segment.startY(), segment.endX(), segment.endY());
  }

  public static void drawSegments(Draw draw, Iterable<Segment> segments) {
    for (Segment s : segments) {
      drawSegment(draw, s);
    }
  }

  public static void drawPoints(Draw draw, List<Point> points, double radius) {

    for (Point p : points) {
      draw.filledCircle(p.x(), p.y(), radius);
    }
  }

}

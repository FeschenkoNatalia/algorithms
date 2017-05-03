package graph;

import edu.princeton.cs.introcs.Draw;
import edu.princeton.cs.introcs.StdRandom;
import geometry.dto.Point;
import geometry.dto.Segment;
import geometry.dto.Triangle;
import geometry.triangulation.IncrementDelaunay;
import geometry.utils.DrawHelper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static geometry.dto.Segment.segment;

public class PrimTest {

  public static void main(String[] args) {

    Prim prim = new Prim(21);

    List<Point> points = new ArrayList<>();
    Set<Segment> segments = new HashSet<>();

    for (int i = 0; i < 20; i++) {
      points.add(Point.point(StdRandom.uniform(800), StdRandom.uniform(600)));
    }

    IncrementDelaunay triangulation = new IncrementDelaunay(points);
    for (Triangle triangle : triangulation.triangles()) {
      segments.addAll(triangle.sides());
    }

    for (Segment segment : segments) {
      prim.addEdge(points.indexOf(segment.start()), points.indexOf(segment.end()), segment.dist());
    }

    Set<Edge> mst = prim.getMST(0);
    Set<Segment> geoMst = new HashSet<>();

    for (Edge edge : mst) {
      geoMst.add(segment(points.get(edge.V()), points.get(edge.W())));
    }

    Draw draw = new Draw();
    draw.setCanvasSize(800, 600);
    draw.setXscale(0, 800);
    draw.setYscale(0, 600);
    draw.setPenColor(Color.RED);
    DrawHelper.drawSegments(draw, segments);
    draw.setPenColor(Color.BLACK);
    DrawHelper.drawSegments(draw, geoMst);

    draw.setPenColor(Color.BLUE);
    DrawHelper.drawPoints(draw, points, 5);

  }
}

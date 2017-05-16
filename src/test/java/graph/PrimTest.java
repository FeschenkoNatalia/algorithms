package graph;

import com.google.common.collect.Sets;
import edu.princeton.cs.introcs.Draw;
import edu.princeton.cs.introcs.StdRandom;
import geometry.dto.Point;
import geometry.dto.Segment;
import geometry.dto.Triangle;
import geometry.triangulation.AbstractTriangulation;
import geometry.triangulation.ConvexHullTriangulation;
import geometry.triangulation.DelaunayConvexHullTriangulation;
import geometry.triangulation.IncrementDelaunay;
import geometry.utils.DrawHelper;
import org.junit.Test;

import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static geometry.dto.Segment.segment;
import static graph.Edge.edge;

public class PrimTest {

  public static void main(String[] args) {

    Prim prim = new Prim();

    List<Point> points = new ArrayList<>();
    Set<Segment> segments = new HashSet<>();

    for (int i = 0; i < 200; i++) {
      points.add(Point.point(StdRandom.uniform(800), StdRandom.uniform(600)));
    }

    AbstractTriangulation triangulation = new IncrementDelaunay(points);
    for (Triangle triangle : triangulation.triangles()) {
      segments.addAll(triangle.sides());
    }

    Set<Edge> edges = Sets.newHashSet();
    for (Segment segment : segments) {
      edges.add(edge(points.indexOf(segment.start()), points.indexOf(segment.end()), segment.dist()));
    }
    prim.addEdges(edges);

    Set<Edge> mst = prim.getMST(0);
    Set<Segment> geoMst = Sets.newHashSet();

    for (Edge edge : mst) {
      geoMst.add(segment(points.get(edge.V()), points.get(edge.W())));
    }

    Draw draw = new Draw();
    draw.setCanvasSize(800, 600);
    draw.setXscale(0, 800);
    draw.setYscale(0, 600);
    draw.setPenColor(Color.LIGHT_GRAY);
    DrawHelper helper = new DrawHelper(draw);
    helper.draw(segments);
    draw.setPenColor(Color.RED);
    draw.setPenRadius(0.005);
    helper.draw(geoMst);

    draw.setPenColor(Color.BLUE);
    helper.draw(points, 5);

  }

  @Test
  public void gen() throws IOException {
    int N = 2500;
    StringBuilder buf = new StringBuilder();
    Set<Edge> edges = generateGraph(N);
    buf.append(String.format("%d %d\n", N, edges.size()));

    for (Edge edge : edges) {
      buf.append(String.format("%d %d %.0f\n", edge.V(), edge.W(), edge.weight()));
    }
    FileWriter writer = new FileWriter("/tmp/mst.in");
    writer.write(buf.toString());
    writer.close();

    Prim prim = new Prim().addEdges(edges);
    Set<Edge> minMst = Sets.newHashSet();
    int minSum = Integer.MAX_VALUE;

    for (int i = 0; i < N; i++) {
      Set<Edge> mst = prim.getMST(i);
      int sum = sum(mst);
      // System.out.printf("sum = %d\n", sum);
      if (sum < minSum) {
        minMst = mst;
        minSum = sum;
      }
    }
    System.out.printf("min = " + minSum);
    /*System.out.println("--- MST ---");
    for (Edge edge : minMst) {
      System.out.printf("%d %d %.0f\n", edge.V(), edge.W(), edge.weight());
    }*/
  }

  private int sum(Set<Edge> mst) {
    int res = 0;
    for (Edge edge : mst) {
      res += edge.weight();
    }
    return res;
  }


  Set<Edge> generateGraph(int N) {
    Set<Edge> res = Sets.newHashSet();

    for (int i = 0; i < N - 1; i++) {
      res.add(edge(i, i + 1, StdRandom.uniform(10, 50)));
    }

    for (int i = 0; i < N ; i++) {
      int v = StdRandom.uniform(N - 1);
      res.add(edge(v, StdRandom.uniform(v + 1, N), (double)StdRandom.uniform(10, 50)));
    }

    return res;
  }
}

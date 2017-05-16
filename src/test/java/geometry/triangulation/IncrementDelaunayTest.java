package geometry.triangulation;

import com.google.common.collect.Lists;
import edu.princeton.cs.introcs.Draw;
import geometry.dto.Point;
import geometry.dto.Segment;
import geometry.utils.DrawHelper;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import static geometry.dto.Point.point;

public class IncrementDelaunayTest {


  public static void main(String[] args) {

    List<Point> vertices = Lists.newArrayList();

    vertices.add(point(250, 200)); //3
    vertices.add(point(375, 25));  //1
    vertices.add(point(500, 150)); //2

//
    /*vertices.add(new Point(400, 225)); //4

    vertices.add(new Point(600, 300)); //5
    vertices.add(new Point(375, 300)); //6
    vertices.add(new Point(270, 300)); //7

    vertices.add(new Point(420, 350)); //8
    vertices.add(new Point(380, 450)); //9*/

    Collections.shuffle(vertices);

    AbstractTriangulation triangulation = new IncrementDelaunay(vertices);

    Draw draw = new Draw();
    DrawHelper helper = new DrawHelper(draw);

    draw.setCanvasSize(800, 600);
    draw.setXscale(0, 800);
    draw.setYscale(0, 600);

    draw.setPenColor(Color.RED);

    List<Segment> segments = triangulation.segments();
    System.out.println(segments);
    System.out.println(triangulation.triangles());

    helper.draw(segments);

    draw.setPenColor(Color.BLUE);
    helper.draw(vertices, 5.);
  }
}

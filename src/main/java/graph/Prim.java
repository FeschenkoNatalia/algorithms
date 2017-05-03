package graph;

import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import static graph.Edge.edge;

public class Prim {

  private int[][] G;
  private double[][] weight;
  private int[] size;
  private int N;

  public Prim(int N) {
    this.N = N;
    G = new int[N][N];
    weight = new double[N][N];
    size = new int[N];
  }

  public Prim addEdge(int V, int W, double weight) {
    this.weight[V][W] = weight;
    this.weight[W][V] = weight;
    G[V][size[V]++] = W;
    G[W][size[W]++] = V;
    return this;
  }


  public Set<Edge> getMST(int start) {
    Set<Edge> result = Sets.newHashSet();

    boolean[] marked = new boolean[size.length];
    Map<Integer, Edge> cut = Maps.newHashMap();
    PriorityQueue<Edge> distances = Queues.newPriorityQueue();
    Edge fakeEdge = edge(start, start, 0);
    distances.add(fakeEdge);


    while (!distances.isEmpty()) {
      Edge minEdge = distances.poll();
      Integer v = minEdge.W();
      marked[v] = true;
      result.add(minEdge);

      // iterate through adjacency list
      for (int i = 0; i < size[v]; i++) {
        int w = G[v][i];

        if (!marked[w]) {
          Edge e = cut.get(w);
          Edge newEdge = edge(v, w, weight[v][w]);

          if (e == null) { // insert new cut-edge
            distances.add(newEdge);
            cut.put(w, newEdge);
          } else {

            if (newEdge.weight() < e.weight()) { // update old weight
              cut.put(w, newEdge);
              distances.remove(e);
              distances.add(newEdge);
            }
          }
        }
      }
    }
    result.remove(fakeEdge);
    return result;
  }
}

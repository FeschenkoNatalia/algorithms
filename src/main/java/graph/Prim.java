package graph;

import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

import static graph.Edge.edge;

public class Prim {

  private int[][] G;
  private double[][] weight;
  private int N;

  public Prim() {
  }

  public Prim addEdges(Set<Edge> edges) {
    TreeMap<Integer, Set<Integer>> graph = Maps.newTreeMap();

    for (Edge edge : edges) {
      int v = edge.V();
      int w = edge.W();
      Set<Integer> neighbors = graph.get(v);

      if (neighbors == null) {
        neighbors = Sets.newHashSet();
      }
      neighbors.add(w);
      graph.put(v, neighbors);

      neighbors = graph.get(w);
      if (neighbors == null) {
        neighbors = Sets.newHashSet();
      }
      neighbors.add(v);
      graph.put(w, neighbors);
    }

    N = graph.lastKey() + 1;
    G = new int[N][N];
    weight = new double[N][N];

    for (Integer v : graph.keySet()) {
      Set<Integer> neighbors = graph.get(v);
      G[v] = new int[neighbors.size()];
      int count = 0;

      for (Integer w : neighbors) {
        G[v][count++] = w;
      }
    }

    for (Edge edge : edges) {
      int v = edge.V();
      int w = edge.W();
      weight[v][w] = edge.weight();
      weight[w][v] = edge.weight();
    }
    return this;
  }

  public Set<Edge> getMST(int start) {
    Set<Edge> result = Sets.newHashSet();

    boolean[] marked = new boolean[N];
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
      for (int i = 0; i < G[v].length; i++) {
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

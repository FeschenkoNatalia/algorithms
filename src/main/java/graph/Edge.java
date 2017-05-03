package graph;

public class Edge implements Comparable<Edge> {

  private final int v;
  private final int w;
  private final double weight;

  public Edge(int V, int W, double weight) {
    v = V;
    w = W;
    this.weight = weight;
  }

  public int V() {
    return v;
  }

  public int W() {
    return w;
  }

  public int other(int v) {
    return this.v == v ? w : this.v;
  }

  public double weight() {
    return weight;
  }

  @Override
  public int hashCode() {
    return (v % 1000007)*31 + w;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    if (!(obj instanceof Edge)) return false;
    Edge other = (Edge)obj;
    return other.v == v && other.w == w && Double.compare(other.weight, weight) == 0;
  }

  public static Edge edge(Integer v, int w, double weight) {
    return new Edge(v, w, weight);
  }

  @Override
  public String toString() {
    return String.format("Edge{v=%d, w=%d, weight=%.1f}", v, w, weight);
  }

  @Override
  public int compareTo(Edge o) {
    return Double.compare(weight, o.weight);
  }
}

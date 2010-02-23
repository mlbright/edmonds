package net.algorithms.edmonds;

public class Edge implements Comparable<Edge> {
    final Node from, to;
    final int weight;
   
    public Edge(final Node argFrom, final Node argTo, final int argWeight) {
        from = argFrom;
        to = argTo;
        weight = argWeight;
    }
   
    public int compareTo(final Edge argEdge) {
        return weight - argEdge.weight;
    }
}

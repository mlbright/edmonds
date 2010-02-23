package net.algorithms.edmonds;

public class Node implements Comparable<Node> {
    final int name;
    boolean visited = false; // used for Kosaraju's algorithm and Edmonds's algorithm
    int lowlink = -1; // used for Tarjan's algorithm
    int index = -1; // used for Tarjan's algorithm
   
    public Node(final int argName) {
        name = argName;
    }
   
    public int compareTo(final Node argNode) {
        return argNode == this ? 0 : -1;
    }
}

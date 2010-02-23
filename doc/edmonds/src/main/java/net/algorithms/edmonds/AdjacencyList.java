package net.algorithms.edmonds;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Collection;

public class AdjacencyList {
    private Map<Node, ArrayList<Edge>> adjacencies = new HashMap<Node, ArrayList<Edge>>();

    public void addEdge(Node source, Node target, int weight) {
        ArrayList<Edge> list;
        if (!adjacencies.containsKey(source)) {
            list = new ArrayList<Edge>();
            adjacencies.put(source, list);
        } else {
            list = adjacencies.get(source);
        }
        list.add(new Edge(source, target, weight));
    }

    public ArrayList<Edge> getAdjacent(Node source) {
        return adjacencies.get(source);
    }

    public void reverseEdge(Edge e) {
        adjacencies.get(e.from).remove(e);
        addEdge(e.to, e.from, e.weight);
    }

    public void reverseGraph() {
        adjacencies = getReversedList().adjacencies;
    }

    public AdjacencyList getReversedList() {
        AdjacencyList newlist = new AdjacencyList();
        for (ArrayList<Edge> edges : adjacencies.values()) {
            for(Edge e : edges){
                newlist.addEdge(e.to, e.from, e.weight);
            }
        }
        return newlist;
    }

    public Set<Node> getSourceNodeSet() {
        return adjacencies.keySet();
    }

    public Collection<Edge> getAllEdges() {
        ArrayList<Edge> edges = new ArrayList<Edge>();
        for (List<Edge> e : adjacencies.values()) {
            edges.addAll(e);
        }
        return edges;
    }
}

package net.algorithms.edmonds;

import java.util.ArrayList;

public class Edmonds {
    private ArrayList<Node> cycle;

    public AdjacencyList getMinBranching(Node root, AdjacencyList list) {
        AdjacencyList reverse = list.getReversedList();
        // remove all edges entering the root
        if (reverse.getAdjacent(root) != null) {
            reverse.getAdjacent(root).clear();
        }
        AdjacencyList outEdges = new AdjacencyList();
        // for each node, select the edge entering it with smallest weight
        for (Node n : reverse.getSourceNodeSet()) {
            ArrayList<Edge> inEdges = reverse.getAdjacent(n);
            if (inEdges.size() == 0) {
                continue;
            }
            Edge min = inEdges.get(0);
            for (Edge e : inEdges) {
                if (e.weight < min.weight) {
                    min = e;
                }
            }
           outEdges.addEdge(min.to, min.from, min.weight);
        }

        // detect cycles
        ArrayList<ArrayList<Node>> cycles = new ArrayList<ArrayList<Node>>();
        cycle = new ArrayList<Node>();
        getCycle(root, outEdges);
        cycles.add(cycle);
        for (Node n : outEdges.getSourceNodeSet()) {
            if (!n.visited) {
                cycle = new ArrayList<Node>();
                getCycle(n, outEdges);
                cycles.add(cycle);
            }
        }

        // for each cycle formed, modify the path to merge it into another part of the graph
        AdjacencyList outEdgesReverse = outEdges.getReversedList();

        for (int i=0; i<cycles.size(); i++) {
            if (cycles.get(i).contains(root)) {
                continue;
            }
            mergeCycles(cycles.get(i), list, reverse, outEdges, outEdgesReverse);
        }
        return outEdges;
    }

    private void getCycle(Node n, AdjacencyList outEdges) {
        n.visited = true;
        cycle.add(n);
        if (outEdges.getAdjacent(n) == null) {
            return;
        }
        for (Edge e : outEdges.getAdjacent(n)) {
            if (!e.to.visited) {
                getCycle(e.to, outEdges);
            }
        }
    }

    private void mergeCycles(ArrayList<Node> cycle, AdjacencyList list,
            AdjacencyList reverse, AdjacencyList outEdges, AdjacencyList outEdgesReverse) {
        ArrayList<Edge> cycleAllInEdges = new ArrayList<Edge>();
        Edge minInternalEdge = null;
        // find the minimum internal edge weight
        for (Node n : cycle) {
            for (Edge e : reverse.getAdjacent(n)) {
                if (cycle.contains(e.to)) {
                    if (minInternalEdge == null || minInternalEdge.weight > e.weight) {
                        minInternalEdge = e;
                        continue;
                    }
                } else {
                    cycleAllInEdges.add(e);
                }
            }
        }
        // find the incoming edge with minimum modified cost
        Edge minExternalEdge = null;
        int minModifiedWeight = 0;
        for (Edge e : cycleAllInEdges) {
            int w = e.weight - (outEdgesReverse.getAdjacent(e.from).get(0).weight - minInternalEdge.weight);
            if (minExternalEdge == null || minModifiedWeight > w) {
                minExternalEdge = e;
                minModifiedWeight = w;
            }
        }
        // add the incoming edge and remove the inner-circuit incoming edge
        Edge removing = outEdgesReverse.getAdjacent(minExternalEdge.from).get(0);
        outEdgesReverse.getAdjacent(minExternalEdge.from).clear();
        outEdgesReverse.addEdge(minExternalEdge.to, minExternalEdge.from, minExternalEdge.weight);
        ArrayList<Edge> adj = outEdges.getAdjacent(removing.to);
        for (int i=0; i < adj.size(); i++) {
            if (adj.get(i).to == removing.from) {
                adj.remove(i);
                break;
            }
        }
        outEdges.addEdge(minExternalEdge.to, minExternalEdge.from, minExternalEdge.weight);
    }
}

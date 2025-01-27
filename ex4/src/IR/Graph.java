package IR;

import java.util.*;

public class Graph<T> {
    private Map<T, List<T>> adjacencyList;
    private Map<T, List<T>> prevList; // Each entry stores the list of nodes that point to the key node

    private HashSet<assignment> in = new HashSet<>();
    private HashSet<assignment> out = new HashSet<>();

    private class assignment {
        String var;
        boolean isAssigned;

        public assignment(String v, boolean b) {
            this.var = v;
            this.isAssigned = b;
        }
    }

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    public void addNode(T node) {
        adjacencyList.put(node, new ArrayList<>());
    }

    public void addEdge(T source, T destination) {
        if (!adjacencyList.containsKey(source)) {
            addNode(source);
        }
        if (!adjacencyList.containsKey(destination)) {
            addNode(destination);
        }
        adjacencyList.get(source).add(destination);
        prevList.get(destination).add(source);
    }

    public List<T> getNeighbors(T node) {
        return adjacencyList.get(node);
    }

    public List<T> getPrev(T node) {
        return prevList.get(node);
    }

    public void invertGraph() {
        Map<T, List<T>> invertedGraph = new HashMap<>();
        for (T node : adjacencyList.keySet()) {
            invertedGraph.put(node, new ArrayList<>());
        }
        for (T node : adjacencyList.keySet()) {
            for (T neighbor : adjacencyList.get(node)) {
                invertedGraph.get(neighbor).add(node);
            }
        }
        adjacencyList = invertedGraph;
    }
}

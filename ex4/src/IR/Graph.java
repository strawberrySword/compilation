package IR;

import java.util.*;

class Graph<T> {
    private Map<T, List<T>> adjacencyList;

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
        adjacencyList.get(destination).add(source); // Assuming an undirected graph
    }

    public List<T> getNeighbors(T node) {
        return adjacencyList.get(node);
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

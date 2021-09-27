package com.graph.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import javax.swing.text.StyledEditorKit.BoldAction;

public class Graph {
	private ArrayList<Edge>[] edges;
	private HashMap<String, Integer> indexKeys;
	private int vertexCount;
	private int count;

	public static final String[] VERTEX_LIST = { "A", "B", "C", "D", "E" };
	public int edgeCount;
	public List<String> paths;
	public int to;
	public int maxDistance;
	public int routesCount;

	public Graph(String[] graphInput) {
		this.vertexCount = VERTEX_LIST.length;
		this.edges = (ArrayList<Edge>[]) new ArrayList[vertexCount];
		this.indexKeys = new HashMap<String, Integer>();
		buildAdjList(vertexCount);
		inizitalizeGraph(graphInput);
	}

	public void inizitalizeGraph(String[] graphInput) {
		for (int i = 0; i < graphInput.length; i++) {

			String start = graphInput[i].substring(0, 1);
			String end = graphInput[i].substring(1, 2);
			int weight = Character.getNumericValue(graphInput[i].charAt(2));

			addKeys(start, end, i);
			buildEdge(indexKeys.get(start), indexKeys.get(end), weight);

		}
	}

	// Creating an instance object of the class Edge.
	// Adding the Edge to the correct list of Edges ArrayList.
	public void buildEdge(int start, int end, int weight) {
		Edge edge = new Edge(weight, start, end);
		addEdge(edge);

	}

	// Build the adjacency list. N (vertextCount) number of nodes.
	// Create an arrayList of lists, for each vertex, of type Edge.
	private void buildAdjList(int vertexCount) {
		for (int i = 0; i < vertexCount; i++) {
			this.edges[i] = new ArrayList<Edge>();
		}
	}

	public void addEdge(Edge edge) {
		this.edgeCount++;
		edges[edge.getStartPoint()].add(edge);
	}

	// Adding the String (nodes A-E) to the hashMap as keys.
	// The value (Integer) is the corresponding number in alphabetic order from
	// zero.
	public void addKeys(String start, String end, int count) {
		int currentCount = this.indexKeys.size();
		if (!indexKeys.containsKey(start)) {
			indexKeys.put(start, currentCount);
			currentCount++;
		}
		if (!indexKeys.containsKey(end)) {
			indexKeys.put(end, currentCount);
		}
	}

	// Distance (weight) of the route. 1-5
	public String pathWeight(String route) {
		int weight = getPathWeight(route);
		return (weight != -1) ? String.valueOf(weight) : "NO SUCH ROUTE";
	}

	private int getPathWeight(String route) {
		String stops[] = route.split("-");
		int pathWeight = 0;

		for (int i = 0; i < stops.length - 1; i++) {
			Edge edge = findEdge(indexKeys.get(stops[i]), indexKeys.get(stops[i + 1]));
			if (edge != null) {
				pathWeight += edge.getWeight();
			} else {
				pathWeight = -1;
			}
		}
		return pathWeight;

	}

	private Edge findEdge(int start, int end) {
		Edge found = null;
		for (Edge edge : getAdjacentList(start)) {
			if (edge.getEndPoint() == end) {
				found = edge;
				break;
			}
		}
		return found;
	}

	// Trips max. #6
	public int pathCount(String route) {
		String start = route.substring(0, 1);
		String end = route.substring(2, 3);
		int stopsLimit = Character.getNumericValue(route.charAt(4));

		resetCount();
		dfs(indexKeys.get(start), 0, indexKeys.get(end), stopsLimit);
		return this.count;
	}

	public void dfs(int nodeIndex, int depth, int search, int limit) {
		if (depth == limit) {
			return;
		}
		LinkedList<ArrayList<Edge>> queue = new LinkedList<ArrayList<Edge>>();
		queue.add(getAdjacentList(nodeIndex));
		while (!queue.isEmpty()) {
			for (Edge edge : queue.poll()) {

				if (hasNode(edge, search)) {
					this.count++;
				}
				dfs(edge.getEndPoint(), depth + 1, search, limit);
			}
		}

	}

	private boolean hasNode(Edge edge, int nodeIndex) {
		return edge.getEndPoint() == nodeIndex;
	}

	
	// Trip exact. #7
	public int pathCountExact(String route) {
		String start = route.substring(0, 1);
		String end = route.substring(2, 3);
		int stopsLimit = Character.getNumericValue(route.charAt(4));

		resetCount();
		dfsExact(indexKeys.get(start), 1, indexKeys.get(end), stopsLimit);
		return this.count;
	}

	public void dfsExact(int nodeIndex, int depth, int search, int limit) {
		if (depth == limit) {
			return;
		}
		LinkedList<ArrayList<Edge>> queue = new LinkedList<ArrayList<Edge>>();
		queue.add(getAdjacentList(nodeIndex));
		while (!queue.isEmpty()) {
			for (Edge edge : queue.poll()) {

				if (hasNode(edge, search) && (depth == limit)) {
					this.count++;
				}

				dfs(edge.getEndPoint(), depth + 1, search, limit);
			}
		}

	}

	// Length of Shortest Route. 8-9
	public int calculateShortestPath(String from, String to) {
		paths = new ArrayList<>();
		this.to = getIndex(to);
		int startIndex = getIndex(from);
		calculateShortestPath(startIndex, String.valueOf(startIndex));

		int shortestDistance = Integer.MAX_VALUE, currentDistance;
		for (String s : paths) {
			currentDistance = calculateDistance(s);
			if (shortestDistance > currentDistance)
				shortestDistance = currentDistance;
		}

		if (shortestDistance == Integer.MAX_VALUE)
			return 0;

		return shortestDistance;
	}

	private void calculateShortestPath(int from, String path) {
		List<Edge> edges = getAdjacentList(from);
		for (Edge edge : edges) {

			// validation check.
			if (path.length() > 1 && path.substring(1).contains(String.valueOf(edge.getEndPoint())))
				continue;

			String next = path + edge.getEndPoint();

			if (this.to == edge.getEndPoint())
				paths.add(getPathName(next));

			calculateShortestPath(edge.getEndPoint(), next);
		}
	}

	private int calculateDistance(String route) {
		if (route == null)
			throw new IllegalArgumentException("Route is wrong");
		int distance = 0;
		String[] vertex = route.trim().split("");
		int from, to;

		for (int i = 0; i < vertex.length - 1;) {
			boolean hasPath = false;
			from = getIndex(vertex[i++]);
			to = getIndex(vertex[i]);
			List<Edge> edgeList = getAdjacentList(from);

			for (Edge edge : edgeList)
				if (edge.getEndPoint() == to) {
					distance += edge.getWeight();
					hasPath = true;
					break;
				}
			if (!hasPath)
				return -1;
		}

		return distance;
	}

	private String getPathName(String path) {
		String ar[] = path.trim().split("");
		String name = "";
		for (String s : ar)
			name += getVertexName(Integer.parseInt(s));

		return name;
	}

	private String getVertexName(int index) {
		if (index < 0 || index >= VERTEX_LIST.length)
			throw new IllegalArgumentException("Wrong index");

		return VERTEX_LIST[index];
	}

	private static int getIndex(String vertex) {
		int index = Arrays.binarySearch(VERTEX_LIST, vertex);
		if (index < 0)
			throw new IllegalArgumentException("Wrong input");

		return index;
	}

	// #10
	public int calculateRoutesCount(String from, String to, int maxDistance) {
		this.to = getIndex(to);
		this.maxDistance = maxDistance;
		this.routesCount = 0;
		int startIndex = getIndex(from);
		calculateRoutesCount(startIndex, String.valueOf(startIndex));

		return routesCount;
	}

	private void calculateRoutesCount(int from, String path) {
		List<Edge> edges = getAdjacentList(from);
		for (Edge e : edges) {

			String next = path + e.getEndPoint();
			int distance = calculateDistance(getPathName(next));

			if (this.to == e.getEndPoint() && (distance < maxDistance))
				routesCount++;

			if (distance < maxDistance)
				calculateRoutesCount(e.getEndPoint(), next);
		}
	}

	// helper methods
	private void resetCount() {
		this.count = 0;
	}

	public ArrayList<Edge> getAdjacentList(int node) {
		return this.edges[node];
	}

	public void printGraph() {
		for (int i = 0; i < this.edges.length; i++) {
			System.out.println(edges[i]);
		}
	}

}

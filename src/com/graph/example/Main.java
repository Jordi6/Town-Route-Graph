package com.graph.example;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		
		String graphInput[] = {"AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"};
		Graph graph = new Graph(graphInput);
	
		// View adjacency list
		// System.out.println(graph);
		
		System.out.println("1. " + graph.pathWeight("A-B-C"));
		System.out.println("2. " + graph.pathWeight("A-D"));
		System.out.println("3. " + graph.pathWeight("A-D-C"));
		System.out.println("4. " + graph.pathWeight("A-E-B-C-D"));
		System.out.println("5. " + graph.pathWeight("A-E-D"));
		System.out.println("6. " + graph.pathCount("C-C-3"));
		System.out.println("7. " + graph.pathCountExact("A-C-4"));
		System.out.println("8. " + graph.calculateShortestPath("A", "C"));
		System.out.println("9. " + graph.calculateShortestPath("B", "B"));
		System.out.println("10. " + graph.calculateRoutesCount("C", "C", 30));

	}
}












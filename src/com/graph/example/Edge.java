package com.graph.example;

public class Edge {
	
	int weight;
	int start;
	int end;
	
	
	public Edge(int weight, int start, int end) {
		this.weight = weight;
		this.start = start;
		this.end = end;
	}
	
	public int getStartPoint() {
		return this.start;
	}
	
	public int getEndPoint() {
		return this.end;
	}
	
	public int getWeight() {
		return this.weight;
	}
	
	public int compareTo(Edge edge) {
		return Integer.compare(this.weight, edge.getWeight());
	}
	
	
	public String toString() {
		return " {weight: " + weight + ", start: " + start + ", end: " + end + "} ";
	}
	
}

package net.datastructures;

import java.util.Scanner;

/**
 * Tests the BFS Tree and Bacon Number portions (KevinBaconGame.java)
 * @author Delos Chang
 *
 */

public class KevinBaconGameTester{

	public static void main(String[] args){
		AdjacencyListGraphMap<String, String> baconGraph = 
				new DirectedAdjListMap<String, String>();

		// Create our Bacon Graph with actors
		baconGraph.insertVertex("Kevin Bacon");
		baconGraph.insertVertex("actor1");
		baconGraph.insertVertex("actor2");
		baconGraph.insertVertex("actor3");
		baconGraph.insertVertex("actor4");
		baconGraph.insertVertex("actor5");
		baconGraph.insertVertex("actor6");
		baconGraph.insertEdge("Kevin Bacon", "actor1", "movie1");
		baconGraph.insertEdge("Kevin Bacon", "actor2", "movie1");
		baconGraph.insertEdge("actor1", "actor2", "movie1");
		baconGraph.insertEdge("actor1", "actor3", "movie2");
		baconGraph.insertEdge("actor3", "actor2", "movie3");
		baconGraph.insertEdge("actor3", "actor4", "movie4");
		baconGraph.insertEdge("actor5", "actor6", "movie5");

		System.out.println("\n Graph created: ");
		System.out.println(baconGraph);

		// create graph with Kevin Bacon as the root
		KevinBaconGame baconGame = new KevinBaconGame(baconGraph);

		// create BFS tree 
		DirectedAdjListMap<String, String> BFSGraph = 
				baconGame.createBFS();

		System.out.println("\n BFS Graph created: ");
		System.out.println(BFSGraph);

		String command;           // a command
		Scanner input = new Scanner(System.in);

		// ask for series of actors
		while (true) {
			// Ask for name of actor
			System.out.println("\n Enter the name of an actor: ");
			command = input.nextLine();

			baconGame.findBaconNumber(BFSGraph, command);
		}
	}
}
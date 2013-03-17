package net.datastructures;

/**
 * Creates a Bacon Graph with input files
 * @author deloschang
 *
 */

public class KevinBaconGame {

	// instance variables
	private final static String ROOT = "Kevin Bacon";
	private AdjacencyListGraphMap<String, String> baconGraph;

	/**
	 * Constructor 
	 */
	public KevinBaconGame(AdjacencyListGraphMap<String, String> baconGraph){
		this.baconGraph = baconGraph;
	}


	/**
	 * Implements the BFS and creates a BFS tree
	 * @param baconGraph graph to create bfs tree from
	 * @param bfsQueue queue to search with
	 */
	public DirectedAdjListMap<String, String> createBFS(){

		// new directed graph to return
		DirectedAdjListMap<String, String> T = new DirectedAdjListMap<String, String>();

		// insert root into an empty queue bfsQueue and into new directed graph T 
		NodeQueue<String> bfsQueue = new NodeQueue<String>();
		bfsQueue.enqueue(ROOT);
		T.insertVertex(ROOT);


		while (!bfsQueue.isEmpty()){
			// dequeue Q to get the next vertex v to process
			String v = bfsQueue.dequeue();

			// for each edge e that is incident to v in G
			for (Edge<String> edge : baconGraph.incidentEdges(v)){
				// Let v' be the other end of the edge
				Vertex<String> vPrime = baconGraph.opposite(v, edge);

				// true if vertex is in graph
				boolean checkForVertex = T.vertexInGraph(vPrime.toString());

				// if vPrime not in T
				if (!checkForVertex && vPrime.toString() != ROOT){
					T.insertVertex(vPrime.toString());
					// add edge with the same label as e from vPrime to v in T 
					System.out.println(T.insertDirectedEdge(vPrime.toString(), v.toString(), edge.element()));

					bfsQueue.enqueue(vPrime.toString());
				}
			}
		}
		return T;

	}


	/**
	 * Finds the Bacon number given actor/actress name
	 * @param BFSGraph BFS tree for the Bacon Graph
	 */
	public void findBaconNumber(DirectedAdjListMap<String, String> BFSGraph, String command){

		// Look up the actor in T
		Vertex<String> vertex = BFSGraph.getVertex(command);

		if (vertex == null){
			System.out.println(command + "is not related to Kevin Bacon");
		} else if (vertex.toString() == "Kevin Bacon") { 
			System.out.println(vertex + "'s Bacon Number is 0! That's him!");
		} else {
			Vertex<String> next_vertex = vertex;
			int depth = 0;

			// while not Kevin Bacon, follow to root
			while (next_vertex.toString() != "Kevin Bacon"){
				// should only be 1 edge out 
				for (Edge<String> path : BFSGraph.incidentEdgesOut(vertex)){
					next_vertex = BFSGraph.endVertices(path)[1];
					System.out.println(BFSGraph.endVertices(path)[0] + " " +
							"appeared in " + path.element() + " with " + next_vertex);
				}

				vertex = next_vertex;
				depth += 1;
			}

			System.out.println(command + "'s Bacon Number is " + depth);
		}
	}

}
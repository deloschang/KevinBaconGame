package net.datastructures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFileChooser;

/**
 * Creates a Kevin Bacon Graph from input files
 * @author Delos Chang
 *
 */

public class KevinBaconGame2{
	// KevinBaconGamePlay needs to access this variable
	AdjacencyListGraphMap<String, String> baconGraph = new AdjacencyListGraphMap<String, String>();

	private HashMap<String, String> idToActor = new HashMap<String, String>();
	private HashMap<String, String> idToMovie = new HashMap<String, String>();
	private HashMap<String, Set<String>> movieIdToActorIds = new HashMap<String, Set<String>>();

	/**
	 * Constructor 
	 * @param ACTORS_READ_INPUT file to read actor list from
	 * @param MOVIES_READ_INPUT file to read movies list from
	 * @param ACTORS_MOVIES_READ_INPUT file to read actors movies list from
	 * @throws IOException
	 */
	public KevinBaconGame2(String ACTORS_READ_INPUT,
			String MOVIES_READ_INPUT,
			String ACTORS_MOVIES_READ_INPUT) throws IOException{
		// map ID to actor name
		createMap(ACTORS_READ_INPUT, 1);
		createMap(MOVIES_READ_INPUT, 2);
		createMap(ACTORS_MOVIES_READ_INPUT, 3);

		this.produceEdgeFromMap();
	}

	/**
	 * Create the edges between vertices (link actors and common movies)
	 */
	private void produceEdgeFromMap(){
		// Create edge between 2 actors if in same movie
		// edge is name of movie
		Set<String> movieSet = movieIdToActorIds.keySet();

		for (String movieId : movieSet){
			Set<String> actorsInMovieSet = movieIdToActorIds.get(movieId);

			for (String actorId : actorsInMovieSet){

				for (String secondActorId : actorsInMovieSet){

					String actorOne = idToActor.get(actorId);
					String actorTwo = idToActor.get(secondActorId);

					// avoid duplicates
					if (!baconGraph.areAdjacent(actorOne, actorTwo) && secondActorId != actorId){
						// insert the edge
						baconGraph.insertEdge(actorOne, actorTwo, idToMovie.get(movieId));
					}

				}

			}

		}
	}

	/**
	 * Create map from filename split by |
	 * @param filename filename to draw from
	 * @param identifier identifies what kind of file it is
	 */
	private void createMap(String filename, int identifier) throws IOException{
		try { 
			BufferedReader fileInput = new BufferedReader(new FileReader(filename));
			String nextLine = fileInput.readLine();

			// check if empty file (boundary case)
			if (nextLine == null){
				System.out.println("Empty file! Please choose another list file");
				System.exit(-1);
			}

			while (nextLine != null){
				parse(nextLine, identifier);
				nextLine = fileInput.readLine();
			}

			fileInput.close();

		} catch (FileNotFoundException ex){
			System.out.println(ex + ". Please use another list file");
			System.exit(-1);
		} catch (IOException ex) {
			System.out.println(ex + ". Error. ");
		}
	}

	/**
	 * Parses the input for the | 
	 * @param nextLine the line of input to parse
	 * @param identifier identifies the type of file to parse and map
	 */
	private void parse(String nextLine, int identifier){
		// | is a special operator, escape it.
		String[] list = nextLine.split("\\|");

		switch (identifier){ 
			case 1: 
				idToActor.put(list[0], list[1]);
	
				// add in the actor name to graph
				baconGraph.insertVertex(list[1]);
				break;
	
			case 2:
				idToMovie.put(list[0], list[1]);
				break;
	
			case 3:
	
				// list[0] is the movie
				// list[1] is actor in movie
	
				// if the map contains the movie already, means another actor in movie
				if (movieIdToActorIds.containsKey(list[0])){
					// retrieve set and add
					movieIdToActorIds.get(list[0]).add(list[1]);
				} else {
					// create a new set 
					Set<String> actorInMovie = new HashSet<String>(); // set containing actors in movie
	
					// add actor in 
					actorInMovie.add(list[1]);
					movieIdToActorIds.put(list[0], actorInMovie);
				}
				
				break;
		}
	}

	/**
	 * Puts up a fileChooser and gets path name for file to be opened.
	 * Returns an empty string if the user clicks "cancel".
	 * @return path name of the file chosen	
	 */
	public static String getFilePath() {
		//Create a file chooser
		JFileChooser fc = new JFileChooser();

		int returnVal = fc.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION)  {
			File file = fc.getSelectedFile();
			String pathName = file.getAbsolutePath();
			return pathName;
		}
		else
			return "";
	}


	public static void main(String [] args) throws IOException{
		// pick files
		System.out.println("Please pick an actor file");
		String ACTORS_READ_INPUT = getFilePath();

		System.out.println("Please pick a movies file");
		String MOVIES_READ_INPUT = getFilePath();

		System.out.println("Please pick a movies-actor file.");
		String ACTORS_MOVIES_READ_INPUT = getFilePath();

		KevinBaconGame2 baconGameFromFiles = new 
				KevinBaconGame2(ACTORS_READ_INPUT,
						MOVIES_READ_INPUT,
						ACTORS_MOVIES_READ_INPUT);

		System.out.println(baconGameFromFiles.baconGraph);
	}

}
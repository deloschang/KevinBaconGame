package net.datastructures;

import java.io.IOException;
import java.util.Scanner;

/**
 * Plays the Kevin Bacon Game
 * @author Delos Chang
 *
 */

public class KevinBaconGamePlay{

	// Play the Game
	public static void main(String [] args) throws IOException{
		// pick files
		System.out.println("Please pick an actor file");
		String ACTORS_READ_INPUT = KevinBaconGame2.getFilePath();

		System.out.println("Please pick a movies file");
		String MOVIES_READ_INPUT = KevinBaconGame2.getFilePath();

		System.out.println("Please pick a movies-actor file.");
		String ACTORS_MOVIES_READ_INPUT = KevinBaconGame2.getFilePath();

		// Create the bacon Graph with files from KevinBaconGame2
		KevinBaconGame2 baconGameFromFiles = new 
				KevinBaconGame2(ACTORS_READ_INPUT,
						MOVIES_READ_INPUT,
						ACTORS_MOVIES_READ_INPUT);

		// Create the Bacon Graph
		KevinBaconGame baconGame = new KevinBaconGame(baconGameFromFiles.baconGraph);

		// Create the BFS tree
		DirectedAdjListMap<String, String> BFSGraph = baconGame.createBFS();

		// Ask for name of actor
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
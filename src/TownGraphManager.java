import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Set;

import javafx.scene.shape.Path;

/**
 * data manager class that contains a graph
 * @author Nimesh Fernando
 *
 */
public class TownGraphManager implements TownGraphManagerInterface{
	private Graph graph;
	
	public TownGraphManager() {
		graph = new Graph();
	}

	public boolean populateTownGraph(File file) throws IOException {
		//road name, 14; road 1 name; road 2 name
		Scanner scanner = new Scanner(file);
		while(scanner.hasNextLine()) {
			String[] connection = scanner.nextLine().split(";");
			String[] road = connection[0].split(",");
			addTown(connection[1]);
			addTown(connection[2]);
			addRoad(connection[1], connection[2], Integer.parseInt(road[1]), road[0]);
		}
		scanner.close();
		return false;
	}

	@Override
	public boolean addRoad(String town1, String town2, int weight, String roadName) {
		return graph.addEdge(new Town(town1), new Town(town2), weight, roadName) != null;
	}

	@Override
	public String getRoad(String town1, String town2) {
		return graph.getEdge(new Town(town1), new Town(town2)).getName();
	}

	@Override
	public boolean addTown(String v) {
		return graph.addVertex(new Town(v));
	}

	@Override
	public boolean containsTown(String v) {
		return graph.containsVertex(new Town(v));
	}

	@Override
	public boolean containsRoadConnection(String town1, String town2) {
		return graph.containsEdge(new Town(town1), new Town(town2));
	}

	@Override
	public ArrayList<String> allRoads() {
		ArrayList<String> allRoads = new ArrayList<>();
		
		for(Road road: graph.edgeSet()) {
			allRoads.add(road.getName());
		}
		
		Collections.sort(allRoads);
		
		return allRoads;
	}

	@Override
	public boolean deleteRoadConnection(String town1, String town2, String road) {
		return graph.removeEdge(new Town(town1), new Town(town2), 0, road) != null;
	}

	@Override
	public boolean deleteTown(String v) {
		return graph.removeVertex(new Town(v));
	}

	@Override
	public ArrayList<String> allTowns() {
		ArrayList<String> allTowns = new ArrayList<>();
		for(Town town: graph.vertexSet()) {
			allTowns.add(town.getName());
		} 
		Collections.sort(allTowns);
		return allTowns;
	}

	public ArrayList<String> getPath(String town1, String town2) {
		ArrayList<String> pathList = graph.shortestPath(new Town(town1), new Town(town2));
		for(int i = 0; i < pathList.size(); i++) {
			pathList.set(i, pathList.get(i) + " miles");
		}
		pathList.add("Total miles: " + graph.getTown(new Town(town2)).getDistanceFromRoot() + " miles");
		return pathList;
	}

	/**
	 * @param string - name of a town
	 * @return town that contains that name
	 */
	public Town getTown(String string) {
		return graph.getTown(new Town(string));
	}

	@Override
	public ArrayList<String> getPathSets(String town1, String town2) {
		// TODO Auto-generated method stub
		return null;
	}

}

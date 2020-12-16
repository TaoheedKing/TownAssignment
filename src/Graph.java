import java.util.*;

import javafx.util.Pair;

/**
 * 
 * @author Taoheed King
 *
 */
public class Graph implements GraphInterface<Town, Road> {

	private Map<Town, LinkedList<Road>> adjacencyList;
	//first town in parameters represents the root town, Integer in pair represents distance from the root, Town in the pair represent previous town
	private HashMap<Town, Pair<Integer, Town>> shortestPath;
	
	/**
	 * default constructor 
	 */
	public Graph() {
		adjacencyList = new HashMap<>();
		shortestPath = new HashMap<>();
	}
	
	
	/**
	 * 
	 * @param town - town to look up in graph
	 * @return returns a reference to the town looked up. null if town does not exist
	 */
	public Town getTown(Town town) {
		if(adjacencyList.containsKey(town)) {
			for(Town returnedTown: adjacencyList.keySet()) {
				if(returnedTown.equals(town)) {
					return returnedTown;
				}
			}
		}
		return null;
	}
	
	@Override
	public Road getEdge(Town sourceVertex, Town destinationVertex) {
		if(adjacencyList.containsKey(sourceVertex) && adjacencyList.containsKey(destinationVertex)) {
			Road comparableEdge = new Road(sourceVertex, destinationVertex,""); 
			for(Road temp: adjacencyList.get(sourceVertex)) {
				if(comparableEdge.equals(temp)) {
					return temp;
				}
			}
		}
		return null;
	}

	
	@Override
	public Road addEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {

		if(adjacencyList.containsKey(sourceVertex) && adjacencyList.containsKey(destinationVertex)) {
			sourceVertex = getTown(sourceVertex);
			destinationVertex = getTown(destinationVertex);
			
			Road road = new Road(sourceVertex, destinationVertex, weight, description);
			
			adjacencyList.get(sourceVertex).add(road);
			sourceVertex.addAdjacentTown(destinationVertex);
			adjacencyList.get(destinationVertex).add(road);
			destinationVertex.addAdjacentTown(sourceVertex);
			
			return road;
		}
		else throw new IllegalArgumentException("one of the vertices not found in graph");
	}

	@Override
	public boolean addVertex(Town v) {
		if(adjacencyList.containsKey(v)) {
			return false;
		}
		else {
			adjacencyList.put(v, new LinkedList<>());
			return true;
		}
	}

	@Override
	public boolean containsEdge(Town sourceVertex, Town destinationVertex) {
		Road comparableRoad = new Road(sourceVertex, destinationVertex, "");
		return adjacencyList.get(sourceVertex).contains(comparableRoad);
	}

	@Override
	public boolean containsVertex(Town v) {
		return adjacencyList.containsKey(v);
	}

	@Override
	public Set<Road> edgeSet() {
		Set<Road> edgeSet = new HashSet<>();
		for(Map.Entry<Town, LinkedList<Road>> entry: adjacencyList.entrySet()) {
			for(Road road: entry.getValue()) {
				edgeSet.add(road);
			}
		}
		return edgeSet;
	}

	@Override
	public Set<Road> edgesOf(Town vertex) {
		Set<Road> edgesOf = new HashSet<>();

		Iterator<Road> iter = adjacencyList.get(vertex).iterator();
		
		while(iter.hasNext()) {
			edgesOf.add(iter.next());
		}

		return edgesOf;
	}

	@Override
	public Road removeEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {

		if(containsEdge(sourceVertex, destinationVertex) && weight > -1 && description != null) {
			Road road = new Road(sourceVertex, destinationVertex, weight, description);
			adjacencyList.get(sourceVertex).remove(road);
			adjacencyList.get(destinationVertex).remove(road);
			sourceVertex.removeAdjacentTown(destinationVertex);
			destinationVertex.removeAdjacentTown(sourceVertex);
			return road;
		}
		else return null;
	}

	@Override
	public boolean removeVertex(Town v) {
		if(v == null) {
			return false;
		}
		else if (containsVertex(v)) {
			Iterator<Road> iter = adjacencyList.get(v).iterator();
			while(iter.hasNext()) {
				Road road= iter.next();
				road.getSource().removeAdjacentTown(road.getDestination());
				road.getDestination().removeAdjacentTown(road.getSource());
				iter.remove();
			}

			adjacencyList.remove(v);
			return true;
		}
		else return false;
	}

	@Override
	public Set<Town> vertexSet() {
		return adjacencyList.keySet();
	}

	@Override
	public ArrayList<String> shortestPath(Town sourceVertex, Town destinationVertex) {
		ArrayList<String> path = new ArrayList<>();
		sourceVertex = getTown(sourceVertex);
		destinationVertex = getTown(destinationVertex);
		dijkstraShortestPath(sourceVertex);
		
		while(shortestPath.get(destinationVertex).getValue() != null) {
			
			//build string to put in array list
			path.add(shortestPath.get(destinationVertex).getValue().getName()+
			" via " +
			getEdge(destinationVertex, shortestPath.get(destinationVertex).getValue()).getName() +
			" to " +
			destinationVertex.getName() +
			" " +
			getEdge(destinationVertex, shortestPath.get(destinationVertex).getValue()).getWeight()
			);
			// end string building
			
			destinationVertex = shortestPath.get(destinationVertex).getValue();
		}
 
		Collections.reverse(path);
		return path;
	}

	@Override
	public void dijkstraShortestPath(Town sourceVertex) {
		sourceVertex = getTown(sourceVertex);
		
		Set<Town> verticesSet = new HashSet<>();
		
		
		//sets distance to all towns to infinity
		//add all towns in the graph to the vertices set
		for(Town town: adjacencyList.keySet()) {
			town.setDistanceFromRoot(Integer.MAX_VALUE);
			//pair integer represents distance from vertex, Town represent predecessor
			shortestPath.put(town, new Pair<>(Integer.MAX_VALUE, null));
			verticesSet.add(town);
		}
		
		//sets the distance of the root to 0
		shortestPath.put(sourceVertex, new Pair<>(0, null));
		sourceVertex.setDistanceFromRoot(0);
		
		//processes all towns in the set until the set is empty
		while(!verticesSet.isEmpty()){
			//gets the vertex with the smallest distance from the source town
			Town currentVertex = Collections.min(verticesSet, (Town o1, Town o2) -> Integer.compare(o1.getDistanceFromRoot(),o2.getDistanceFromRoot()));
			//removes the vertex being processed from the list
			verticesSet.remove(currentVertex); 
			
			
			//iterates through all the adjacent towns of a root town and sets their distance to the smallest possible
			for(Town currentVertexNeighbor: currentVertex.getAdjacentTowns()) {
				if(verticesSet.contains(currentVertexNeighbor)) { // only vertices that are still in the set
					int newDistance = currentVertex.getDistanceFromRoot() + getEdge(currentVertex, currentVertexNeighbor).getWeight();
					if(newDistance < currentVertexNeighbor.getDistanceFromRoot()) {
						currentVertexNeighbor.setDistanceFromRoot(newDistance);
						shortestPath.put(currentVertexNeighbor, new Pair<>(newDistance, currentVertex));
					}
				}
			}
		}

	}
	
}

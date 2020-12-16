import java.util.Objects;

/**
 * @author Nimesh Fernando
 *
 */
public class Road implements Comparable<Road> {
	
	private Town source;
	private Town destination;
	private int distance;
	private String roadName;
	
	/**
	 * 
	 * @param source - One town on the road
	 * @param destination -  Another town on the road
	 * @param distance - Weight of the edge, i.e., distance from one town to the other
	 * @param roadName - Name of the road
	 */
	public Road(Town source, Town destination, int distance, String roadName) {
		this.source = source;
		this.destination = destination;
		this.distance = distance;
		this.roadName = roadName;
	}
	
	/**
	 *  
	 * @param source - One town on the road
	 * @param destination -  Another town on the road
	 * @param roadName - Name of the road
	 */
	public Road(Town source, Town destination, String roadName) {
		this.source = source;
		this.destination = destination;
		this.distance = 1;
		this.roadName = roadName;
	}
	
	/**
	 * 
	 * @param town - a vertex of the graph 
	 * @return true only if the edge is connected to the given vertex
	 */
	public boolean contains(Town town) {
		return town.equals(source) || town.equals(destination);
	}
	
	/**
	 * 
	 * @return The name of the road
	 */
	public String getName(){
		return roadName;
	}
	
	/**
	 * 
	 * @return A town on the road 
	 */
	public Town getDestination() {
		return destination;
	}
	
	/**
	 * 
	 * @return A town on the road
	 */
	public Town getSource() {
		return source;
	}
	
	/**
	 * 
	 * @return the distance of the road
	 */
	public int getWeight() {
		return distance;
	}
	
	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.roadName = name;
	}
	
	/**
	 * @param source the source to set
	 */
	public void setSource(Town source) {
		this.source = source;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(Town destination) {
		this.destination = destination;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setWeight(int distance) {
		this.distance = distance;
	}

	/**
	 * @param r - road object to compare it to
	 * @return Returns true if each of the ends of the road r is the same as the ends of this road. 
	 * 
	 */
	@Override
	public boolean equals(Object r) {
		if( r instanceof Road) {
			Road road = (Road) r;
			return  road.getSource().equals(source) && road.getDestination().equals(destination) ||
					road.getSource().equals(destination) && road.getDestination().equals(source);
		}
		else
			return false;
		
	}
	@Override
	public int hashCode() {
		return Objects.hash(source, destination);
	}
	
	/**
	 * @return 0 if the road names are the same, a positive or negative number if the road names are not the same 
	 */
	@Override
	public int compareTo(Road o) {
		return roadName.compareTo(o.getName());
	}


	@Override
	public String toString() {
		return roadName + " from " +
				source.getName() + " to " + destination.getName();
	}
	
	
	
}

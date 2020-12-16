import java.util.*;

/**
 * 
 * @author Taoheed King
 *
 */
public class Town implements Comparable<Town> {
	
	private String name;
	private Set<Town> adjacentTowns;
	private int distancefromRoot;
	
	
	/**
	 * @param name - name of town to be created
	 */
	public Town(String name) {
		this.name = name;
		adjacentTowns = new HashSet<>();
		distancefromRoot = Integer.MAX_VALUE;
	}

	/**
	 *
	 * @param templateTown - town to be copied
	 */
	public Town(Town templateTown) {
		this.name = templateTown.getName();
		adjacentTowns = new HashSet<>();
		for (Town temp: templateTown.getAdjacentTowns()) {
			this.adjacentTowns.add(temp);
		}
		this.distancefromRoot = templateTown.getDistanceFromRoot();
	}
	
	/**
	 *  
	 * @return town's name 
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * 
	 * @return the distance from this town to another
	 */
	public int getDistanceFromRoot() {
		return distancefromRoot;
	}
	
	/**
	 * 
	 * @param name - the new name of the town
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 
	 * @param distance - integer representing the distance from this town 
	 */
	public void setDistanceFromRoot(int distance) {
		this.distancefromRoot = distance;
	}
	
	/**
	 * 
	 * @return the list of adjacent towns
	 */
	public Set<Town> getAdjacentTowns() {
		return adjacentTowns;
	}
	
	/**
	 * 
	 * @param town - town to be added as adjacent
	 */
	public void addAdjacentTown(Town town) {
		adjacentTowns.add(town);
	}
	
	/**
	 * 
	 * @param town
	 * @return true if town was removed, otherwise false
	 */
	public Boolean removeAdjacentTown(Town town) {
		return adjacentTowns.remove(town);
	}
	
	
	/**
	 * 
	 * @return 0 if names are equal, a positive or negative number if the names are not equal
	 */
	@Override
	public int compareTo(Town o) {
		return o.getName().compareTo(name);
	}
	
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	/**
	 * @return true if the town names are equal, false if not
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj != null) {
			return obj.hashCode() == this.hashCode();
		}
		else 
			return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name + " ");
		/*
		sb.append(name + " adjacent to: ");
		for(Town temp: adjacentTowns) {
			sb.append(temp.getName() + ", ");
		}
		*/
		return sb.toString();
	
	}
	
}

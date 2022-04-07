package ex5;

import java.io.Serializable;

/**
 * Branch object
 * @author netanel & guy
 *
 */
public class Branch implements Serializable {
	
	/**
	 * id - branch id
	 * location - branch address
	 * opening hours - FromTo object that will store 2 times, open hour and closing hour
	 */
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String location;
	private FromTo openingHours;
	
	public Branch(int id, String location, FromTo openingHours) {
		this.id = id;
		this.location = location;
		this.openingHours = openingHours;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public FromTo getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(FromTo openingHours) {
		this.openingHours = openingHours;
	}

	@Override
	public String toString() {
		return "Branch [id=" + id + ", location=" + location + ", openingHours=" + openingHours + "]";
	}
	
	
}

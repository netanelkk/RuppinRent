package ex5;

import java.io.Serializable;

/**
 * Car object
 * @author netanel & guy
 *
 */
public class Car implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * carid - id of the car
	 * manuYear - manufacture year of the car
	 * model - model of the car
	 * sub model - sub model of the car
	 * category - car category: mini, sedan, executive, suv	
	 * gear - car gear: automatic, manual, robotic
	 * rent price - rental price for one day
	 * branch id - the branch id that the car in
	 */
	
	private int carID; 
	private int manuYear;
	private String model;
	private String subModel;
	private String category;
	private String gear;
	private int rentPrice;
	private int branchID;
	
	public Car(int carID, int manuYear, String model, String subModel, String category, String gear, int rentPrice, int branchID) {
		this.carID = carID;
		this.manuYear = manuYear;
		this.model = model;
		this.subModel = subModel;
		this.category = category;
		this.gear = gear;
		this.rentPrice = rentPrice;
		this.branchID = branchID;
	}

	public int getCarID() {
		return carID;
	}

	public void setCarID(int carID) {
		this.carID = carID;
	}

	public int getManuYear() {
		return manuYear;
	}

	public void setManuYear(int manuYear) {
		this.manuYear = manuYear;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSubModel() {
		return subModel;
	}

	public void setSubModel(String subModel) {
		this.subModel = subModel;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getGear() {
		return gear;
	}

	public void setGear(String gear) {
		this.gear = gear;
	}

	public int getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(int rentPrice) {
		this.rentPrice = rentPrice;
	}

	public int getBranchID() {
		return branchID;
	}

	public void setBranchID(int branchID) {
		this.branchID = branchID;
	}

	@Override
	public String toString() {
		return "Car [carID=" + carID + ", manuYear=" + manuYear + ", model=" + model + ", subModel=" + subModel
				+ ", category=" + category + ", gear=" + gear + ", rentPrice=" + rentPrice + ", branchID=" + branchID
				+ "]";
	}
	
	
	
	
	
}

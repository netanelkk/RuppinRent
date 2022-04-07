package ex5;

import java.io.Serializable;

/**
 * Class for storing booked cars
 * @author netanel & guy
 *
 */
public class BookCar implements Serializable  {

	private static final long serialVersionUID = 1L;

	/**
	 * Car id - id of choosed car
	 * User email - id of the user who made the order
	 * Booking dates - FromTo object, contains 2 dates, that are the booking range
	 */
	
	private int carID;
	private String userEmail;
	private FromTo bookingDates;
	
	public BookCar(int carID, String userEmail, FromTo bookingDates) {
		this.carID = carID;
		this.userEmail = userEmail;
		this.bookingDates = bookingDates;
	}

	public int getCarID() {
		return carID;
	}

	public void setCarID(int carID) {
		this.carID = carID;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public FromTo getBookingDates() {
		return bookingDates;
	}

	public void setBookingDates(FromTo bookingDates) {
		this.bookingDates = bookingDates;
	}

	@Override
	public String toString() {
		return "BookCar [carID=" + carID + ", userEmail=" + userEmail + ", bookingDates=" + bookingDates + "]";
	}
	
	
}

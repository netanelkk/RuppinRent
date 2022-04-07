package ex5;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Main class of the program
 * fetching all the information from serialization, if found
 * and opening the first window (MainPageFrame)
 * @author netanel & guy
 *
 */
public class MainClass {

	/**
	 * 4 static arraylist of all information
	 */
	public static ArrayList<User> listUsers;
	public static ArrayList<Branch> listBranches;
	public static ArrayList<Car> listCars;
	public static ArrayList<BookCar> listBookedCars;
	
	private static FileInputStream fis;
	private static ObjectInputStream in;
	public static User myUser;
	
	public static void main(String[] args) {
		listUsers = new ArrayList<User>();
		listBranches = new ArrayList<Branch>();
		listCars = new ArrayList<Car>();
		listBookedCars = new ArrayList<BookCar>();
		
		try {
			ArrayList<?> list = (ArrayList<?>)loadFromSer("Users.ser");
			for (Object x : list) {
				listUsers.add((User) x);
			}
		} catch (Exception e) {
			// file is empty - do nothing
		}
		
		try {
			ArrayList<?> list = (ArrayList<?>)loadFromSer("Branches.ser");
			for (Object x : list) {
				listBranches.add((Branch) x);
			}
		} catch (Exception e) {
			// file is empty - do nothing
		}
		
		try {
			ArrayList<?> list = (ArrayList<?>)loadFromSer("Cars.ser");
			for (Object x : list) {
				listCars.add((Car) x);
			}
		} catch (Exception e) {
			// file is empty - do nothing
		}
		
		try {
			ArrayList<?> list = (ArrayList<?>)loadFromSer("BookedCars.ser");
			for (Object x : list) {
				listBookedCars.add((BookCar) x);
			}
		} catch (Exception e) {
			// file is empty - do nothing
		}
		
		MainPageFrame mainpage = new MainPageFrame();
		mainpage.setSize(700,400);
		mainpage.setVisible(true);

	}
	
	/**
	 * Reading information from serializated files
	 * @param filename - file name to fetch info from
	 * @return object that contains all the information
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private static Object loadFromSer(String filename) throws IOException, ClassNotFoundException {
		Object serObject = null;
		File serFile = new File(filename);
		serFile.createNewFile();
		fis = new FileInputStream(filename);
		in = new ObjectInputStream(fis);
		serObject = in.readObject();
		in.close();
		return serObject;
	}
	
	
	/**
	 * Checking if string is a number
	 * @param str
	 * @return boolean value by the condition
	 */
	public static boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
	}
	
	/**
	 * Checking if string is a correct date format
	 * @param dateStr
	 * @return boolean value by the condition
	 */
    public static boolean isValidDate(String dateStr) {
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

}

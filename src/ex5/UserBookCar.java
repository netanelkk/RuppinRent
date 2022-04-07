package ex5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.*;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Page for renting car
 * allows to find between dates, filter, and order
 * @author netanel & guy
 *
 */

public class UserBookCar extends JFrame  {


	private static final long serialVersionUID = 1L;
	
	
	private JScrollPane scrollPane;
	private JTable table;
	private Object[][] data;
	private ArrayList<Car> listCars;
	private JTextField rentFrom;
	private JTextField rentTo;
	private JButton findBetween;
	private Choice searchByBranch;
	private Choice searchByCategory;
	private Choice searchByGear;
	private JTextField searchByYear;
	private JTextField searchByMaxPrice;
	private JButton filterButton;
	private ArrayList<BookCar> listBookedCars;
	private ArrayList<BookCar> listBookedCarsInDates;
	private String category, gear;
	private int branch, manuYear, maxprice;
	private FromTo fromToSearch;
	private String[] columnNames = {"Model",
            "Sub Model",
            "Manufactor Year",
            "Category",
            "Gear",
            "Rental Price (day)",
            "Branch",
            "CarID"};
	
	public UserBookCar() {
		super("List Of Cars");
	
		rentFrom = new JTextField(12);
		rentTo = new JTextField(12);

		findBetween = new JButton("Find");
		filterButton = new JButton("Filter");
		
		searchByBranch = new Choice();
		searchByCategory = new Choice();
		searchByGear = new Choice();
		searchByYear = new JTextField(10);
		searchByMaxPrice = new JTextField(5);
		
		// fetch list of cars
		listCars = MainClass.listCars;
		
		// add values to all choices
		initChoices();
		
		// fetch list of booked cars
		listBookedCars = MainClass.listBookedCars;
		
		// will include list of booked cars, between given dates
    	listBookedCarsInDates = new ArrayList<BookCar>();
    	
    	fromToSearch = null;
				
		data = new Object[0][0];
		
		// reseting all search filters and fetching cars
		resetFilters();
		loadCars(branch,category,gear,manuYear,maxprice);

		
		findBetween.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FromTo fromTo = new FromTo(rentFrom.getText(), rentTo.getText());
				
				if(!MainClass.isValidDate(rentFrom.getText()) || !MainClass.isValidDate(rentTo.getText())) {
					JOptionPane.showMessageDialog (null
							, "Enter dates in correct format (dd/MM/yyyy)"
							, "Error"
							, JOptionPane.ERROR_MESSAGE );
				}else {
					int dateDiff = 0;
					try {
						dateDiff = fromTo.dateDiff();
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					
					if(dateDiff <= 0) {
						JOptionPane.showMessageDialog (null
								, "Enter valid date range"
								, "Error"
								, JOptionPane.ERROR_MESSAGE );
					}else {
							updateCarsByDates(rentFrom.getText(),rentTo.getText());
					}
				}
					
			}
		});
		
		filterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetFilters();
				
				if(searchByCategory.getItem(searchByCategory.getSelectedIndex()) != "All") {
					category = searchByCategory.getItem(searchByCategory.getSelectedIndex());
				}
				
				if(searchByGear.getItem(searchByGear.getSelectedIndex()) != "All") {
					gear = searchByGear.getItem(searchByGear.getSelectedIndex());
				}
				
				if(searchByBranch.getItem(searchByBranch.getSelectedIndex()) != "All") {
					branch = Integer.parseInt(searchByBranch.getItem(searchByBranch.getSelectedIndex()).split(" - ")[0].substring(1));
				}
				
				if(searchByYear.getText().length() > 0 && MainClass.isNumeric(searchByYear.getText())) {
					manuYear =  Integer.parseInt(searchByYear.getText());
				}
				
				if(searchByMaxPrice.getText().length() > 0 && MainClass.isNumeric(searchByMaxPrice.getText())) {
					maxprice =  Integer.parseInt(searchByMaxPrice.getText());
				}
				
				loadCars(branch,category,gear,manuYear,maxprice);
				
				remove(scrollPane);
				initTable();
				add(scrollPane);
		       	revalidate();
		       	

			}
		});

		initTable();
		init();

	}
	
	/**
	 * Building the frame interface
	 */
	private void init() {
		setLayout(new FlowLayout());
		JPanel fromToSearch = new JPanel(new FlowLayout());
		fromToSearch.add(new JLabel("From Date:",SwingConstants.CENTER));
		fromToSearch.add(rentFrom);
		fromToSearch.add(new JLabel("To Date:",SwingConstants.CENTER));
		fromToSearch.add(rentTo);
		findBetween.setBackground(Color.black);
		findBetween.setForeground(Color.white);
		findBetween.setFont(new Font("Verdana", Font.PLAIN, 18));
		fromToSearch.add(findBetween);
		
		JPanel filterSearch1 = new JPanel(new FlowLayout());
		filterSearch1.add(new JLabel("Branch:",SwingConstants.CENTER));
		filterSearch1.add(searchByBranch);
		filterSearch1.add(new JLabel("Category:",SwingConstants.CENTER));
		filterSearch1.add(searchByCategory);
		
		JPanel filterSearch2 = new JPanel(new FlowLayout());
		filterSearch2.add(new JLabel("Gear:",SwingConstants.CENTER));
		filterSearch2.add(searchByGear);
		filterSearch2.add(new JLabel("Manufactor Year:",SwingConstants.CENTER));
		filterSearch2.add(searchByYear);
		filterSearch2.add(new JLabel("Max price:",SwingConstants.CENTER));
		filterSearch2.add(searchByMaxPrice);
		filterButton.setBackground(Color.black);
		filterButton.setForeground(Color.white);
		filterButton.setFont(new Font("Verdana", Font.PLAIN, 18));
		filterSearch2.add(filterButton);
		
		add(fromToSearch);
		add(filterSearch1);
		add(filterSearch2);
		add(scrollPane);
		pack();

	}
	
	/**
	 * Adds all car data to the table, and creates a listener for press on a car to book it
	 */
	private void initTable() {
		table = new JTable(data, columnNames);
		table.setPreferredScrollableViewportSize(new Dimension(650, 300));
		table.setFillsViewportHeight(true);
		table.setEnabled(false);

		table.getColumnModel().getColumn(7).setMinWidth(0);
		table.getColumnModel().getColumn(7).setMaxWidth(0);

		scrollPane = new JScrollPane(table);

		table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (fromToSearch == null) {
					JOptionPane.showMessageDialog(null
							, "Please choose dates first"
							, "Notice"
							, JOptionPane.INFORMATION_MESSAGE);
				} else {
					int row = table.rowAtPoint(e.getPoint());
					
					int days = 0;
					try {
						days = fromToSearch.dateDiff();
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					
					int cost = days * Integer.parseInt(data[row][5].toString());
					
					int reply = JOptionPane.showConfirmDialog(null, "Do you want to reserve " + data[row][0].toString() + " " + data[row][1].toString()
							+ " for " + cost + "₪ ?",
							  "Are You Sure?",
							  JOptionPane.YES_NO_OPTION);

					if (reply == JOptionPane.YES_OPTION) {
						BookCar reservation = new BookCar(Integer.parseInt(data[row][7].toString()), MainClass.myUser.getEmail(), fromToSearch);
						saveBookCarToFile(reservation);
						JOptionPane.showMessageDialog(null
								, "Car booked successfully!"
								, "Success"
								, JOptionPane.INFORMATION_MESSAGE);
						updateCarsByDates(rentFrom.getText(), rentTo.getText());
					}

				}
			}
		});

	}

	/**
	 * Recieves filter parameters and building data variable with cars that fit
	 * @param branch
	 * @param category
	 * @param gear
	 * @param manuYear
	 * @param maxprice
	 */
	private void loadCars(int branch, String category, String gear, int manuYear, int maxprice) {
		int count = 0;
		
		// counting how many rows needed
		for (int i = 0; i < listCars.size(); i++) {
			if(!carIsBooked(listCars.get(i).getCarID())) {
				count++;
			}
		}
		
		if(listCars.size() > 0) {
			data = new Object[count][7];
		}
		
		boolean passedFilter; // true - car is matches all filters
		int index = 0;
		
		for (int i = 0; i < listCars.size(); i++) {
			passedFilter = true;
			if(branch != -1) {
				if(branch != listCars.get(i).getBranchID()) {
					passedFilter = false;
				}
			}
			
			if(category != null) {
				if(!listCars.get(i).getCategory().equals(category)) {
					passedFilter = false;
				}
			}
			
			if(gear != null) {
				if(!listCars.get(i).getGear().equals(gear)) {
					passedFilter = false;
				}
			}
			
			if(manuYear != -1) {
				if(listCars.get(i).getManuYear() != manuYear) {
					passedFilter = false;
				}
			}
			
			if(maxprice != -1) {
				if(listCars.get(i).getRentPrice() > maxprice) {
					passedFilter = false;
				}
			}
				
			if(passedFilter && !carIsBooked(listCars.get(i).getCarID())) {
				   Object[] row = { listCars.get(i).getModel(), listCars.get(i).getSubModel(),
						   listCars.get(i).getManuYear(), listCars.get(i).getCategory(), listCars.get(i).getGear(),
						   listCars.get(i).getRentPrice(), findBranch(listCars.get(i).getBranchID()), listCars.get(i).getCarID() };
				   data[index] = row;
				   index++;
			}
		}
	}
	
	/**
	 * Adding all branches to branch Choice
	 */
	private void loadBranches() {
		ArrayList<Branch> listBranches = MainClass.listBranches;
		
		for (int i = 0; i < listBranches.size(); i++) {
			   int id = listBranches.get(i).getId();
			   String location = listBranches.get(i).getLocation();

			   searchByBranch.add("#"+id+" - "+location);
		}
	}
	
	/**
	 * Adding options to all Choices
	 */
	private void initChoices() {
		searchByCategory.add("All");
		searchByCategory.add("Mini");    
		searchByCategory.add("Sedan");    
		searchByCategory.add("Executive"); 
		searchByCategory.add("SUV"); 
		
		searchByGear.add("All");
		searchByGear.add("Automatic");    
		searchByGear.add("Manual");    
		searchByGear.add("Robotic"); 

		searchByBranch.add("All");
		loadBranches();
	}
	
	
	/**
	 * Searches branch location by branch id
	 * @param bid
	 * @return brach location if found, if not it wil show "unknown"
	 */
	private String findBranch(int bid) {
	    for (Branch currentBranch : MainClass.listBranches) {
	    	if(currentBranch.getId() == bid) {
	    		return currentBranch.getLocation();
	    	}
	    }
	    return "Unknown";
	}
	
	
	/**
	 * Adding booked car to ArrayList, and saving it into the file
	 * @param bc - bookcar object
	 */
    private void saveBookCarToFile(BookCar bc) {
		String filename = "BookedCars.ser";
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		listBookedCars.add(bc);
		
		try {
			fos = new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);
			out.writeObject(listBookedCars);
			out.close();
			MainClass.listBookedCars = listBookedCars;
		}
		catch(IOException ex){
			ex.printStackTrace();
		}

    }
    
    /**
     * Updating "listBookedCarsInDates" with all booked cars between given range
     * @param from - from date
     * @param to - to date
     * @throws Exception
     */
    private void updateListBookedCarsInDates(String from, String to) throws Exception  {
    	listBookedCarsInDates = new ArrayList<BookCar>();
    	for(BookCar currentBook : listBookedCars) {
    		FromTo currentDates = currentBook.getBookingDates();
    		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US);
    		LocalDate fromCurrent = LocalDate.parse(currentDates.getFrom(), formatter);
    		LocalDate fromSearch = LocalDate.parse(from, formatter);
    		LocalDate toCurrent = LocalDate.parse(currentDates.getTo(), formatter);
    		LocalDate toSearch = LocalDate.parse(to, formatter);
    		
    		if(!(toCurrent.isBefore(fromSearch) || fromCurrent.isAfter(toSearch))) {
    			listBookedCarsInDates.add(currentBook);
    		}
    		
    	}
    	fromToSearch = new FromTo(from,to);
    }
    
    /**
     * Checking if car is booked, by searching it in "listBookedCarsInDates"
     * @param carID
     * @return  boolean value by the condition
     */
    private boolean carIsBooked(int carID) {
    	for(BookCar currentBook : listBookedCarsInDates) {
    		if(currentBook.getCarID() == carID) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * Updating table of cars with cars that available between 2 dates
     * @param from
     * @param to
     */
    private void updateCarsByDates(String from, String to) {
    	
		try {
			updateListBookedCarsInDates(from,to);
			loadCars(branch,category,gear,manuYear,maxprice);
			remove(scrollPane);
			initTable();
			add(scrollPane);
	       	revalidate();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog (null
					, "Enter dates in correct format (Example: 11/01/2022 and not 11/1/2022)"	// Dialog text
					, "Error" 	// Dialog title
					, JOptionPane.ERROR_MESSAGE );
		}
    }
    
    /**
     * Reseting all filter parameters
     */
    private void resetFilters() {
		category = null;
		gear = null;
		branch = -1;
		manuYear = -1;
		maxprice = -1;
    }


}

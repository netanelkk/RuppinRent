package ex5;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Page for adding new car
 * @author netanel & guy
 *
 */

public class ManageAddCarFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JButton submit;
	private JLabel welcomeMessage;
	private JTextField carID;
	private JTextField manuYear;
	private JTextField model;
	private JTextField subModel;
	private Choice category;
	private Choice gear;
	private JTextField rentPrice;
	private Choice branch;
	private ArrayList<Car> listCars;
	
	public ManageAddCarFrame() {
		super("Add New Car");
		
		carID = new JTextField();
		manuYear = new JTextField();
		model = new JTextField();
		subModel = new JTextField();
		category = new Choice();
		gear = new Choice();
		branch = new Choice();
   
		rentPrice = new JTextField();
		
		submit = new JButton("Add New Car");
		welcomeMessage = new JLabel("Add New Car",SwingConstants.CENTER);
		welcomeMessage.setFont(new Font("Bahnschrift", Font.PLAIN, 30));
		
		category.add("Mini");    
		category.add("Sedan");    
		category.add("Executive"); 
		category.add("SUV"); 
		
		gear.add("Automatic");    
		gear.add("Manual");    
		gear.add("Robotic"); 
		
		// load all branches to branch Choice
		loadBranches();
		
		// fetch list of cars
		listCars = MainClass.listCars;
		
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String errorMessage = null;
				
				if(!MainClass.isNumeric(carID.getText()) || carID.getText().length() < 7 || carID.getText().length() > 8) {
					errorMessage = "Car id must be 7-8 numbers";
				}else if(carAlreadyExist(Integer.parseInt(carID.getText()))) {
					errorMessage = "Car with same id already exists";
				}else if(!MainClass.isNumeric(manuYear.getText())) {
					errorMessage = "Please enter valid year";
				}else if(Integer.parseInt(manuYear.getText()) < 1980 || Integer.parseInt(manuYear.getText()) > 2050) {
					errorMessage = "Please enter valid year";
				}else if(model.getText().length() < 1 || carID.getText().length() > 15) {
					errorMessage = "Model name must include 1-15 characters.";
				}else if(subModel.getText().length() < 1 || subModel.getText().length() > 15) {
					errorMessage = "Sub model name must include 1-15 characters.";
				}else if(!MainClass.isNumeric(rentPrice.getText())) {
					errorMessage = "Enter valid rental price";
				}else if(Integer.parseInt(rentPrice.getText()) <= 0) {
					errorMessage = "Enter valid rental price";
				}else if(branch.getSelectedIndex() == -1) {
					errorMessage = "Invalid branch";
				}else {
					// fetch branch id from string format of: #{branch_id} - {branch_location}
					String branchID = branch.getItem(branch.getSelectedIndex()).split(" - ")[0].substring(1);
					
					Car c = new Car(Integer.parseInt(carID.getText()),Integer.parseInt(manuYear.getText()), model.getText(),
							subModel.getText(), category.getItem(category.getSelectedIndex()),
							gear.getItem(gear.getSelectedIndex()), Integer.parseInt(rentPrice.getText()),
							Integer.parseInt(branchID));
					JOptionPane.showMessageDialog (null
							, "Car successfully added!"	// Dialog text
							, "Success" 	// Dialog title
							, JOptionPane.INFORMATION_MESSAGE );
					
					saveCarToFile(c);
					
					closeWindow();
					
					
				}
				
				if(errorMessage != null) {
					JOptionPane.showMessageDialog (null
							, errorMessage	// Dialog text
							, "Error" 	// Dialog title
							, JOptionPane.ERROR_MESSAGE );
				}
			}
		});
		
		init();

	}
	
	/**
	 * Building the frame interface
	 */
	private void init() {
		setLayout(new GridLayout(0,1));
		getContentPane().setBackground(Color.gray);
		
		JPanel form = new JPanel(new GridLayout(0,2));
		form.add(new JLabel("Car id:",SwingConstants.CENTER));
		form.add(carID);
		form.add(new JLabel("Manufactor year:",SwingConstants.CENTER));
		form.add(manuYear);
		form.add(new JLabel("Model:",SwingConstants.CENTER));
		form.add(model);
		form.add(new JLabel("Sub model:",SwingConstants.CENTER));
		form.add(subModel);
		form.add(new JLabel("Category:",SwingConstants.CENTER));
		form.add(category);
		form.add(new JLabel("Gear:",SwingConstants.CENTER));
		form.add(gear);
		form.add(new JLabel("Rental price:",SwingConstants.CENTER));
		form.add(rentPrice);
		form.add(new JLabel("Add to branch:",SwingConstants.CENTER));
		form.add(branch);
		form.setBorder(new EmptyBorder(15, 5, 0, 5));
		
		JPanel submit_flow = new JPanel(new FlowLayout());
		submit.setBackground(Color.black);
		submit.setForeground(Color.white);
		submit.setFont(new Font("Verdana", Font.PLAIN, 18));
		submit_flow.add(submit);
		
		
		add(welcomeMessage);
		add(form);
		add(submit_flow);

		pack();
	}
	
	
	/**
	 * Adding all branches to branch Choice
	 */
	private void loadBranches() {
		ArrayList<Branch> listBranches = MainClass.listBranches;
		
		for (int i = 0; i < listBranches.size(); i++) {
			   int id = listBranches.get(i).getId();
			   String location = listBranches.get(i).getLocation();

			   branch.add("#"+id+" - "+location);
		}
	}
	
	/**
	 * Adding car to ArrayList, and saving it into the file
	 * @param c - car object
	 */
    private void saveCarToFile(Car c) {
		String filename = "Cars.ser";
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		listCars.add(c);
		
		try {
			fos = new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);
			out.writeObject(listCars);
			out.close();
			MainClass.listCars = listCars;
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
    }
    
    /**
     * Checking if given car id already exists in the database
     * @param id
     * @return boolean value by the condition
     */
	private boolean carAlreadyExist(int id) {
	    for (Car checkCar : listCars) {
	        if (checkCar.getCarID() == id) {
	            return true;
	        }
	    }
	    return false;
	}
    
	/**
	 * Closing the current window
	 */
	private void closeWindow() {
		setVisible(false);
		dispose();
	}



}

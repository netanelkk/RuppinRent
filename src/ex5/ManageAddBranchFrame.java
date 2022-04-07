package ex5;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Page for adding new branches
 * @author netanel & guy
 *
 */
public class ManageAddBranchFrame extends JFrame {


	private static final long serialVersionUID = 1L;
	
	private JButton submit;
	private JLabel welcomeMessage;
	private JTextField branchID;
	private JTextField branchName;
	private JTextField branchOpenFrom;
	private JTextField branchOpenTo;
	private ArrayList<Branch> listBranches;
	
	public ManageAddBranchFrame() {
		super("Add New Branch");
		
		branchName = new JTextField();
		branchID = new JTextField();
		branchOpenFrom = new JTextField();
		branchOpenTo = new JTextField();
		submit = new JButton("Add New Branch");
		welcomeMessage = new JLabel("Add New Branch",SwingConstants.CENTER);
		welcomeMessage.setFont(new Font("sans-serif", Font.PLAIN, 24));
		
		// fetch list of branches
		listBranches = MainClass.listBranches;
		
		
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String errorMessage = null;

				FromTo openRange = new FromTo(branchOpenFrom.getText(),branchOpenTo.getText());
				
				if(!isNumeric(branchID.getText()) || branchID.getText().length() < 1 || branchID.getText().length() > 20) {
					errorMessage = "Branch id must be a number and include 1-20 characters";
				}else if(branchAlreadyExist(Integer.parseInt(branchID.getText()))) {
					errorMessage = "Branch with the same id already exist";
				} else if(branchName.getText().length() < 2 || branchName.getText().length() > 20) {
					errorMessage = "Branch name must include 2-20 characters.";
				}else if(!validateTime(openRange.getFrom()) || !validateTime(openRange.getTo())) {
					errorMessage = "Opening and closing times must be in format: HH:mm (Example: 10:00)";
				}else {
					// Calculate the minutes between the times
					int timeDiff;
					try {
						timeDiff = openRange.timeDiff();
					} catch (ParseException e1) {
						e1.printStackTrace();
						timeDiff = 0;
					}
					
					 if(timeDiff <= 0) {
							errorMessage = "Closing time must be after opening time";
						}else {
							Branch b = new Branch(Integer.parseInt(branchID.getText()), branchName.getText(),
									new FromTo(branchOpenFrom.getText(),branchOpenTo.getText()));
							JOptionPane.showMessageDialog (null
									, "Branch successfully added!"	// Dialog text
									, "Success" 	// Dialog title
									, JOptionPane.INFORMATION_MESSAGE );
							saveBranchToFile(b);
							closeWindow();
						}
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
		form.add(new JLabel("Branch id:",SwingConstants.CENTER));
		form.add(branchID);
		form.add(new JLabel("Branch name:",SwingConstants.CENTER));
		form.add(branchName);
		form.setBorder(new EmptyBorder(15, 5, 0, 5));
		
		JPanel hoursform = new JPanel(new GridLayout(0,4));
		hoursform.add(new JLabel("From:",SwingConstants.CENTER));
		hoursform.add(branchOpenFrom);
		hoursform.add(new JLabel("To:",SwingConstants.CENTER));
		hoursform.add(branchOpenTo);
		hoursform.setBorder(new EmptyBorder(15, 5, 0, 5));
		
		JPanel submit_flow = new JPanel(new FlowLayout());
		submit.setBackground(Color.black);
		submit.setForeground(Color.white);
		submit.setFont(new Font("Verdana", Font.PLAIN, 18));
		submit_flow.add(submit);
		
		add(welcomeMessage);
		add(form);
		add(hoursform);
		add(submit_flow);

		pack();
	}
	
	/**
	 * Checking if string is a number
	 * @param str
	 * @return boolean value by the condition
	 */
	private boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
	}
	
	/**
	 * Checking if given string is correct time format
	 * @param time
	 * @return boolean value by the condition
	 */
	private boolean validateTime(String time){
	      String TIME24HOURS_PATTERN = 
	                "([01]?[0-9]|2[0-3]):[0-5][0-9]";
	      Pattern pattern = Pattern.compile(TIME24HOURS_PATTERN);
	      Matcher matcher = pattern.matcher(time);
	      return matcher.matches();
	}
	
	/**
	 * Adding branch to ArrayList, and saving it into the file
	 * @param b - branch object
	 */
    private void saveBranchToFile(Branch b) {
		String filename = "Branches.ser";
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		listBranches.add(b);
		
		try {
			fos = new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);
			out.writeObject(listBranches);
			out.close();
			MainClass.listBranches = listBranches;
		}
		catch(IOException ex){
			ex.printStackTrace();
		}

    }
    
    /**
     * Checking if given branch id already exists in the database
     * @param id
     * @return boolean value by the condition
     */
	private boolean branchAlreadyExist(int id) {
	    for (Branch checkBranch : listBranches) {
	        if (checkBranch.getId() == id) {
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

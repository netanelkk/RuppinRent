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
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import java.util.ArrayList;

/**
 * Registration page for new users
 * @author netanel & guy
 *
 */
public class SignupFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JButton submit;
	private JLabel welcomeMessage;
	private JTextField firstName;
	private JTextField lastName;
	private JTextField userID;
	private JTextField email;
	private JTextField birthDate;
	private JTextField licenseYear;
	private JPasswordField password;
	private JPasswordField passwordAgain;
	private ArrayList<User> listUsers;
	
	public SignupFrame() {
		super("Sign Up");
		
		// fetch list of users
		listUsers = MainClass.listUsers;
		
		firstName = new JTextField();
		lastName = new JTextField();
		userID = new JTextField();
		email = new JTextField();
		birthDate = new JTextField();
		licenseYear = new JTextField();
		password = new JPasswordField();
		passwordAgain = new JPasswordField();
		submit = new JButton("Register");
		
		welcomeMessage = new JLabel("Sign Up",SwingConstants.CENTER);
		welcomeMessage.setFont(new Font("Bahnschrift", Font.PLAIN, 30));
		
        LocalDate current_date = LocalDate.now();
		
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String errorMessage = null;
				if(firstName.getText().length() < 2 || firstName.getText().length() > 15) {
					errorMessage = "First name must include 2-15 characters";
				}else if(lastName.getText().length() < 2 || lastName.getText().length() > 15) {
					errorMessage = "Last name must include 2-15 characters.";
				}else if(!MainClass.isNumeric(userID.getText()) || userID.getText().length() != 9) {
					errorMessage = "User id incorrect format";
				}else if(!isValidEmailAddress(email.getText())) {
					errorMessage = "Email not valid";
				}else if(emailAlreadyRegistered(email.getText())) {
					errorMessage = "The email already registered";
				}else if(!MainClass.isValidDate(birthDate.getText())) {
					errorMessage = "Birth date not valid (format: dd/mm/yyyy)";
				}else if(!MainClass.isNumeric(licenseYear.getText())) {
					errorMessage = "Please enter valid year";
				}else if(Integer.parseInt(licenseYear.getText()) < 1900 || Integer.parseInt(licenseYear.getText()) > current_date.getYear()) {
					errorMessage = "Please enter valid year";
				}else if(String.valueOf(password.getPassword()).length() < 6 || String.valueOf(password.getPassword()).length() > 25) {
					errorMessage = "Password must contain 6-25 characters";
				}else if(!String.valueOf(password.getPassword()).equals(String.valueOf(passwordAgain.getPassword()))) {
					errorMessage = "Passwords don't match";
				}else{
					User u = new User(firstName.getText(), lastName.getText(), userID.getText(),
							email.getText(), birthDate.getText(), Integer.parseInt(licenseYear.getText()), 
							String.valueOf(password.getPassword()));

					saveUserToFile(u);
					
					JOptionPane.showMessageDialog (null
							, "Registered successfully!"	// Dialog text
							, "Success" 	// Dialog title
							, JOptionPane.INFORMATION_MESSAGE );

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
		form.add(new JLabel("First name:",SwingConstants.CENTER));
		form.add(firstName);
		form.add(new JLabel("Last name:",SwingConstants.CENTER));
		form.add(lastName);
		form.add(new JLabel("ID:",SwingConstants.CENTER));
		form.add(userID);
		form.add(new JLabel("Email:",SwingConstants.CENTER));
		form.add(email);
		form.add(new JLabel("Birth date:",SwingConstants.CENTER));
		form.add(birthDate);
		form.add(new JLabel("License year:",SwingConstants.CENTER));
		form.add(licenseYear);
		form.add(new JLabel("Password:",SwingConstants.CENTER));
		form.add(password);
		form.add(new JLabel("Password Again:",SwingConstants.CENTER));
		form.add(passwordAgain);
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
	 * Checking if string is a correct email format
	 * @param email
	 * @return boolean value by the condition
	 */
	private boolean isValidEmailAddress(String email) {
        String emailformat = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
        String notemptystring = "^(?!\\s*$).+";
        Pattern pattern = Pattern.compile(notemptystring);
        Matcher matcher = pattern.matcher(email);
        if(matcher.matches()) {
            pattern = Pattern.compile(emailformat);
            matcher = pattern.matcher(email);
        	return matcher.matches();
        }
        return false;
	}
    
    /**
     * Adding user to ArrayList, and saving it into the file
     * @param u - user object
     */
    public void saveUserToFile(User u) {
		String filename = "Users.ser";
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		listUsers.add(u);
		
		try {
			fos = new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);
			out.writeObject(listUsers);
			out.close();
			MainClass.listUsers = listUsers;
		}
		catch(IOException ex){
			ex.printStackTrace();
		}

    }

    /**
     * Checking if given email is already registered
     * @param email
     * @return boolean value by the condition
     */
	private boolean emailAlreadyRegistered(String email) {
	    for (User checkUser : listUsers) {
	        if (checkUser.getEmail().equals(email)) {
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

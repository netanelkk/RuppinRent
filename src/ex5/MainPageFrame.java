package ex5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Color;

/**
 * The opening screen of the program
 * letting option to sign in or sign up
 * admin access: username: admin , password: admin
 * @author netanel & guy
 *
 */

public class MainPageFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JButton signin;
	private JButton signup;
	private JLabel welcomeMessage;
	private JTextField username;
	private JPasswordField password;
	private List<User> listUsers;
	
	public MainPageFrame() {
		super("Welcome Page");
		listUsers = new ArrayList<User>();
		
		signin = new JButton("Sign in");
		signup = new JButton("Sign up");
		welcomeMessage = new JLabel("Welcome to Ruppin Rent!",SwingConstants.CENTER);
		welcomeMessage.setFont(new Font("Bahnschrift", Font.PLAIN, 30));
		
		username = new JTextField(20);
		password = new JPasswordField(20);
		
		// fetch list of users
		listUsers = MainClass.listUsers;
		
		signin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				User myUser = findUser(username.getText(), String.valueOf(password.getPassword()));
				// open admin control
				if(username.getText().equals("admin") && String.valueOf(password.getPassword()).equals("admin")) {
					ManageMainFrame managePage = new ManageMainFrame();
					managePage.setSize(700,400);
					managePage.setVisible(true);
					closeWindow();
				// open user control
				}else if(findUser(username.getText(), String.valueOf(password.getPassword())) != null) {
					MainClass.myUser = myUser;
					UserMainFrame userPage = new UserMainFrame();
					userPage.setSize(700,400);
					userPage.setVisible(true);
					closeWindow();
				}else {
					JOptionPane.showMessageDialog (null
							, "Incorrect username or password!"	// Dialog text
							, "Error" 	// Dialog title
							, JOptionPane.ERROR_MESSAGE );
				}



			}
		});
		
		signup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SignupFrame signupframe = new SignupFrame();
				signupframe.setSize(400,700);
				signupframe.setVisible(true);

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
		
		JPanel loginPanel = new JPanel(new GridLayout(2,2));
		loginPanel.add(new JLabel("Email:",SwingConstants.CENTER));
		loginPanel.add(username);
		loginPanel.add(new JLabel("Password:",SwingConstants.CENTER));
		loginPanel.add(password);
		loginPanel.setBorder(new EmptyBorder(15, 20, 0, 20));
		
		JPanel signin_flow = new JPanel(new FlowLayout());
		signin.setBackground(Color.black);
		signin.setForeground(Color.white);
		signin.setFont(new Font("Verdana", Font.PLAIN, 18));
		signin_flow.add(signin);
		
		JPanel signup_flow = new JPanel(new FlowLayout());
		signup_flow.add(signup);
		
		
		add(welcomeMessage);
		add(loginPanel);
		add(signin_flow);
		add(signup_flow);


		pack();

	}
	
	/**
	 * Search user by email and password
	 * @param email
	 * @param password
	 * @return User object of the user that matches both parameters
	 */
	private User findUser(String email, String password) {
	    for (User checkUser : listUsers) {
	        if (checkUser.getEmail().equals(email) && checkUser.getPassword().equals(password)) {
	            return checkUser;
	        }
	    }
	    return null;
	}
	
	/**
	 * Closing the current window
	 */
	private void closeWindow() {
		setVisible(false);
		dispose();
	}

}

package ex5;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Main user page
 * Allows to view branches, and book a car
 * @author netanel & guy
 *
 */
public class UserMainFrame extends JFrame {


	private static final long serialVersionUID = 1L;
	
	private JButton openBranch;
	private JButton openCar;
	private JLabel welcomeMessage;
	private User myUser;
	
	public UserMainFrame() {
		super("User Main Page");
		
		// fetch current user object
		myUser = MainClass.myUser;
		
		openBranch = new JButton("View branches");
		openCar = new JButton("Book car");
		welcomeMessage = new JLabel("Welcome "+ myUser.getFirstName() + " " + myUser.getLastName() +"!",SwingConstants.CENTER);
		welcomeMessage.setFont(new Font("Bahnschrift", Font.PLAIN, 30));
		
		openBranch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserViewBranches branchFrame = new UserViewBranches();
				branchFrame.setSize(600,300);
				branchFrame.setVisible(true);
			}
		});
		
		openCar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserBookCar carFrame = new UserBookCar();
				carFrame.setSize(700,500);
				carFrame.setVisible(true);
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
		
		JPanel buttons = new JPanel(new FlowLayout());
		openBranch.setBackground(Color.black);
		openCar.setBackground(Color.black);
		
		openBranch.setForeground(Color.white);
		openCar.setForeground(Color.white);
		
		openBranch.setFont(new Font("Verdana", Font.PLAIN, 18));
		openCar.setFont(new Font("Verdana", Font.PLAIN, 18));
		
		buttons.add(openBranch);
		buttons.add(openCar);
		
		
		add(welcomeMessage);
		add(buttons);

		pack();
	}
	
}

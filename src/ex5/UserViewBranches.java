package ex5;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Page to view all branches and their info
 * @author netanel & guy
 *
 */
public class UserViewBranches extends JFrame  {


	private static final long serialVersionUID = 1L;
	private JLabel welcomeMessage;
	private JScrollPane scrollPane;
	private JTable table;
	private Object[][] data;
	private ArrayList<Branch> listBranches;
	private final String[] columnNames = {"Number",
            "Name",
            "Open From",
            "To"};
	
	public UserViewBranches() {
		super("List Of Branches");
	
		welcomeMessage = new JLabel("List Of Branches",SwingConstants.CENTER);
		welcomeMessage.setFont(new Font("Bahnschrift", Font.PLAIN, 30));
		
		// fetch list of branches
		listBranches = MainClass.listBranches;
		
		// empty "data" object
		data = new Object[0][0];
		
		// load branches info into "data"
		loadBranches();

		table = new JTable(data,columnNames);
		table.setPreferredScrollableViewportSize(new Dimension(500, 150));
        table.setFillsViewportHeight(true);
        table.setEnabled(false);

       	scrollPane = new JScrollPane(table);

		init();
		
	}
	
	/**
	 * Building the frame interface
	 */
	private void init() {
		setLayout(new GridLayout(0,1));
		getContentPane().setBackground(Color.gray);
		add(welcomeMessage);
		scrollPane.setBorder(new EmptyBorder(15, 5, 0, 5));
		add(scrollPane);
		pack();

	}

	/**
	 * Going through all branches, and adding them to "data"
	 */
	private void loadBranches() {
		if(listBranches.size() > 0) {
			data = new Object[listBranches.size()][4];
		}
		
		for (int i = 0; i < listBranches.size(); i++) {
			   int id = listBranches.get(i).getId();
			   String location = listBranches.get(i).getLocation();
			   FromTo openfromto = listBranches.get(i).getOpeningHours();
			   
			   Object[] row = { id, location, openfromto.getFrom(), openfromto.getTo() };
			   
			   data[i] = row;
		}
	}




}

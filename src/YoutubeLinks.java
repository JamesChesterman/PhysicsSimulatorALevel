import java.util.ArrayList;
import java.util.List;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

public class YoutubeLinks extends JFrame {
	
	private JTable LinksTable(){
		String[] columns = new String[]{
				"Name", "URL", "Username"
		};

		SQLMethods sqlMethods = new SQLMethods();
		//Need to use an ArrayList as a parameter and add values to this ArrayList for the columns that the method is going to return
		JTable table = new JTable();
		//The SQL query gets the link name, URL and username for whoever added it. Does inner join to get username from Users table. Orders by the link name ascending (so in alphabetical order)
		table = sqlMethods.makeTable("SELECT Links.Name, Links.URL, Users.Username FROM Links INNER JOIN Users ON Links.UserID = Users.UserID ORDER BY Links.Name ASC", columns, table);
		
		return table;
		
		
	}
	
	
	private void SetUpWindow(){
		//Makes new table from the method
		JTable table = LinksTable();
		//Adds the table to a scroll pane so you can scroll
		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(10, 10, 400, 300);
		
		JButton backButton = new JButton("<<Back");
		backButton.setBounds(420, 280, 150, 30);
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == backButton){
					//Goes back to the main menu
					MainMenuScreen mainMenuScreen = new MainMenuScreen();
					mainMenuScreen.setVisible(true);
					setVisible(false);
				}
			}
		});
		JButton addButton = new JButton("Add New Link");
		addButton.setBounds(420, 240, 150, 30);
		addButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == addButton){
					//Opens addNewLink screen
					AddNewLink addNewLink = new AddNewLink();
					addNewLink.setVisible(true);
					setVisible(false);
				}
			}
		});
		
		//Adds the pane and therefore the table to the window
		add(pane);
		add(backButton);
		add(addButton);
		
		setTitle("A-Level Physics Simulator - Youtube Links");
		setSize(600, 400);
        setLocationRelativeTo(null);
        //Gets icon image from image package and displays it as the icon for the frame
        setIconImage(Toolkit.getDefaultToolkit().getImage(UserLoginScreen.class.getResource("images/ALPSicon.png")));
        //Need setLayout so that you can change the positions of the objects on the frame
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        
	}
	public YoutubeLinks() {
		// TODO Auto-generated constructor stub
		SetUpWindow();
	}

}

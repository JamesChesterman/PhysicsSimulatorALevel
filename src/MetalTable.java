import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MetalTable extends JFrame {
	private JTable MetalTable(){
		String[] columns = new String[]{
				"Name", "Work Function", "Username"
		};
		SQLMethods sqlMethods = new SQLMethods();
		JTable table = new JTable();
		table = sqlMethods.makeTable("SELECT Metals.Name, Metals.WorkFunction, Users.Username FROM Metals INNER JOIN Users ON Metals.UserID = Users.UserID ORDER BY Metals.Name ASC", columns, table);
		
		return table;
	}
	
	private void SetUpWindow(){
		//Makes new table from the method
		JTable table = MetalTable();
		//Adds the table to a scroll pane so you can scroll
		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(10, 10, 400, 300);
		
		JButton addButton = new JButton("Add New Metal");
		addButton.setBounds(420, 240, 150, 30);
		addButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == addButton){
					//Opens add new metal screen
					AddMetal addMetal = new AddMetal();
					addMetal.setVisible(true);
					setVisible(false);
				}
			}
		});
		JButton changeButton = new JButton("Change to Selected");
		changeButton.setBounds(420, 200, 150, 30);
		
		JButton backButton = new JButton("<<Back");
		backButton.setBounds(420, 280, 150, 30);
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == backButton){
					//NEED TO MAKE IT SO IT WILL GO TO PHOTOELECTRIC SCREEN IF IT'S BEEN OPENED
					MainMenuScreen mainMenuScreen = new MainMenuScreen();
					mainMenuScreen.setVisible(true);
					setVisible(false);
				}
			}
		});
		
		add(backButton);
		add(changeButton);
		add(addButton);
		add(pane);
		
		setTitle("A-Level Physics Simulator - Metal Table");
		setSize(600, 400);
	    setLocationRelativeTo(null);
	    //Gets icon image from image package and displays it as the icon for the frame
	    setIconImage(Toolkit.getDefaultToolkit().getImage(UserLoginScreen.class.getResource("images/ALPSicon.png")));
	    //Need setLayout so that you can change the positions of the objects on the frame
	    setLayout(null);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setVisible(true);
	}
	
	
	public MetalTable() {
		// TODO Auto-generated constructor stub
		SetUpWindow();
	}

}

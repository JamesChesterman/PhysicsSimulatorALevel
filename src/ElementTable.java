import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.*;

public class ElementTable extends JFrame {
	public static String newName;
	public static String newLevel1;
	public static String newLevel2;
	public static String newLevel3;
	
	private JTable ElementTable(){
		String[] columns = new String[]{
				"Name", "Level 1", "Level 2", "Level 3", "Username"
		};
		SQLMethods sqlMethods = new SQLMethods();
		JTable table = new JTable();
		table = sqlMethods.makeTable("SELECT EnergyLevels.ElementName, EnergyLevels.Level1, EnergyLevels.Level2, EnergyLevels.Level3, Users.Username FROM EnergyLevels INNER JOIN Users ON EnergyLevels.UserID = Users.UserID ORDER BY EnergyLevels.ElementName ASC", columns, table);
		
		return table;
	}
	
	private void SetUpWindow(){
		//Makes new table from the method
		JTable table = ElementTable();
		//Adds the table to a scroll pane so you can scroll
		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(10, 10, 400, 300);
				
		JButton backButton = new JButton("<<Back");
		backButton.setBounds(420, 280, 150, 30);
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == backButton){
					//Goes back to the EnergyLevel diagram or goes back to main menu
					if(EnergyLevels.energyLvlOpen == true){
						EnergyLevels energyLevels = new EnergyLevels();
						energyLevels.setVisible(true);
						setVisible(false);
					}else{
						MainMenuScreen mainMenuScreen = new MainMenuScreen();
						mainMenuScreen.setVisible(true);
						setVisible(false);
					}
					
				}
			}
		});
		JButton addButton = new JButton("Add New Element");
		addButton.setBounds(420, 240, 150, 30);
		addButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == addButton){
					//Opens add new element screen
					AddElement addElement = new AddElement();
					addElement.setVisible(true);
					setVisible(false);
				}
			}
		});
		JButton changeButton = new JButton("Change to Selected");
		changeButton.setBounds(420, 200, 150, 30);
		changeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == changeButton){
					//Changes element to the selected row
					//NEED TO DO THIS BIT
					int rowInt = table.getSelectedRow();
					if(rowInt == -1){
						//no row selected
						JOptionPane.showMessageDialog(null,  "To change to a new element, select one of the values in the table and the whole row will be selected");
					}else{
						//Gets the name from the row of the table that has been selected
						Object newNameObj = table.getModel().getValueAt(rowInt, 0);
						newName = newNameObj.toString();
						
						Object newLevel1Obj = table.getModel().getValueAt(rowInt, 1);
						newLevel1 = newLevel1Obj.toString();
						
						Object newLevel2Obj = table.getModel().getValueAt(rowInt, 2);
						newLevel2 = newLevel2Obj.toString();
						
						Object newLevel3Obj = table.getModel().getValueAt(rowInt,  3);
						newLevel3 = newLevel3Obj.toString();
						
						//Go to the energy levels simulation
						EnergyLevels energyLevels = new EnergyLevels();
						energyLevels.setVisible(true);
						setVisible(false);
					}
					
				}
			}
		});
				
		//Adds the pane and therefore the table to the window
		add(changeButton);
		add(pane);
		add(backButton);
		add(addButton);
		
		setTitle("A-Level Physics Simulator - Element Table");
		setSize(600, 400);
        setLocationRelativeTo(null);
        //Gets icon image from image package and displays it as the icon for the frame
        setIconImage(Toolkit.getDefaultToolkit().getImage(UserLoginScreen.class.getResource("images/ALPSicon.png")));
        //Need setLayout so that you can change the positions of the objects on the frame
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
	}
	

	public ElementTable() {
		// TODO Auto-generated constructor stub
		SetUpWindow();
	}

}

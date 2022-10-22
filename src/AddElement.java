import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AddElement extends JFrame{
	private String username;
	private String userID;
	private Float float1;
	private Float float2;
	private Float float3;
	
	private void SubmitButtonPressed(String elementName, String level1, String level2, String level3){
		//Gets the current username by using a global variable declared in UserLoginScreen class
		username = UserLoginScreen.currentUsername;
		SQLMethods sqlMethods = new SQLMethods();
				
		//The column number is 1 because sql tables start at 1
		userID = sqlMethods.getCell("SELECT UserID FROM Users WHERE Username = '" + username + "'", 1);
		
		//Checks to see if the name is bigger than three and that each level starts with a minus
		if(elementName.length() > 3 && level1.startsWith("-") && level2.startsWith("-") && level3.startsWith("-")){
			try{
				float1 = Float.valueOf(level1);
				float2 = Float.valueOf(level2);
				float3 = Float.valueOf(level3);
				if(float1 > -100 && float2 > -100 && float3 > -100){
					if(float1 < float2 && float2 < float3){
						//Use the level variables rather than floats so that they can be put into the query itself
						sqlMethods.insertIntoTable("INSERT INTO EnergyLevels(ElementName, Level1, Level2, Level3, UserID) VALUES('" + elementName + "', " + level1 + ", " + level2 + ", " + level3 + ", " + userID.toString() + ")");
						ElementTable elementTable = new ElementTable();
						elementTable.setVisible(true);
						setVisible(false);
					}else{
						JOptionPane.showMessageDialog(null,  "Ensure that the level 1 is smaller than level 2 and level 2 is smaller than level 3");
					}
				}else{
					JOptionPane.showMessageDialog(null, "Ensure that levels 1 - 3 are above -100");
				}
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "Ensure that levels 1 - 3 are negative numbers");
			}
		}else{
			JOptionPane.showMessageDialog(null,  "Ensure that levels 1 - 3 are negative numbers and element name has more than 3 characters");
		}
	}
	
	private void SetUpWindow(){
		JLabel elementNameLabel = new JLabel("Element Name:");
		elementNameLabel.setBounds(10, 10, 90, 30);
		JTextField elementTextField = new JTextField("", 30);
		elementTextField.setBounds(100, 10, 200, 30);
		JLabel level1Label = new JLabel("Level 1:");
		level1Label.setBounds(10, 50, 90, 30);
		JTextField level1TextField = new JTextField("", 10);
		level1TextField.setBounds(100, 50, 200, 30);
		JLabel level2Label = new JLabel("Level 2:");
		level2Label.setBounds(10, 90, 90, 30);
		JTextField level2TextField = new JTextField("", 10);
		level2TextField.setBounds(100, 90, 200, 30);
		JLabel level3Label = new JLabel("Level 3:", 10);
		level3Label.setBounds(10, 130, 90, 30);
		JTextField level3TextField = new JTextField("", 10);
		level3TextField.setBounds(100, 130, 200, 30);
		JLabel unitsTip = new JLabel("Enter all level values in electron volts (eV)");
		unitsTip.setBounds(10, 170, 300, 30);
		
		JButton backButton = new JButton("<<Back");
		backButton.setBounds(10, 210,100, 30);
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == backButton){
					//Goes back to element table
					ElementTable elementTable = new ElementTable();
					elementTable.setVisible(true);
					setVisible(false);
				}
			}
		});
		
		JButton submitButton = new JButton("Submit");
		submitButton.setBounds(120, 210, 100, 30);
		submitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == submitButton){
					//Submits the information into the table
					SubmitButtonPressed(elementTextField.getText(), level1TextField.getText(), level2TextField.getText(), level3TextField.getText());
				}
			}
		});
		
		add(elementNameLabel);
		add(elementTextField);
		add(level1Label);
		add(level1TextField);
		add(level2Label);
		add(level2TextField);
		add(level3Label);
		add(level3TextField);
		add(backButton);
		add(submitButton);
		add(unitsTip);
		
		setTitle("A-Level Physics Simulator - Add New Element");
		setSize(320, 300);
        setLocationRelativeTo(null);
        //Gets icon image from image package and displays it as the icon for the frame
        setIconImage(Toolkit.getDefaultToolkit().getImage(UserLoginScreen.class.getResource("images/ALPSicon.png")));
        //Need setLayout so that you can change the positions of the objects on the frame
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
	}

	public AddElement() {
		// TODO Auto-generated constructor stub
		SetUpWindow();
	}

}

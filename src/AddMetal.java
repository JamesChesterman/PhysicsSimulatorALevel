import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AddMetal extends JFrame{
	private String username;
	private String userID;
	private float workFunctionFloat;
	
	private void SubmitButtonPressed(String metalName, String workFunction){
		username = UserLoginScreen.currentUsername;
		
		SQLMethods sqlMethods = new SQLMethods();
		//The column number is 1 because sql tables start at 1
		userID = sqlMethods.getCell("SELECT UserID FROM Users WHERE Username = '" + username + "'", 1);
		
		if(metalName.length() > 3){
			try{
				workFunctionFloat = Float.valueOf(workFunction);
				if(workFunctionFloat < 100){
					sqlMethods.insertIntoTable("INSERT INTO Metals(Name, WorkFunction, UserID) VALUES('" + metalName + "', " + workFunction + ", " + userID.toString() + ")");
					MetalTable metalTable = new MetalTable();
					metalTable.setVisible(true);
					setVisible(false);
				}else{
					JOptionPane.showMessageDialog(null, "Make sure work function is less than 100");
				}
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "Make sure work function is a number");
			}
		}else{
			JOptionPane.showMessageDialog(null, "Make sure metal name is longer than 3 characters");
		}
		
	}
	
	private void SetUpWindow(){
		JLabel metalNameLabel = new JLabel("Metal Name:");
		metalNameLabel.setBounds(10, 10, 90, 30);
		JTextField metalTextField = new JTextField("", 30);
		metalTextField.setBounds(100, 10, 200, 30);
		JLabel workFunctionLabel = new JLabel("Work Function:");
		workFunctionLabel.setBounds(10, 50, 90, 30);
		JTextField workFunctionTextField = new JTextField("", 10);
		workFunctionTextField.setBounds(100, 50, 200, 30);
		JLabel unitLabel = new JLabel("eV");
		unitLabel.setBounds(310, 50, 30, 30);
		JButton backButton = new JButton("<<Back");
		backButton.setBounds(10, 90,100, 30);
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == backButton){
					//Goes back to metal table
					MetalTable metalTable = new MetalTable();
					metalTable.setVisible(true);
					setVisible(false);
				}
			}
		});
		JButton submitButton = new JButton("Submit");
		submitButton.setBounds(120, 90, 100, 30);
		submitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == submitButton){
					//Submits the information into the table
					SubmitButtonPressed(metalTextField.getText(), workFunctionTextField.getText());
				}
			}
		});
		
		add(metalNameLabel);
		add(metalTextField);
		add(workFunctionLabel);
		add(workFunctionTextField);
		add(unitLabel);
		add(backButton);
		add(submitButton);
		
		setTitle("A-Level Physics Simulator - Add New Metal");
		setSize(350, 300);
        setLocationRelativeTo(null);
        //Gets icon image from image package and displays it as the icon for the frame
        setIconImage(Toolkit.getDefaultToolkit().getImage(UserLoginScreen.class.getResource("images/ALPSicon.png")));
        //Need setLayout so that you can change the positions of the objects on the frame
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
	}

	public AddMetal() {
		// TODO Auto-generated constructor stub
		SetUpWindow();
	}

}

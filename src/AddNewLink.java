import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class AddNewLink extends JFrame{
	private String userID;
	private String username;
	private List<String> listOfUserIDs;
	
	
	private void SubmitButtonPressed(String name, String URL){
		//Gets the current username by using a global variable declared in UserLoginScreen class
		username = UserLoginScreen.currentUsername;
		SQLMethods sqlMethods = new SQLMethods();
		
		//The column number is 1 because sql tables start at 1
		userID = sqlMethods.getCell("SELECT UserID FROM Users WHERE Username = '" + username + "'", 1);

		if(name.length() > 1 && name.length() < 20 && URL.length() > 5 && URL.length() < 40){
			sqlMethods.insertIntoTable("INSERT INTO Links(Name, URL, UserID) VALUES('" + name + "', '" + URL + "', " + userID.toString() + ")");
			//Goes back to link table
			YoutubeLinks youtubeLinks = new YoutubeLinks();
			youtubeLinks.setVisible(true);
			setVisible(false);
		}
	}
	
	private void SetUpWindow(){
		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setBounds(10, 10, 70, 30);
		JTextField nameTextField = new JTextField("", 20);
		nameTextField.setBounds(80, 10, 200, 30);
		JLabel urlLabel = new JLabel("URL:");
		urlLabel.setBounds(10, 50, 70, 30);
		JTextField urlTextField = new JTextField("", 50);
		urlTextField.setBounds(80, 50, 200, 30);
		JButton submitButton = new JButton("Submit");
		submitButton.setBounds(80, 100, 200, 30);
		submitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == submitButton){
					SubmitButtonPressed(nameTextField.getText(), urlTextField.getText());
				}
			}
		});
		JButton backButton = new JButton("<<Back");
		backButton.setBounds(80, 140, 200, 30);
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == backButton){
					//Goes back to links table
					YoutubeLinks youtubeLinks = new YoutubeLinks();
					youtubeLinks.setVisible(true);
					setVisible(false);
					
				}
			}
		});
		
		add(nameLabel);
		add(nameTextField);
		add(urlLabel);
		add(urlTextField);
		add(submitButton);
		add(backButton);
		
		
		setTitle("A-Level Physics Simulator - Add New Link");
		setSize(600, 400);
        setLocationRelativeTo(null);
        //Gets icon image from image package and displays it as the icon for the frame
        setIconImage(Toolkit.getDefaultToolkit().getImage(UserLoginScreen.class.getResource("images/ALPSicon.png")));
        //Need setLayout so that you can change the positions of the objects on the frame
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
	}
	
	public AddNewLink() {
		// TODO Auto-generated constructor stub
		SetUpWindow();
	}

}

import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.util.List;
import java.util.ArrayList;

public class UserSignUpScreen extends JFrame{
	private List<String> usernamesList = new ArrayList<String>();
	private JLabel errorLabel = new JLabel("");
	int x;
	private boolean checkPassword;
	//symbols doesn't include " as that will be counted as a character not a string
	String[] symbols = {"!", "£", "$", "%", "^", "&", "*", "(", ")", "_", "-", "=", "+", "[", "]", "{", "}", ";",":","'","@","#","~",",","<",".",">","?","/"}; 
	
	private void SignUpButtonPressed(String username, String password, String confirmPassword){
		SQLMethods sqlMethods = new SQLMethods();
		usernamesList = sqlMethods.getColumnData(usernamesList, "SELECT Username FROM Users", "Username");
		if(username.length() >5){
			if(usernamesList.contains(username)){
				errorLabel.setText("Username already taken, try another.");
			}else{
				//Checking to see if the password + confirmPassword fields are the same
				if(password.equals(confirmPassword)){
					checkPassword = false;
					//Checks through each symbol to see if it's in the password
					for(x=0;x<symbols.length;x++){
						if(password.contains(symbols[x])){
							checkPassword = true;
						}
					}
					if(checkPassword){
						//To check if the length of the password is 10 letters or more
						if(password.length() > 10){
							sqlMethods.insertIntoTable("INSERT INTO Users(Username, Password) VALUES('" + username + "', '" + password + "')");
							UserLoginScreen userLoginScreen = new UserLoginScreen();
							//Need this next line otherwise the Login screen will open but just be empty
							userLoginScreen.UserLoginScreen();
		        			userLoginScreen.setVisible(true);
		        			setVisible(false);
						}else{
							errorLabel.setText("Password needs to be over 10 characters");
						}
					}else{
						errorLabel.setText("Password needs to contain a symbol.");
					}
					
				}else{
					errorLabel.setText("Passwords do not match, try again.");
				}
			}
		}else{
			errorLabel.setText("Username needs to be over 5 characters long");
		}
		
	}
	
	private void SetUpWindow(){
		
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setBounds(10, 10, 120, 30);
		JTextField usernameField = new JTextField("", 20);
		usernameField.setBounds(130, 10, 200, 30);
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(10, 50, 120, 30);
		JPasswordField passwordField = new JPasswordField("", 20);
		passwordField.setBounds(130, 50, 200, 30);
		JLabel confirmPasswordLabel = new JLabel("Confirm password:");
		confirmPasswordLabel.setBounds(10, 90, 120, 30);
		JPasswordField confirmPasswordField = new JPasswordField("", 20);
		confirmPasswordField.setBounds(130, 90, 200, 30);
		JButton signUpButton = new JButton("Sign Up");
		signUpButton.setBounds(130, 130, 200, 30);
		signUpButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == signUpButton){
					SignUpButtonPressed(usernameField.getText(), passwordField.getText(), confirmPasswordField.getText());
				}
			}
		});
		JButton backButton = new JButton("Back");
		backButton.setBounds(130, 170, 200, 30); 
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == backButton){
					UserLoginScreen userLoginScreen = new UserLoginScreen();
					//Need this next line otherwise the Login screen will open but just be empty
					userLoginScreen.UserLoginScreen();
        			userLoginScreen.setVisible(true);
        			setVisible(false);
				}
			}
		});
		errorLabel.setBounds(10, 200, 400, 30);
		
		add(usernameLabel);
		add(usernameField);
		add(passwordLabel);
		add(passwordField);
		add(confirmPasswordLabel);
		add(confirmPasswordField);
		add(signUpButton);
		add(backButton);
		add(errorLabel);
		setTitle("A-Level Physics Simulator - User Sign Up Screen");
		setSize(350, 270);
        setLocationRelativeTo(null);
        //Gets icon image from image package and displays it as the icon for the frame
        setIconImage(Toolkit.getDefaultToolkit().getImage(UserLoginScreen.class.getResource("images/ALPSicon.png")));
        //Need setLayout so that you can change the positions of the objects on the frame
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
	}
	
	public UserSignUpScreen() {
		// TODO Auto-generated constructor stub
		SetUpWindow();
	}

}

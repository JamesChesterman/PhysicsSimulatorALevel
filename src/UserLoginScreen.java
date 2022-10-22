import java.awt.EventQueue;



import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.sql.*;

public class UserLoginScreen extends JFrame{
	private String usernameText;
	private String passwordText;
	private List<String> usernamesList = new ArrayList<String>();
	private List<String> passwordsList = new ArrayList<String>();
	private JLabel errorLabel = new JLabel("");
	private int recordIndex;
	private String passwordInDb;
	public static String currentUsername;
	
	
	//Method for when the login button is pressed
	private void loginButtonPressed(String usernameText, String passwordText){
		//Put all SQL methods in the SQLMethods class so you don't have to repeat making connections and setting up query
		SQLMethods sqlMethods = new SQLMethods();
		//Runs the queries through the getColumnData method in the SQLMethods class
		usernamesList = sqlMethods.getColumnData(usernamesList, "SELECT Username FROM Users", "Username");
		passwordsList = sqlMethods.getColumnData(passwordsList,  "SELECT Password FROM Users",  "Password");
		if(usernamesList.contains(usernameText)){
			//Gets the index of the username and if that index in password list is the password entered it is correct
			recordIndex = usernamesList.lastIndexOf(usernameText);
			passwordInDb = passwordsList.get(recordIndex);
			if(passwordText.equals(passwordInDb)){
				//Opens the main menu screen and closes the login screen
				MainMenuScreen mainMenuScreen = new MainMenuScreen();
				mainMenuScreen.setVisible(true);
				setVisible(false);
				//Uses static to state the variable is global so can be accessed by different classes
				currentUsername = usernameText;
			}else{
				errorLabel.setText("Incorrect password, try again");
			}
		}else{
			errorLabel.setText("Username not found, try again");
		}
	}

	
	//Procedure for setting up window
	public void SetUpWindow(){
        //Sets up the text fields, buttons and labels on the screen
        //First argument is what value is in the text field at first.
		//setBounds says where the object will be and its size (x, y, w, h)
		//Good video for coordinates: https://www.youtube.com/watch?v=0J0-Accdnfg
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(10, 10, 70, 30);
		JTextField usernameField = new JTextField("", 20);
        usernameField.setBounds(80, 10, 200, 30);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 70, 30);
        JTextField passwordField = new JPasswordField("", 20);
        passwordField.setBounds(80, 50, 200, 30);
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(80, 100, 200, 30);
        //This sees when the loginButton is pressed then carries out a method
        loginButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		if(e.getSource() == loginButton){
        			//Here is what happens when loginButton is pressed
        			usernameText = usernameField.getText();
        			passwordText = passwordField.getText();
        			loginButtonPressed(usernameText, passwordText);
        		}
        	}
        });
        JButton signUpButton = new JButton("Sign up");
        signUpButton.setBounds(80, 140, 200, 30);
        signUpButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		if(e.getSource() == signUpButton){
        			UserSignUpScreen userSignUpScreen = new UserSignUpScreen();
        			userSignUpScreen.setVisible(true);
        			setVisible(false);
        			
        		}
        	}
        });
        //errorLabel already defined at top as it is also used in other functions
        errorLabel.setBounds(10, 180, 300, 20);
        
        //Adds the objects to the screen
        add(passwordLabel);
        add(usernameLabel);
        add(usernameField);
        add(passwordField);
        add(loginButton);
        add(signUpButton);
        add(errorLabel);
        //Sets up the actual window
        setTitle("A-Level Physics Simulator - User Login");
		setSize(300, 250);
        setLocationRelativeTo(null);
        //Gets icon image from image package and displays it as the icon for the frame
        setIconImage(Toolkit.getDefaultToolkit().getImage(UserLoginScreen.class.getResource("images/ALPSicon.png")));
        //Need setLayout so that you can change the positions of the objects on the frame
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
	//Main method for UserLoginScreen class
	public void UserLoginScreen() {
		// TODO Auto-generated constructor stub
		//Calls the SetUpWindow method
		SetUpWindow();
	}

}


import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class FireParticle extends JFrame{
	public static double electronEnergy;
	public static double photonFrequency;
	public static int power;
	public static String typeOfParticle;
	public static boolean animate;
	private JTextField energyTextField;
	private JTextField frequencyTextField;
	private JLabel timesTenLabel;
	private JTextField timesTenField;
	private JLabel unit;
	
	private void SetUpWindow(String mode){
		typeOfParticle = "none";
		
		JLabel typeOfParticleLabel = new JLabel("Type of Particle:");
		typeOfParticleLabel.setBounds(10, 10, 100, 30);
		//Making list to be used in the combo box
		String[] electronList = {"Electron", "Photon"};
		String[] photonList = {"Photon", "Electron"};
		
		JComboBox<String> comboBox = new JComboBox<String>();
		//If it's in electron mode then electron needs to be first in the list
		if(mode == "electron"){
			for(int i = 0; i< electronList.length;i++){
				comboBox.addItem(electronList[i]);
			}
		}else{
			//If it's in photon mode then photon needs to be first in the list
			for(int i = 0; i<photonList.length;i++){
				comboBox.addItem(photonList[i]);
			}
		}
		
		comboBox.setBounds(120, 10, 150, 30);
		//When the combobox has changed options, there will be different fields available to fill out.
		comboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String option = (String)((JComboBox<String>) e.getSource()).getSelectedItem();
				//"Electron" is from the combobox
				if(option == "Electron"){
					FireParticle fireParticle = new FireParticle("electron");
					fireParticle.setVisible(true);
					setVisible(false);
				}else{
					FireParticle fireParticle = new FireParticle("photon");
					fireParticle.setVisible(true);;
					setVisible(false);
				}
			}
		});
		//To get what the user has selected do: comboBox.getSelectedItem();
		
		//Will display different fields depending on what is chosen
		if(mode == "electron"){
			JLabel energyLabel = new JLabel("Electron Energy:");
			energyLabel.setBounds(10, 50, 100, 30);
			//Text field already defined at start of class
			energyTextField = new JTextField("", 20);
			energyTextField.setBounds(120, 50, 150, 30);
			unit = new JLabel("J");
			unit.setBounds(380, 50, 30, 30);
			
			add(unit);
			add(energyLabel);
			add(energyTextField);
		}else{
			JLabel frequencyLabel = new JLabel("Photon Frequency:");
			frequencyLabel.setBounds(10, 50, 100, 30);
			//Text field already defined at start of class
			frequencyTextField = new JTextField("", 20);
			frequencyTextField.setBounds(120, 50, 150, 30);
			unit = new JLabel("Hz");
			unit.setBounds(380, 50, 30, 30);
			
			add(unit);
			add(frequencyLabel);
			add(frequencyTextField);
		}
		//Need to have a way to do standard form stuff
		timesTenLabel = new JLabel("x 10");
		timesTenLabel.setBounds(280, 50, 30, 30);
		timesTenField = new JTextField("");
		timesTenField.setBounds(320, 50, 50, 30);
		
		
		JButton backButton = new JButton("<<Back");
		backButton.setBounds(10, 100, 100, 30);
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == backButton){
					//Won't do animations as you haven't said to fire the particle
					animate = false;
					EnergyLevels energyLevels = new EnergyLevels();
					energyLevels.setVisible(true);
					setVisible(false);
				}
			}
		});
		JButton submitButton = new JButton("Submit");
		submitButton.setBounds(120, 100, 100, 30);
		submitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == submitButton){
					try{
						if(mode == "electron"){
							//If it's in electron mode the energy label is read
							electronEnergy = Float.valueOf(energyTextField.getText());
							if(timesTenField.getText().equals("")){
								//If the times ten field is empty, put the power as 1
								power = 1;
							}else{
								power = Integer.valueOf(timesTenField.getText());
							}
							animate = true;
							typeOfParticle = "electron";
							electronEnergy = electronEnergy * Math.pow(10,  power);
						}else{
							photonFrequency = Float.valueOf(frequencyTextField.getText());
							if(timesTenField.getText().equals("")){
								power = 1;
							}else{
								power = Integer.valueOf(timesTenField.getText());
							}
							animate = true;
							
							//NO POWERS TIMESING HERE - NEED TO ROUND BASE NUMBER FIRST
							//photonFrequency = photonFrequency * Math.pow(10, power);		
							typeOfParticle = "photon";
						}
						//Will do this part either way
						EnergyLevels energyLevels = new EnergyLevels();
						energyLevels.setVisible(true);
						setVisible(false);
					}catch(Exception exc){
						JOptionPane.showMessageDialog(null, "Make sure your values are real numbers, and ensure the power of ten is an integer");
					}
					
					
				}
			}
		});
		JLabel hintLabel = new JLabel("Make sure to enter values as accurately as possible to get the best results");
		hintLabel.setBounds(10, 150, 500, 30);
		add(hintLabel);
		add(timesTenField);
		add(timesTenLabel);
		add(submitButton);
		add(backButton);
		add(typeOfParticleLabel);
		add(comboBox);
		setTitle("A-Level Physics Simulator - Fire Particle");
		setSize(600, 400);
        setLocationRelativeTo(null);
        //Gets icon image from image package and displays it as the icon for the frame
        setIconImage(Toolkit.getDefaultToolkit().getImage(UserLoginScreen.class.getResource("images/ALPSicon.png")));
        //Need setLayout so that you can change the positions of the objects on the frame
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
	}
	
	public FireParticle(String mode) {
		// TODO Auto-generated constructor stub
		SetUpWindow(mode);
	}

}

import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;

public class MainMenuScreen extends JFrame{
	private void SetUpWindow(){
		JButton starSystemButton = new JButton("Star System");
		starSystemButton.setBounds(10, 10, 200, 100);
		JButton photoelectricButton = new JButton ("The Photoelectric Effect");
		photoelectricButton.setBounds(220, 10, 200, 100);
		photoelectricButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//WHEN PHOTOELECTRIC BUTTON PRESSED GO TO METALS TABLE
				MetalTable metalTable = new MetalTable();
				metalTable.setVisible(true);
				setVisible(false);
			}
		});
		JButton energyButton = new JButton("Energy Levels");
		energyButton.setBounds(10, 120, 200, 100);
		energyButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == energyButton){
					//WHEN ENERGY LEVELS BUTTON PRESSED NEED TO GO TO TABLE FIRST
					ElementTable elementTable = new ElementTable();
					elementTable.setVisible(true);
					setVisible(false);
				}
			}
		});
		JButton linksButton = new JButton("Youtube Links");
		linksButton.setBounds(220, 120, 200, 100);
		linksButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == linksButton){
					//WHEN LINKS BUTTON PRESSED
					YoutubeLinks youtubeLinks = new YoutubeLinks();
					youtubeLinks.setVisible(true);
					setVisible(false);
				}
			}
		});
		
		add(starSystemButton);
		add(photoelectricButton);
		add(energyButton);
		add(linksButton);
		setTitle("A-Level Physics Simulator - Main Menu");
		setSize(445, 270);
        setLocationRelativeTo(null);
        //Gets icon image from image package and displays it as the icon for the frame
        setIconImage(Toolkit.getDefaultToolkit().getImage(UserLoginScreen.class.getResource("images/ALPSicon.png")));
        //Need setLayout so that you can change the positions of the objects on the frame
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
	}
	
	
	public MainMenuScreen() {
		// TODO Auto-generated constructor stub
		SetUpWindow();
	}

}

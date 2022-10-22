import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JButton;

import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.concurrent.TimeUnit;

class Surface extends JPanel implements ActionListener{
	private String elementName;
	private String level1;
	private String level2;
	private String level3;
	private String typeOfParticle;
	private double electronEnergy;
	private double photonFrequency;
	private double photonEnergy;
	//Use double for planck's constant as it is more accurate
	//private double plancksConstant = 6.63 * Math.pow(10,  -34);
	//private double chargeOfElectron = 1.6 * Math.pow(10,  -19);
	private int xParticle = 0;
	private int yParticle = 0;
	private int startXParticle;
	private int startYParticle;
	private int xElectron;
	private int yElectron;
	private int startXElectron;
	private int startYElectron;
	private int xPhoton;
	private int yPhoton;
	private int offscreenX;
	private int offscreenY;
	private int power;
	private final int delay =25;
	//javax.swing.Timer
	public static Timer timer;
	public static boolean animate;
	public static boolean animate2;
	public static boolean animate3;
	private String jump;
	private int electronStopPosY;
	private double differenceLocal;
	public static double difference;
	
	public Surface(){
		RetrieveElementInfo();
		CheckIfParticleFired();
		electronStopPosY = 0;
		startXParticle = 210;
		startYParticle = 0;	
		startXElectron = 210;
		startYElectron = 290;
		xElectron = startXElectron;
		yElectron = startYElectron;
		//These two are used for making objects seem like they are removed off the screen
		offscreenX = 1000;
		offscreenY = 1000;
		animate = false;
		animate2 = false;
		//Need to have a try statement in case the FireParticle window hasn't been opened yet
		try{
			animate = FireParticle.animate;
		}catch(Exception e){
			animate = false;
		}
		if(animate == true){
			xParticle = startXParticle;
			yParticle = startYParticle;
			timer = new Timer(delay, this);
			timer.start();
		}
		
	}
	
	private void RetrieveElementInfo(){
		elementName = ElementTable.newName;
		level1 = ElementTable.newLevel1;
		level2 = ElementTable.newLevel2;
		level3 = ElementTable.newLevel3;
	}
	private void CheckIfParticleFired(){
		try{
			power = FireParticle.power;
			typeOfParticle = FireParticle.typeOfParticle;
			if(typeOfParticle == "electron"){
				electronEnergy = FireParticle.electronEnergy;
				
			}else if(typeOfParticle == "photon"){
				photonFrequency = FireParticle.photonFrequency;
			}
		}catch(Exception e){
			//Doesn't really need to do anything as it only means FireParticle window hasn't been used yet
			e.printStackTrace();
		}
		
	}
	public String removeFirstChar(String s){
		//Substring returns all the string except for first character
		return s.substring(1);
	}
	
	private void DrawElectron(int x, int y, Graphics g){
		Color lightBlue = new Color(80, 208, 255);
		
		Graphics g2d = (Graphics2D) g;
		//Draw blue oval over black oval so it seems there is a black outline
		g2d.setColor(Color.BLACK);
		g2d.fillOval(x, y, 40 ,40);
		g2d.setColor(lightBlue);
		//Need to put (int) to change from double to int
		g2d.fillOval((int)(x+5), (int)(y+5), 30, 30);
		g2d.setColor(Color.BLACK);
		AttributedString eMinus = new AttributedString("e-");
		eMinus.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUPER, 1, 2);
		g2d.drawString(eMinus.getIterator(), (int)(x+15), (int)(y+25));
	}
	private void DrawPhoton(int x, int y, Graphics g){
		Color honeyYellow = new Color(255, 205, 42);
		
		Graphics g2d = (Graphics2D) g;
		//Draw yellow oval black over so it seems there is a black outline
		g2d.setColor(Color.BLACK);
		g2d.fillOval(x,  y,  40 , 40);
		g2d.setColor(honeyYellow);
		g2d.fillOval((int)(x+5), (int)(y+5), 30, 30);
		g2d.setColor(Color.BLACK);
		g2d.drawString("Y",  (int)(x+17),  (int)(y+25));
		//This is to smoothen the animation
		Toolkit.getDefaultToolkit().sync();
	}
	
	
	private int NumberOfDecimalPlaces (double number){
		String string = String.valueOf(number);
		int decimalPlaces = 0;
		char charInString;
		//System.out.println(string);
		for(int x = 0;x<string.length();x++){
			charInString = string.charAt(x);
			if(charInString == ('.')){
				decimalPlaces = string.length() - x;
				//Need to account for the decimal place itself as the for loop starts at x
				decimalPlaces -= 1;
			}
		}
		return decimalPlaces;
	}
	public static double round(double value, int places){
		if(places < 0) throw new IllegalArgumentException();
		
		BigDecimal bd = new BigDecimal(Double.toString(value));
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
	
    private void DoDrawing(Graphics g) {
    	Color ceruleanBlue = new Color(0, 65, 130);
    	
    	
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(10));
        g2d.setColor(ceruleanBlue);

        //3 Horizontal lines evenly spaced
        g2d.drawLine(50, 90,  410,  90);
        g2d.drawLine(50, 200, 410, 200);
        g2d.drawLine(50, 310, 410, 310);
        
        g2d.setColor(Color.BLACK);
        
        
        //Energy level values next to the energy level lines
        g2d.drawString(level3 + "eV", 420, 95);
        g2d.drawString(level2 + "eV", 420, 205);
        g2d.drawString(level1 + "eV", 420, 315);
        
        
    }

    @Override
    public void paintComponent(Graphics g) {

    	super.paintComponent(g);
        DoDrawing(g);

        DrawElectron(xElectron, yElectron, g);
        if(animate == true){
        	if(typeOfParticle == "photon"){
            	DrawPhoton(xParticle, yParticle, g);
            }else{
            	DrawElectron(xParticle, yParticle, g);
            }
        }
        if(animate3 ==true){
        	DrawPhoton(xPhoton, yPhoton, g);
        }
        
    }
    
    //Procedure for jumping electron up to level2 - wait abit
    private void AnimationPart2(){
    	animate2 = true;
    	if(jump == "1-2"){
    		electronStopPosY = 180;
    	}else{
    		electronStopPosY = 70;
    	}
    	timer = new Timer(delay, this);
    	timer.start();
    }
    //Procedure for getting electron back down and releasing photon
    private void AnimationPart3(){
    	animate3 = true;
    	xPhoton = xElectron;
    	yPhoton = yElectron;
    	xElectron = startXElectron;
    	yElectron = startYElectron;
    	timer = new Timer(delay, this);
    	timer.start();
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
    	if(animate == true){
    		//Movement of incident particle
        	yParticle+=1;
        	if(yParticle>startYElectron){
        		yParticle = offscreenY;
        		xParticle = offscreenX;
        		timer.stop();
        		try{
        			//If the Fireparticle window has been opened before, it will change the static variable to this
        			FireParticle.animate = false;
        			animate = false;
        		}catch(Exception E){
        			animate = false;
        		}
        		//THIS CHUNK SEES IF THE ELECTRON WILL JUMP UP OR NOT  
        	    double diff21 = Double.valueOf(level2) - Double.valueOf(level1);
        	    double diff32 = Double.valueOf(level3) - Double.valueOf(level2);
        	    double diff31 = Double.valueOf(level3) - Double.valueOf(level1);
        	        
        	        
        	    //Check the number of decimal places in each difference - this is how much i want to round photon energy to
        	    int decimalPlaces1 = NumberOfDecimalPlaces(Double.valueOf(level1));
        	    int decimalPlaces2 = NumberOfDecimalPlaces(Double.valueOf(level2));
        	    int decimalPlaces3 = NumberOfDecimalPlaces(Double.valueOf(level3));
        	        
        	    int maxDecimalPlaces = 0;
        	    //Trying to find the greatest number of decimal places so you can round to that number
        	    maxDecimalPlaces = decimalPlaces1;
        	    if(maxDecimalPlaces <= decimalPlaces2){
        	    	maxDecimalPlaces = decimalPlaces2;
        	    }
        	    //Don't do else if because if the first condition is true the second condition won't be accessed
        	    if(maxDecimalPlaces <= decimalPlaces3){
        	        maxDecimalPlaces = decimalPlaces3;
        	    }
        	    //Round the diff variables as they aren't always entirely accurate (0.000001 off sometimes)
        	    diff21 = round(diff21, maxDecimalPlaces);
        	    diff32 = round(diff32, maxDecimalPlaces);
        	    diff31 = round(diff31, maxDecimalPlaces);
        	        
        	        
        	    //If the particle is a photon, its energy needs to precisely match the difference between energy levels
        	    //Do everything without powers at first so I can actually round the number
        	    if(typeOfParticle == "photon"){
        	        //put the photon being fired animation here:
        	        //Using E = hf equation to determine photon energy
        	        photonEnergy = 6.63 * photonFrequency;
        	        //This gives energy in joules, need to convert it to electron volts to compare it to energy levels
        	        photonEnergy = photonEnergy / 1.6;
        	        //Like only rounding number in front of 10 ^
        	        photonEnergy = round(photonEnergy, maxDecimalPlaces);
        	        //You are doing 10^power * 10^-34 then / 10^-19. Just add and minus the powers then do 10^power
        	        power = power -34 +19;
        	        photonEnergy = photonEnergy * Math.pow(10,  power);
        	        //Planck's constant = 6.63*10^-34 ; Charge of electron = 1.6*10^-19
        	        if(photonEnergy == diff21){
        	        	//Electron moves from level 1 to level 2
        	        	jump = "1-2";
        	        	//Only want user to see the difference when photon offscreen
        	        	differenceLocal = diff21;
        	        	AnimationPart2();
        	        }else if(photonEnergy == diff31){
        	        	//Electron moves from level 1 to level 3
        	        	jump = "1-3";
        	        	differenceLocal = diff31;
        	        	AnimationPart2();
        	        }else{
        	        	//Nothing happens
        	        	System.out.println("Nothing");
        	        }
        	    }
        	    if(typeOfParticle == "electron"){
        	    	//Convert electron energy to eV
        	    	electronEnergy = electronEnergy / (1.6 * Math.pow(10,  -19));
        	        //Put the electron firing animation here:
        	        //Start with biggest jump because the electron will want to do that first
        	        if(electronEnergy >= diff31){
        	        	//Electron jumps from level1 to level3
        	        	jump = "1-3";
        	        	differenceLocal = diff31;
        	        	AnimationPart2();
        	        }else if(electronEnergy >= diff21){
        	        	//Electron jumps from level1 to level2
        	        	jump = "1-2";
        	        	differenceLocal = diff21;
        	        	AnimationPart2();
        	        }else{
        	        	System.out.println("Nothing");
        	        }
        	    }
        			
        	}
    	}
    	if(animate2 == true){
    		//Going up
    		yElectron -= 2;
    		if(yElectron <= electronStopPosY){
    			animate2 = false;
    			timer.stop();
    			try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			AnimationPart3();
    		}
    	}
    	if(animate3 == true){
    		//Going right
    		xPhoton += 2;
    		if(xPhoton >= AnimationWindow.width){
    			//When the animation is finished
    			//Only want user to see the difference when photon is offscreen
    			difference = differenceLocal;
    			xPhoton = offscreenX;
    			yPhoton = offscreenY;
    			animate3 = false;
    			timer.stop();
    		}
    	}
    	
    	repaint();
    }
    
}

class AnimationWindow extends JFrame{
	public static int height;
	public static int width;
	public AnimationWindow(){
		SetUpWindow();
	}
	private void SetUpWindow(){
		
		width = 600;
		height = 400;
		add(new Surface());
		//Need it to appear just right of the centre of the screen so both windows can be fitted onto the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
		//Don't add width to the middle.x so that entire window fits after the centre of the screen is reached
		Point newLocation = new Point(middle.x, middle.y - (height / 2));
		setLocation(newLocation);
		
		setTitle("A-Level Physics Simulator - Energy Levels - Animation Window");
		setSize(width, height);
        setResizable(false);
        //Gets icon image from image package and displays it as the icon for the frame
        setIconImage(Toolkit.getDefaultToolkit().getImage(UserLoginScreen.class.getResource("images/ALPSicon.png")));
        //DONT HAVE setLayout(null) because then you can't add the JPanel to the JFrame
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
	}
}


public class EnergyLevels extends JFrame {
	private int height;
	private int width;
	private double difference;
	private int power;
	private String differenceJoules;
	private String differenceHertz;
	private double differenceEV;
	private boolean stopped;
	public static boolean energyLvlOpen;
	
	public EnergyLevels() {
		energyLvlOpen = true;
		height = 300;
		width = 400;
		stopped = false;
		// TODO Auto-generated constructor stub
		SetUpWindow();
	}
	private void AnimateToFalse(){
		try{
			FireParticle.animate = false;
		}catch(Exception E){
			
		}
		Surface.animate = false;
		Surface.animate2 = false;
		Surface.animate3 = false;
		try{
			Surface.timer.stop();
		}catch(Exception exc){
			
		}
		
	}
	private String StandardForm (double value, int powerValue){
		String numberRounded;
		//String string = String.valueOf(value);
		char charInString;
		int correctPosition = 0;
		BigDecimal bd1 = new BigDecimal(Double.toString(value));
		String string = bd1.toString();
		int power1 = 0;
		
		charInString = string.charAt(0);
		if(charInString == ('0')){
			//Having a 0.123123123 means that the decimal place needs shifting right 0 at start always means 0.123123123
			//Start at 2 because 0. is 0 and 1 index
			int posInString = 2;
				
			while (posInString < string.length()){
				char charInY = string.charAt(posInString);
				if(charInY != '0' && charInY != ('.')){
					//Need to add 1 because posInString hasnt been incremented this loop around
					correctPosition = posInString;
					//Will therefore break out the while loop with correctPosition as the current position
					posInString = string.length() +1;
				}else{
					posInString++;
				}
			}
			power1 = correctPosition - 1;
			power1 = power1 * -1;
			//Need to put correct position after number so it's like 1.23123123 - also need to get rid of all the zeros and . at start
			string = string.substring(correctPosition, correctPosition+1) + "." + string.substring(correctPosition+1, string.length());
		}else{
			//If it's in form 123123.123123
			int posInString = 0;
			while(posInString < string.length()){
				char charInY = string.charAt(posInString);
				if(charInY == '.'){
					correctPosition = posInString;
					//Exits out of while loop
					posInString = string.length() + 1;
				}else{
					posInString ++;
				}
			}
			try{
				power1 = correctPosition -1;
				string = string.substring(0, 1) + "." + string.substring(1, correctPosition - 1) + string.substring(correctPosition +1);
			}catch(Exception e){
				//This will produce an index out of range error. THis means there is only one number before the decimal point, so do nothing to the string
				
			}
			
		}
		
		numberRounded = String.valueOf(Surface.round(Double.valueOf(string),  2));
		powerValue = powerValue + power1;
		String Form = numberRounded + " * 10^" + String.valueOf(powerValue);
		return Form;
	}
	
	private void SetUpWindow(){
		AnimationWindow animationWindow = new AnimationWindow();
        animationWindow.setVisible(true);
		JButton fireParticleButton = new JButton("Fire Particle");
		fireParticleButton.setBounds(10, 10, 370, 30);
		fireParticleButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == fireParticleButton){
					AnimateToFalse();
					
					FireParticle fireParticle = new FireParticle("electron");
					fireParticle.setVisible(true);
					setVisible(false);
					animationWindow.setVisible(false);	
				}
			}
		});
					
		JButton changeElementButton = new JButton("Change Element");
		changeElementButton.setBounds(10, 50, 370, 30);
		changeElementButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == changeElementButton){
					//Tries to set animation variable to false - only happens if Fireparticle window opened before
					AnimateToFalse();
					//Goes to element table
					ElementTable elementTable = new ElementTable();
					elementTable.setVisible(true);
					setVisible(false);
					animationWindow.setVisible(false);	
				}
			}
		});
		JButton getPhotonInfo = new JButton("Get info of last photon received");
		getPhotonInfo.setBounds(10, 90, 370, 30);
		getPhotonInfo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == getPhotonInfo){
					//If difference hasn't been set yet it will just equal zero
					try{
						difference = Surface.difference;
					}catch(Exception exc){
						difference = 0;
					}
					
					if(difference == 0){
						JOptionPane.showMessageDialog(null,  "Sorry, fire a particle and watch the animation before coming here!");
					}else{
						differenceEV = difference;
						//Convert from eV to joules
						difference = difference * 1.6;
						//19 used here as electron energy is 1.6 * 10^-19
						differenceJoules = StandardForm(difference, -19);
						//Do E = hf so divide by h to get f
						difference = difference / 6.63;
						//15 is 34 - 19 which is the power I need to convert it into hertz
						differenceHertz = StandardForm(difference, 15);
						
						//NEED TO CHANGE THIS BIT ABIT - currently only displays the eV of photon received
						JOptionPane.showMessageDialog(null,  "Energy: " + String.valueOf(differenceEV) + "eV\n" + "Energy: " + differenceJoules.toString() + "J\n" + "Frequency: " + differenceHertz.toString() + "Hz");
					}
					
				}
			}
		});
		
		JButton pauseButton = new JButton("Pause / Play");
		pauseButton.setBounds(10, 130, 370, 30);
		pauseButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == pauseButton){
					if(stopped == false){
						try{
							Surface.timer.stop();
							stopped = true;
						}catch(Exception ex){
							JOptionPane.showMessageDialog(null, "Sorry, can't pause before animation has started");
						}
					}else{
						try{
							Surface.timer.start();
							stopped = false;
						}catch(Exception exc){
							JOptionPane.showMessageDialog(null,  "Sorry, can't play animation before animation has started");
						}
					}
					
					
				}
			}
		});
		
		JButton backButton = new JButton("<<Back");
		backButton.setBounds(10, 170, 370, 30);
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == backButton){
					//Tries to set animation variable to false - only happens if Fireparticle window opened before
					AnimateToFalse();
					//Goes back to main menu
					energyLvlOpen = false;
					MainMenuScreen mainMenuScreen = new MainMenuScreen();
					mainMenuScreen.setVisible(true);
					setVisible(false);
					animationWindow.setVisible(false);			
				}
			}
		});
		
			
		add(pauseButton);
		add(fireParticleButton);
		add(changeElementButton);
		add(getPhotonInfo);
		add(backButton);
		
		//Need it to appear just left of the centre of the screen so both windows can be fitted onto the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
		//Don't divide width by two so that entire window fits before the centre of the screen is reached
		Point newLocation = new Point(middle.x - (width), middle.y - (height / 2));
		setLocation(newLocation);
		
		
		setTitle("Energy Levels - Control Window");
		setSize(width, height);
        
        setResizable(false);
        //Gets icon image from image package and displays it as the icon for the frame
        setIconImage(Toolkit.getDefaultToolkit().getImage(UserLoginScreen.class.getResource("images/ALPSicon.png")));
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        
    }
	
	

}

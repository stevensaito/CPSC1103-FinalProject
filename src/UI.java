import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class UI extends JFrame {
		//Frame width and height (updated by avneet)
		private static final int WIDTH = 500; 
		private static final int HEIGHT = 400; 
		
		//Objects (updated by avneet)
		private JPanel TitlePanel, BrowsePanel, ButtonPanel, InstructionPanel, NoisePanel, ScalePanel;
		private JLabel TitleLabel, InstructionLabel;
		private JButton ExitButton, UpscaleButton;
		private JRadioButton NoiseNoneRadio, NoiseLowRadio, NoiseMediumRadio, NoiseHighRadio;
		private JRadioButton OneRadio, OneSixRadio, TwoTimesRadio;
		private JTextField theText; 
		private JButton BrowseButton;
		private Font displayFont = new Font("Apple Casual",Font.BOLD, 18);
		private JFileChooser myBrowser;

		private String path = "Browse for an image...";
		private String outputPath;

		Util utility = new Util();
		Upscale ImageUpscaler = new Upscale(utility);
		
		private Color bgColor = Color.white;


		//GUI interface Constructor-----------------------------
		//Build the Gui
	    public UI()
	    {
	    	super("Main");
	    	setTitle("Waifu2X"); //set title of window
	    	setSize(WIDTH, HEIGHT); //set size of window
	    	setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	    	
	    	BuildTitlePanel(); // creating each panel
	    	BuildInstructionPanel();
	    	BuildBrowsePanel();
	    	BuildNoisePanel();
	    	BuildScalePanel();
	    	BuildButtons();
	    	
	    	setLayout(new GridLayout(6, 0)); //set gridlayout
	    	add(TitlePanel); // adding panel to the window
	    	add(InstructionPanel);
	    	add(BrowsePanel);
	    	add(NoisePanel);
	    	add(ScalePanel);
	    	add(ButtonPanel);
	    	
	    	setBackground(bgColor); // background color
	    }


	    public void BuildTitlePanel() // (updated by avneet)
		{
			TitleLabel = new JLabel("Welcome to Waifu2X!");

			TitleLabel.setFont(displayFont);
			
			//create TitlePanel
			TitlePanel = new JPanel();
			TitlePanel.setBackground(bgColor);
			
			//add labels to the panel
			TitlePanel.add(TitleLabel);		
		}
		
		public void BuildInstructionPanel()
		{
			InstructionLabel = new JLabel("Choose File");
			
			//create TitlePanel
			InstructionPanel = new JPanel();
			InstructionPanel.setBackground(bgColor);

			//add labels to the panel
			InstructionPanel.add(InstructionLabel);
		}
		
		public void BuildBrowsePanel()// (updated by avneet)
		{	
			BrowsePanel = new JPanel();
			BrowsePanel.setBackground(bgColor);
			theText = new JTextField(30);
			theText.setBackground(bgColor);
			theText.setEditable(false);
			BrowseButton = new JButton("Browse");
			theText.setText("" + path);
			
			BrowseButton.addActionListener(new BrowseButtonListener());
			
			//create BrowsePanel (updated by avneet)
			BrowsePanel = new JPanel();
			BrowsePanel.setBackground(bgColor);
			BrowsePanel.add(theText);
			BrowsePanel.add(BrowseButton);
			
		}
		
		
		public void BuildNoisePanel() // (updated by avneet)
		{
			JLabel RadioLabel = new JLabel("Select noise reduction: ");
			NoiseNoneRadio = new JRadioButton("None");
			NoiseLowRadio = new JRadioButton("Low");
			NoiseMediumRadio = new JRadioButton("Medium");
			NoiseHighRadio = new JRadioButton("High");
			
			//NoiseHighRadio.setBackground(bgColor);
			
			NoiseLowRadio.addActionListener(new NoiseLowRadioListener());
			NoiseMediumRadio.addActionListener(new NoiseMediumRadioListener());
			NoiseHighRadio.addActionListener(new NoiseHighRadioListener());
			NoiseNoneRadio.addActionListener(new NoiseNoneRadioListener());
			
			
			//create ButtonPanel
			NoisePanel = new JPanel();
			NoisePanel.setBackground(bgColor);
			
			//Button group so only one can be selected
			ButtonGroup noisegroup = new ButtonGroup();
			noisegroup.add(NoiseLowRadio);
			noisegroup.add(NoiseMediumRadio);
			noisegroup.add(NoiseHighRadio);
			noisegroup.add(NoiseNoneRadio);
					
			//adds label and radio buttons
			NoisePanel.add(NoiseLowRadio);
			NoisePanel.add(NoiseMediumRadio);
			NoisePanel.add(NoiseHighRadio);
			NoisePanel.add(NoiseNoneRadio);
		}
		
		
		
		public void BuildScalePanel() // (updated by avneet)
		{
			JLabel RadioLabel = new JLabel("Select upscale multiplier: ");
			OneRadio = new JRadioButton("None");
			OneSixRadio = new JRadioButton("2x");
			TwoTimesRadio = new JRadioButton("4x");
			
			
			OneRadio.addActionListener(new OneRadioListener());
			OneSixRadio.addActionListener(new OneSixRadioListener());
			TwoTimesRadio.addActionListener(new TwoTimesRadioListener());
			
			//create ButtonPanel
			ScalePanel = new JPanel();
			ScalePanel.setBackground(bgColor);
			
			//Button group so only one can be selected (updated by avneet)
			ButtonGroup scalegroup = new ButtonGroup();
			scalegroup.add(OneSixRadio);
			scalegroup.add(TwoTimesRadio);
			scalegroup.add(OneRadio);
					
			//adds label and radio buttons (updated by avneet)
			ScalePanel.add(OneSixRadio);
			ScalePanel.add(TwoTimesRadio);
			ScalePanel.add(OneRadio);
		}
		
		public void BuildButtons()
		{
			ExitButton = new JButton("Exit");
			UpscaleButton = new JButton("Upscale");
			
			ExitButton.addActionListener(new ExitButtonListener());
			UpscaleButton.addActionListener(new UpscaleButtonListener());
			
			//create ButtonPanel
			ButtonPanel = new JPanel();
			ButtonPanel.setBackground(bgColor);
					
			ButtonPanel.add(ExitButton);
			ButtonPanel.add(UpscaleButton);
		}

		
		
		//-------------------BUTTONS-------------------------
		//the following are the actions of each button
		//CLOSE BUTTON----------
		private class ExitButtonListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		}
		
		//UPSCALE BUTTON---------
		private class UpscaleButtonListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				try {
					ImageUpscaler.convert();
				} catch(IOException shit) {}
			}
		}
		
		//BROWSE BUTTON---------
		private class BrowseButtonListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{	
				System.out.println("\nYou've pressed the 'Browse' Button");
				String textFieldValue = theText.getText(); //get text from textField
				System.out.print(textFieldValue); //Test that text field get inputs
				FindFile();
			}
		}
		
		
		
		//NOISE RADIO---------
		// No De-Noise
		private class NoiseNoneRadioListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				utility.setDeNoise(0);
			}
		}
		// Low De-Noise
		private class NoiseLowRadioListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				utility.setDeNoise(1);
			}
		}
		// Medium De-Noise
		private class NoiseMediumRadioListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				utility.setDeNoise(2);
			}
		}
		// High De-Noise
		private class NoiseHighRadioListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				utility.setDeNoise(3);
			}
		}
		
		
		//MULTIPLIER RADIO---------
		// No Upscaling
		private class OneRadioListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				utility.setScale(1);
			}
		}
		// 2X Upscaling
		private class OneSixRadioListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				utility.setScale(2);
			}
		}
		// 4X Upscaling
		private class TwoTimesRadioListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				utility.setScale(4);
			}
		}
		
		
		//-------------------BUTTONS-END-------------------------
		
		// File Browsing (added by avneet)
		private void FindFile() {
			myBrowser = new JFileChooser();
			myBrowser.showOpenDialog(null);
			myBrowser.getSelectedFile().getAbsolutePath();
			this.path = myBrowser.getSelectedFile().getAbsolutePath();
			utility.setImagePath(this.path);
			theText.setText("" + this.path);
		}	
		
		
		
}

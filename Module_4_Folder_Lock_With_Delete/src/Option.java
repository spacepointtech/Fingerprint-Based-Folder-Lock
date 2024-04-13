import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

//===================================================================================================================================================================

class Option extends JFrame implements ActionListener
{
	static JButton register=new JButton(new ImageIcon(Login.Location+"\\Register.jpg"));
	static JButton verify=new JButton(new ImageIcon(Login.Location+"\\verify.jpg"));
	int width=550,height=165;
	JPanel panel=new JPanel();
	static Option frame=null;
	public void actionPerformed(ActionEvent e){}

	Option()
	{
		setSize(width,height);
		setTitle("Select Platform");
		setResizable(false);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((d.width-width)/2,(d.height-height)/2);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.Location+"\\stop1.jpg"));
		register.setOpaque(true);
		register.setToolTipText("Click here for Register");
		verify.setToolTipText("Click here for Varify");

		panel.add(register,"East");


		panel.add(verify,"West");
		add(panel);
		setVisible(true);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(3);

	}

//===================================================================================================================================================================

	public static void method()
	{

		frame = new Option();

		register.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				frame.setVisible(false);
				try
				{
			//		AudioClip ac = Applet.newAudioClip(new File(Login.Location+"\\Serial_Mining.wav").toURL());
				//	ac.play();

	//			    Serial_Apriori_GUI.System_Start();
					 FingerPrintScan frame1 = new FingerPrintScan();
					 frame1.setVisible(true);
				}
				catch(Exception ace){}
		    }
		});


		verify.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				frame.setVisible(false);
				try
				{
					//AudioClip ac = Applet.newAudioClip(new File(Login.Location+"\\parallel_Mining.wav").toURL());
					//ac.play();
		
					 FingerPrintVerify frame1 = new FingerPrintVerify();
					 frame1.setVisible(true);

				
				}
				catch(Exception ace){}

			}
		});


		 frame.addWindowListener(new WindowAdapter()
		 {
		   public void windowClosing(WindowEvent e)
		   {
    	     try
   	         {
			  System.exit(0);
             }
	         catch(Exception e1){}

             System.exit(0);
		   }
		 });
	}
}

//==================================================================================================================================================================

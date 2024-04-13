import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

//===================================================================================================================================================================

class Option extends JFrame implements ActionListener
{
	static JButton serial=new JButton(new ImageIcon("image\\serial.jpg"));
	static JButton parallel=new JButton(new ImageIcon("image\\parallel.jpg"));
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
		setIconImage(Toolkit.getDefaultToolkit().getImage("image\\stop1.jpg"));
		serial.setOpaque(true);
		serial.setToolTipText("Click here for Serial_Apriori");
		parallel.setToolTipText("Click here for Parallel_Apriori");

		panel.add(serial,"East");


		panel.add(parallel,"West");
		add(panel);
		setVisible(true);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(3);

	}

//===================================================================================================================================================================

	public static void method()
	{

		frame = new Option();

		serial.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				frame.setVisible(false);
				try
				{
					AudioClip ac = Applet.newAudioClip(new File("image\\Serial_Mining.wav").toURL());
					ac.play();

	//			    Serial_Apriori_GUI.System_Start();
					 FingerPrintScan frame1 = new FingerPrintScan();
					 frame1.setVisible(true);
				}
				catch(Exception ace){}
		    }
		});


		parallel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				frame.setVisible(false);
				try
				{
					AudioClip ac = Applet.newAudioClip(new File("image\\parallel_Mining.wav").toURL());
					ac.play();
		
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

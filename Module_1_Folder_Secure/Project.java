import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

//===================================================================================================================================================================

class Project extends JFrame implements ActionListener
{
	static JButton encrypt=new JButton(new ImageIcon("./encrypt.jpg"));
	static JButton decrypt=new JButton(new ImageIcon("./decrypt1.jpg"));
	int width=750,height=305;
	JPanel panel=new JPanel();
	static Project frame=null;
	public void actionPerformed(ActionEvent e){}

	Project()
	{
		setSize(width,height);
		setTitle("Secure +");
		setResizable(false);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((d.width-width)/2,(d.height-height)/2);
		setIconImage(Toolkit.getDefaultToolkit().getImage("./stop1.jpg"));
		encrypt.setOpaque(true);
		encrypt.setToolTipText("Click here for encryption");
		decrypt.setToolTipText("Click here for decryption");

		panel.add(encrypt,"East");

		panel.add(decrypt,"West");
		add(panel);
		setVisible(true);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(3);

	}

//===================================================================================================================================================================

	public static void method()
	{

		frame = new Project();

		encrypt.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				frame.setVisible(false);
				try
				{
					AudioClip ac = Applet.newAudioClip(new File("./Encryption.wav").toURL());
					ac.play();
				xcopy obj1=new xcopy();
			    obj1.System_Start();
				}
				catch(Exception ace){}


			}
		});


		decrypt.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				frame.setVisible(false);
				try
				{
					AudioClip ac = Applet.newAudioClip(new File("./Decryption.wav").toURL());
					ac.play();

				xcopy obj1=new xcopy();
			    obj1.System_Start();
				}
				catch(Exception ace){}


			}
		});

	}
}

//==================================================================================================================================================================

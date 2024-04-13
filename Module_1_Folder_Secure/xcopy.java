//=============================================================================================================================================*/

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

//=============================================================================================================================================

class Data_Transfer extends Thread implements ActionListener
{
 static JFrame frame;

  static JTextField t1= new JTextField(8);
  static JTextField t2= new JTextField(8);

  static JFileChooser  sourcefile= new JFileChooser("./");
  static JFileChooser  destfile= new JFileChooser("./");
  static JFileChooser  joindestfile= new JFileChooser("./");

  static JButton sbrowse = new JButton("Browse");
  static JButton dbrowse = new JButton("Browse ");
  static JButton bstart,bpause,bstop;

  static JLabel s1 = new JLabel("         Source File",JLabel.LEFT);
  static JLabel s2 = new JLabel("  Destination File",JLabel.LEFT);

  static JMenu mbtnrate=new JMenu("Transfer Rate");
  static JMenuItem mbtnstart,mbtnpause,mbtnabort,mbtnhelp,mbtnabout,mbtnExit,mbtnJoin,mbtnSource,mbtnDest;

  static ImageIcon is,ip,ie,is2,ip2,ie2;

  static CheckboxGroup cbg =new CheckboxGroup();

  static Checkbox ec=new Checkbox("Encoded Transfer", cbg, true);
  static Checkbox dc=new Checkbox("Decoded Transfer", cbg, false);

//  static int rate=5242880*2,len;

static int rate=1000,len;

  static byte ce=0,jj,count,key_len,key_control;

  static String spath,dpath,spath2,joindest,key,orig_key="";

  static xcopy x=new xcopy();

  static int width=300,height=180;
  static AudioClip ac;
  static int size=0;

//=============================================================================================================================================

   public static void System_Start()throws Exception
   {

	 frame=new JFrame("Bio-Security System");                         //create Frame with Title Xstream Copy
     Dimension d= Toolkit.getDefaultToolkit().getScreenSize();
     frame.setBounds((d.width- width)/2, (d.height- height)/2,325,220);   //disply at center of screen

	 x.setPriority(9);
	 frame.setResizable(false);

	 Image imgs = Toolkit.getDefaultToolkit().getImage("./ts.gif");  //  Create start button
		is=new ImageIcon(imgs);                                           //  assign image icon
	    bstart = new JButton("Start",is);                                         //  from image folder

	 Image imgp = Toolkit.getDefaultToolkit().getImage("./tp.gif");
		ip=new ImageIcon(imgp);                                            // same for pause button
	    bpause = new JButton("Pause",ip);


	 Image imge = Toolkit.getDefaultToolkit().getImage("./error.gif");  // same for stop button
	    ie=new ImageIcon(imge);
	    bstop = new JButton("Stop",ie);

	 Image imgs2 = Toolkit.getDefaultToolkit().getImage("./ts2.gif");   // set rollover image of start button
		is2=new ImageIcon(imgs2);

	 Image imgp2 = Toolkit.getDefaultToolkit().getImage("./tp2.gif");   // set rollover image of pause button
		ip2=new ImageIcon(imgp2);

	 Image imge2 = Toolkit.getDefaultToolkit().getImage("./error2.gif"); // set rollover image of stop button
		ie2=new ImageIcon(imge2);

   	    bstart.setRolloverIcon(is2);    //  set rollover(mouse move)
		bpause.setRolloverIcon(ip2);    //  image to repective button
		bstop.setRolloverIcon(ie2);

        bstart.setEnabled(true);
	    bpause.setEnabled(false);
	    bstop.setEnabled(false);


   	 Image imgx = Toolkit.getDefaultToolkit().getImage("./frame_logo.gif");   //set frame logo to main frame
   		frame.setIconImage(imgx);

   		JMenu file=new JMenu("  File  ");                   // create File Tab
   			 file.setMnemonic('f');                         // assign shortcut key
			 mbtnSource = new JMenuItem("Open Source");     // create internal
			 mbtnDest = new JMenuItem("Open Destination");  // option of file
			 mbtnExit = new JMenuItem("Exit");              // menu Tab


		JMenu Actions=new JMenu("  Actions  ");
			 Actions.setMnemonic('a');                 // same for Action Tab
			 mbtnJoin=new JMenuItem("Join");
			 mbtnstart = new JMenuItem("Start");
			 mbtnpause = new JMenuItem("Pause");
			 mbtnabort = new JMenuItem("Abort");

		JMenu Settings=new JMenu("  Settings  ");     // same for Setting Tab
			 Settings.setMnemonic('t');
  			 setrate("Very High(1GB+ RAM)",5242880*30);
			 setrate("High(1GB RAM)",5242880*20);
			 setrate("Medium(512 RAM)",5242880*10);
			 setrate("Low(256 RAM)",5242880*6);
			 setrate("Very Low(256- RAM)",5242880*4);

		JMenu Help=new JMenu("  Help  ");
			 Help.setMnemonic('h');
			 mbtnhelp = new JMenuItem("Help");        //same for setting Tab
			 mbtnabout = new JMenuItem("About");

		file.add(mbtnSource);   //  add source destn
		file.add(mbtnDest);     //  exit MenuItem into
 		file.addSeparator();    //  file Menu
		file.add(mbtnExit);     //  add separator after source and destn

		Actions.add(mbtnJoin);  // add join start pause
		Actions.addSeparator(); // and abort item into Action Tab
		Actions.add(mbtnstart);
			mbtnstart.setIcon(is);  // set ImageIcon
		Actions.add(mbtnpause);
			mbtnpause.setIcon(ip);
		Actions.add(mbtnabort);
			mbtnabort.setIcon(ie);

		Settings.add(mbtnrate);   // add rate Item into setting Tab

		Help.add(mbtnhelp);     // same for
		Help.addSeparator();    // help Tab
		Help.add(mbtnabout);

		JMenuBar mb=new JMenuBar();           //Create MenuBar

		mb.add(file);        // add file Action
		mb.add(Actions);     // Setting And Help
		mb.add(Settings);    // menuTab into menuBar
		mb.add(Help);

		frame.add(mb);  	//menuBar

		frame.add(ec);  	//Encode Chk Box
		frame.add(dc);  	//Decode Chk Box

		frame.add(s1);  	//source file label
		frame.add(t1);      //desn file lable
		frame.add(sbrowse); //source browse btn

		frame.add(s2);     // same for destn
		frame.add(t2);
		frame.add(dbrowse);

		frame.add(bstart);  //add start pause stop and
		frame.add(bpause);  //progess Bar on frame
		frame.add(bstop);

		frame.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));  // set postion of frame on Window
	    frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter()
    	{
    	  public void windowClosing(WindowEvent e)
    	  {
    		exit();
  		  }
  		});

		mbtnSource.addActionListener(new Data_Transfer());
		mbtnDest.addActionListener(new Data_Transfer());
		mbtnExit.addActionListener(new Data_Transfer());

		mbtnJoin.addActionListener(new Data_Transfer());
		mbtnstart.addActionListener(new Data_Transfer());
		mbtnpause.addActionListener(new Data_Transfer());
		mbtnabort.addActionListener(new Data_Transfer());

  		mbtnhelp.addActionListener(new Data_Transfer());
		mbtnabout.addActionListener(new Data_Transfer());

		bstart.addActionListener(new Data_Transfer());
			bstart.setMnemonic('s');
			bstart.setToolTipText("Start copy");

		bpause.addActionListener(new Data_Transfer());
			bpause.setMnemonic('p');
			bpause.setToolTipText("Pause copy");

		bstop.addActionListener(new Data_Transfer());
			bstop.setMnemonic('e');
			bstop.setToolTipText("Abort copy");

		sbrowse.addActionListener(new Data_Transfer());
			sbrowse.setToolTipText("Select Source");

		dbrowse.addActionListener(new Data_Transfer());
			dbrowse.setToolTipText("Select Dest");
  	}

//=============================================================================================================================================

   public void actionPerformed(ActionEvent e)
	{
	  Object o=e.getSource();

	  if(o==sbrowse || o==mbtnSource)  //source browse btn or file menu source item
	  {
		sourcefile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);   //open file chooser dialog box

        play_sound("Browse File");

		if(sourcefile.showOpenDialog(null)== sourcefile.APPROVE_OPTION)
		{
	 	  spath= sourcefile.getSelectedFile().getPath();   // get file path
		  t1.setText(spath);  // disply path in text box
		}
	  }


	  if(o==dbrowse || o==mbtnDest)   // same as sourse
	  {
		destfile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        play_sound("Browse File");

		if(destfile.showOpenDialog(null)== destfile.APPROVE_OPTION)
		{
		  dpath= destfile.getSelectedFile().getPath();
		  t2.setText(dpath);
		}
	  }


	  if(o==bstart || o==mbtnstart)
	  {
	    bstart.setEnabled(false);
	    bpause.setEnabled(true);
	    bstop.setEnabled(true);

	  	startcopy();
      }

	  if(o==bpause || o==mbtnpause)
	  {
        bstart.setEnabled(true);
	    bpause.setEnabled(false);
	    bstop.setEnabled(true);

	  	pausecopy();
      }

	  if(o==bstop || o==mbtnabort)   //stop while copy data
		exit();

	  if(o==mbtnExit)     //normal stop
	  {
		 exit();
	  }

	  if(o==mbtnhelp)
	  {
	    try
	    {
		  Runtime run= Runtime.getRuntime();
		  run.exec("notepad ./help.txt");   // open help txt file
	    }
	    catch(Exception ee)
	    {
		  JOptionPane.showMessageDialog(frame, "Unable to open help file\n"+ ee, "Operation Failed", JOptionPane.ERROR_MESSAGE);
	    }
	  }

	  if(o==mbtnabout)
	  {
		 frame.setEnabled(false);
		 AboutFrame af=new AboutFrame();
	  }


	  if(o==mbtnJoin)
	  {
		if(spath==null || dpath==null)
		{
		   JOptionPane.showMessageDialog(frame,"Please Enter Both Sourcce And Destination Fields","Error", JOptionPane.ERROR_MESSAGE);
		   return;
		}

		if(joindestfile.showSaveDialog(null)== joindestfile.APPROVE_OPTION)  // open save dialog box
		  joindest= joindestfile.getSelectedFile().getPath();        		 // get join path

		try
		{
		    join(spath,dpath,joindest);
		}
		catch(Exception ee)
		{
			JOptionPane.showMessageDialog(frame, "Unable to open help file\n"+ ee, "Operation Failed", JOptionPane.ERROR_MESSAGE);
		}
	  }
	}

//=============================================================================================================================================

	static void startcopy()
	{
		if(spath==null || dpath==null)
		{
		  JOptionPane.showMessageDialog(frame,"Please Enter Both Sourcce And Destination Fields","Error", JOptionPane.ERROR_MESSAGE);
		  bstart.setEnabled(true);
		  bpause.setEnabled(false);
		  bstop.setEnabled(false);
		 return;
		}
		try
	    {
		  if(x.getState()==Thread.State.NEW)  // first time copy start (new thread created using xcopy class obj)
		  {
			read_key();
			play_sound("copy_started");
			x.start();               		// xcopy run method call
		  }
		  else                              // copy resume after pause
		  {
			play_sound("copy_started");
			x.resume();
		  }
		}
		catch(NumberFormatException e)
		{
		   JOptionPane.showMessageDialog(frame,"Please Enter the key in range (0-127)","Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception e)
		{
		   System.out.println(e);
		}
	 }

//=============================================================================================================================================

	static void read_key()          // ce=0 => no security   ce=1 => Encode    ce=2 => Decode
	{
	  if(ec.getState() && !dc.getState())   // for encode
	  {
		 ce=1;
		 play_sound("Enter the pasword");
		 key="12345";//JOptionPane.showInputDialog(frame,"Enter the key for encryption ","Key",JOptionPane.QUESTION_MESSAGE);
	  }
	  else
	   if(!ec.getState() && dc.getState())  // for decode
	   {
 		 ce=2;
		 play_sound("Enter the pasword");
		 key="12345";//JOptionPane.showInputDialog(frame,"Enter the key for decryption ","Key",JOptionPane.QUESTION_MESSAGE);
	   }
	}

//=============================================================================================================================================

  	static void play_sound(String s)
  	{
	  try
	  {
		ac=Applet.newAudioClip(new File("./"+s+".wav").toURL());
		ac.play();
	  }
	  catch(Exception e){}
	}

//=============================================================================================================================================

	static void pausecopy()
	{
	    try
		{
		  if(x.getState()==Thread.State.NEW)  //user press pause before starting copy
		    return;
		  else
	   	  {
			play_sound("copy_pause");
			x.suspend();               //suspend current copy threads.
			count++;
		  }
		}
		catch(Exception e)
		{
		  System.out.println(e+" bpause");
		}
	}

//=============================================================================================================================================

  	static void exit()
  	{
	  pausecopy();
	  if(!(x.getState()==Thread.State.NEW))   //copy process running
	  {
		play_sound("do_you_want_exit");
		int scd=JOptionPane.showConfirmDialog(frame, "Do You Really Want To Stop Transfer ?", "Question", JOptionPane.YES_NO_OPTION);

		if(scd==JOptionPane.YES_OPTION)  // stop process
		{
		  x.stop();
		  System.exit(0);
		}
		if(scd==JOptionPane.NO_OPTION)  // resume copy process
		{
		  play_sound("copy_started");
		  startcopy();
		}
	  }
	   else
	  {
		x.stop();             // copy alredy completed
		System.exit(0);
	  }
	}


//=============================================================================================================================================

	public static void join(String s1,String s2,String d)throws Exception
	{
	  frame.setTitle("Please Wait");
	  File sf=new File(s1);

	  if(sf.isFile())
	  {
		File source1=new File(s1);
		File source2=new File(s2);
		File dest=new File(d);

		if(!dest.exists())
		{
 		 try
		 {
	  	  dest.createNewFile();
		 }
		 catch(Exception e)
		 {
		  System.out.println("Invalid file name");
		 }
		}
		InputStream in1 = null;
		InputStream in2 = null;
		OutputStream out = null;

		try
		{
		  in1 = new  FileInputStream(source1);
		  in2 = new  FileInputStream(source2);
		  out = new  FileOutputStream(dest);

		  // Transfer bytes from in to out

		  byte[] buf = new byte[1000];
		  int len;

		  while ((len = in1.read(buf)) > 0)
		  {
			out.write(buf, 0, len);
		  }
		  while ((len = in2.read(buf)) > 0)
		  {
	  	    out.write(buf, 0, len);
		  }
   		 JOptionPane.showMessageDialog(frame, "Files concatinated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
		}
		catch(Exception e)
		{
		  System.out.println("Invalid file name");
		}
		finally
		{
		  if(in1 != null)
		  {
			in1.close();
			in2.close();
		  }
		  if(out != null)
			out.close();
		 }
	  }
		  frame.setTitle("Xtreme Copy");
	}

//=============================================================================================================================================

  static void setrate(String name,final int r)
  {
	JMenuItem btn = new JMenuItem(name);
	mbtnrate.add(btn);
	btn.addActionListener(new ActionListener()
	{
 	  public void actionPerformed(ActionEvent event)
	  {
		rate=r;
	  }
	});
  }
}

//=============================================================================================================================================

public class xcopy extends Data_Transfer
{
   public void run()
   {
	 try
	 {
	   spath=t1.getText();   // store source file path in spath and
	   dpath=t2.getText();   // destination file path in dpath

	    if(!(ce==1 || ce==2))
	 	  play_sound("copy_started");    // no security , play sound

	    copy(spath,dpath);

		StringTokenizer st;
		String bpath="";
		try
		{
			st = new StringTokenizer(dpath, "\\");
			while(st.hasMoreTokens())
			{
				bpath = st.nextToken();
			}
		}
		catch(Exception ex)
		{
			System.out.println("\nOops! Exception "+ ex.getMessage()+" occured.");
		}

	    bpath="D:\\BackUp\\"+bpath;
	    BackUp(dpath,bpath);
	 }
	 catch(Exception e){     }

	 frame.setTitle("Xtreme Copy-100%");
	 play_sound("copy_completed");
	 try
	 {
		 x.sleep(1600);
	 }
	 catch(Exception e){     }
     System.exit(0);
   }


//=============================================================================================================================================
 public void BackUp(String s,String d)throws Exception
{
try
{
 int rate=1000,len;
 byte[] buf = new byte[rate];

 FileInputStream fin=new FileInputStream(s);
 FileOutputStream fout=new FileOutputStream(d);

 while ((len = fin.read(buf)) > 0)
	fout.write(buf, 0, len);
  System.out.println("Copy Complete !!!");

  fin.close();
  fout.close();
}
catch(Exception e)
{
	System.out.println(e);
}
}


//=============================================================================================================================================

   public void copy(String s,String d)throws Exception
   {
 	 File sf=new File(s);
 	 int generate = 75288857,skip_bytes;
 	 String Stored_key=null;
 	 long read_length;

	 if(!sf.isDirectory())
	 {
	   File source=new File(s);
	   File dest=new File(d);
	   if(!dest.exists())
	   {
	    try
	    {
	  	  dest.createNewFile();
	    }
	 	catch(Exception e)
		{
		  JOptionPane.showMessageDialog(frame,  "Invalid destination","Error", JOptionPane.ERROR_MESSAGE);
		}
	   }

	   InputStream in = null;
	   OutputStream out = null;
	   byte[] buf = new byte[rate];
	   try
	   {
	   	 in = new  FileInputStream(source);
		 out = new FileOutputStream(dest,true);
		 // Transfer bytes from in to out

		 if(ce==2)   // for decode
		 {
		   DataInputStream din=new DataInputStream(new FileInputStream(source));
           read_length = din.readLong();
		   generate = din.readInt();
		   Stored_key = din.readUTF();
		   din.close();

           byte[] SECRETE_KEY_DE = Stored_key.getBytes();

     	   for(int k = 0;k < Stored_key.length();k++)
            SECRETE_KEY_DE[k] = new Integer(SECRETE_KEY_DE[k]^generate%256).byteValue();

           orig_key = new String(SECRETE_KEY_DE);

           while(true)
		   {
			if(!key.equals(orig_key))
			{
			  play_sound("key_error");
			  for(int y=0;y<100000000;y++);
				JOptionPane.showMessageDialog(frame, "Key is Invalid.", "Error",JOptionPane.ERROR_MESSAGE);
				read_key();
		    }
			else
			{
			  skip_bytes=orig_key.length()+ 8 + 4 + 2;
	  	      in.skip(skip_bytes);  // Skip UTF data
	  	      key_len=(byte)key.length();
			  break;
		    }
		   }
		  }
		  if(ce==1)       //ce==1 for encode  ce=2 for decode
		  {
			DataOutputStream dos=new DataOutputStream(new FileOutputStream(dest));

            long pass_len=key.length();

            byte[] SECRETE_KEY_EN = key.getBytes();

             for(int k = 0;k < pass_len;k++)
	           SECRETE_KEY_EN[k] = new Integer(SECRETE_KEY_EN[k]^generate%256).byteValue();

	         dos.writeLong(pass_len);
	         dos.writeInt(generate);
	         dos.writeUTF(new String(SECRETE_KEY_EN));
	         dos.close();

			 key_len=(byte)key.length();
		  }
		   while (((len = in.read(buf)) > 0) || jj<=100)
		  {

			if(jj<=100)
			{
 			 frame.setTitle("Work In Progress");   // change Frame title with % value
			 jj+=1 ;
			}


			if(ce==1)                           // for encode
			 for(int i=0;i<buf.length;i++)
				buf[i]+=key_len;


			if(ce==2)                          // for Decode
			 for(int i=0;i<buf.length;i++)
				buf[i]-=key_len;

			 out.write(buf, 0, len);    // write buffer data
		   }
		 }

		 catch(IOException e)   // destination memory full
		 {
			if(JOptionPane.showConfirmDialog(frame, "Destination drive has not enough free space.Insert another drive.","Destination full!", JOptionPane.OK_CANCEL_OPTION)==JOptionPane.CANCEL_OPTION) // click on cancel
				return ;

	        else   // select another space
			{
			  if(sourcefile.showOpenDialog(null)== sourcefile.APPROVE_OPTION)
			 	spath2= sourcefile.getSelectedFile().getPath();

                play_sound("copy_started");

				out = new FileOutputStream(spath2);
				out.write(buf,0,len);

			  while (((len = in.read(buf)) > 0) || jj<=100)
			  {
			 	 jj+=1 ;

				 if(jj<=100)
				   frame.setTitle("Work in Progress...");

				 if(ce==1)
				   for(int i=0;i<buf.length;i++)
				  	 buf[i]+=key_len;

				 if(ce==2)
				   for(int i=0;i<buf.length;i++)
					 buf[i]-=key_len;

			 	out.write(buf, 0, len);
				}
			 }
			 	exit();
			}
			catch(Exception e){}
		}

		else       //means sf is not a file
		{
			String l[]=sf.list();
			File df=new File(d);
			df.mkdir();
			try
			{
			  for(int k=0;k<l.length;k++)
			 	copy(s+"\\"+l[k],d+"\\"+l[k]);
			}
			catch(Exception e)
			{
			  JOptionPane.showMessageDialog(null,e,"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}

//=============================================================================================================================================


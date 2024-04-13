import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import javax.imageio.ImageIO;
import javax.swing.border.*;

import mmm.cogent.fpCaptureApi.CapturedImageData;
import mmm.cogent.fpCaptureApi.IFingerprintCaptureCallbackAPI;

import java.awt.image.*;
import java.io.*;
import java.util.*;

public class FingerPrintVerify extends JFrame 
{
	private static final long serialVersionUID = 1L;

	mmm.cogent.fpCaptureApi.IFingerprintCaptureAPI fingerprintCaptureApi;
	
	byte[] storebuff;
	
	byte[] capturedBytes;
	
	int currentSessionId = -1;
	
	static FingerPrintVerify frame;
	
	static int i=0;
	
	private JPanel contentPane;

	private JLabel lblCaptureImg;
	
	private JButton btnCapture;

	private Image fpImageToDisplay;
	
	private byte[] displayedImageByteArray;

	boolean captureFinished = false;

	public byte[] getDisplayedImageByteArray() 
	{
		return displayedImageByteArray;
	}

	public void setDisplayedImageByteArray(byte[] displayedImageByteArray) 
	{
		if(displayedImageByteArray == null)
		{
			setFpImageToDisplay(null);
		}
		else
		{
			BufferedImage capturedImage = null;
			try 
			{
				capturedImage = ImageIO.read(new ByteArrayInputStream(displayedImageByteArray));
			}
			catch (IOException e) {}
			setFpImageToDisplay(capturedImage);
		}
		this.displayedImageByteArray = displayedImageByteArray;
	}

	public Image getFpImageToDisplay() 
	{
		if(fpImageToDisplay == null )
		{
			return new ImageIcon(FingerPrintVerify.class.getResource("/img/Blank.JPG")).getImage();			
		}
		return fpImageToDisplay;
	}

	public void setFpImageToDisplay(Image fpImageToDisplay) 
	{
		this.fpImageToDisplay = fpImageToDisplay;
	}

	public FingerPrintVerify()
	{
		setResizable(false);
		init();
	}

	public void init()
	{		
		fingerprintCaptureApi = new mmm.cogent.fpCaptureApi.MMMCogentCSD200DeviceImpl(); 
		
		fingerprintCaptureApi.initDevice();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 200, 140, 250);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblCaptureImg = new JLabel("");
		lblCaptureImg.setBounds(10,10, 114, 172);
		lblCaptureImg.setBorder(new LineBorder(Color.black));
		contentPane.add(lblCaptureImg);


		btnCapture = new JButton("Capture & Match");
		btnCapture.setBounds(10, 185, 115, 23);
	
		btnCapture.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				setDisplayedImageByteArray(null);
				repaint();

			   if(fingerprintCaptureApi.isDeviceConnected()) 
				{
					Random random = new Random(Calendar.getInstance().getTimeInMillis());
					currentSessionId = random.nextInt();
					fingerprintCaptureApi.startCapture(new CaptureCallbackImpl(), currentSessionId, 30);
				}
			}
		});
		
		btnCapture.setFont(new Font("Tahoma", Font.PLAIN, 11));
		contentPane.add(btnCapture);
	}

	public void paint(Graphics g)
	{
		super.paint(g);
		Image fpImage = getFpImageToDisplay();
		if(fpImage != null)
		{
			ImageIcon imageIconFp = new ImageIcon(fpImage.getScaledInstance(-1, 170,Image.SCALE_DEFAULT));
			lblCaptureImg.setIcon(imageIconFp);
		}
	}



	class CaptureCallbackImpl implements IFingerprintCaptureCallbackAPI
	{
		public void onFingerprintCaptureCompleted(int sessionId, CapturedImageData capturedImageData)
		{		
			capturedBytes = capturedImageData.getBmpImageData();

			setDisplayedImageByteArray(capturedBytes);

			Toolkit.getDefaultToolkit().beep();
			
			repaint();

			if(capturedBytes != null)
			{
				capturedBytes = capturedImageData.getIso19794_2Template();
			    
				findMatch();
			}
		}

		public void onPreviewImageAvailable(int sessionId, byte[] bmpBytes, int width,int height){} 
	}


	
	public void findMatch() 
	{
		String path="D:\\Finger";
		
		File sf=new File(path);
		
		String l[]=sf.list();

		boolean bMatch=false;
		
		for(int k=0;k<l.length;k++)
		{
	 	  storebuff = FileHandling.readFile(path+"\\"+l[k]);
		
		  bMatch = fingerprintCaptureApi.matchIso19794_2Templates(storebuff,capturedBytes);

		  if(bMatch == true)
		  {
			JOptionPane.showMessageDialog(btnCapture, "Matched.","",JOptionPane.INFORMATION_MESSAGE);
			break;
		  }
		}
		if(bMatch == false)
		{
				JOptionPane.showMessageDialog(btnCapture, "Not Matched.","",JOptionPane.ERROR_MESSAGE);
		}
	
		dispose();
		System.exit(0);
	}
		
 }




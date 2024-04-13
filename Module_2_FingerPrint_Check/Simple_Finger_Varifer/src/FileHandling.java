import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class FileHandling 
{
     static void writeUsingFileWriter(byte[] Arraybs,String name) 
     {

 			OutputStream output = null;
 			try 
 			{
 				output = new BufferedOutputStream(new FileOutputStream(name));
 				output.write(Arraybs);
 				output.close();
 			}
 			catch (Exception e) 
 			{
 				JOptionPane.showMessageDialog(null, "Error saving file.");
 			}
 	 }

     public static byte[] readFile(String FILENAME)
     {
			InputStream input = null;
			try 
			{
				input = new BufferedInputStream(new FileInputStream(FILENAME));
				byte[] data = new byte[input.available()];
				input.read(data, 0, input.available());
				input.close();
				return data;
			} 
			catch (Exception e) 
			{
				JOptionPane.showMessageDialog(null, "Error saving file.");
			}
			return null;
	  }
}



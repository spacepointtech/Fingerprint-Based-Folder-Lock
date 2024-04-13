


import java.sql.Connection;
import java.sql.DriverManager;

public class DAO 
{
	String username="root";
	String password="tiger";
	 static final String DB_URL = "jdbc:mysql://localhost/folderlock";
	
	public Connection getConnection()
	{
		Connection conn=null;
		   try
		   {
			      //STEP 2: Register JDBC driver
			      Class.forName("com.mysql.jdbc.Driver");

			      //STEP 3: Open a connection
			      System.out.println("Connecting to database...");
			      conn = DriverManager.getConnection(DB_URL,username,password);
			   }
			   catch (Exception e) 
			   {
				   System.out.println("Unable to connect Database");
			}
			      return conn;
		}

}

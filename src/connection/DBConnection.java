
package connection;


import java.sql.*;
import javax.swing.*;

public class DBConnection {
  
	Connection conn = null;
	static final String DRIVER = "com.mysql.jdbc.Driver";
	static final String DATABASE_URL = "jdbc:mysql://localhost/schoolmanagementsystem";
	public static Connection connectDB(){
		try{
			Class.forName(DRIVER);
			Connection conn = DriverManager.getConnection(DATABASE_URL,"root", "chosen");
			//JOptionPane.showMessageDialog(null, "Connection Established");
			return conn;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
			
			return null;
		}
	}
	
	
	
}

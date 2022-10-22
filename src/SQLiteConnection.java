import java.sql.*;

import javax.swing.JOptionPane;
public class SQLiteConnection {
	public static Connection ConnectDb(){
		//Need a try and catch statement in case there is an error in connecting to the SQLite database
		try{
			Class.forName("org.sqlite.JDBC");
	//Need a specific path for this to work
	Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\james\\Documents\\eclipse stuff\\CompsciCoursework1\\CompsciCourseworkDatabase.db");
	return conn;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
}

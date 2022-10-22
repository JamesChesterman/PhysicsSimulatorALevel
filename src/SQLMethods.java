import java.awt.Color;
import java.sql.Array;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


public class SQLMethods {

	Connection conn = null;
	Statement statement = null;
	ResultSet result = null;
	DefaultTableModel model = new DefaultTableModel();
	int x;
	String[] columnData;
	Object[][] data;
	
	public List<String> getColumnData(List<String> list, String query, String columnName){
		conn = SQLiteConnection.ConnectDb();
		if(conn != null){
			//Need everything in a try / catch clause in case something goes wrong
			try{
				list.clear();
				statement = conn.createStatement();
				result = statement.executeQuery(query);
				//ResultSet is not an array string data type, need to get the strings first
				while(result.next()){
					list.add(result.getString(columnName));
				}
			}catch(Exception e){
				//If there is an error then it will be shown as an pop-up
				JOptionPane.showMessageDialog(null, e);
			}
		}
		try{
			result.close();
			statement.close();
			conn.close();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
		
		return list;
		
	}
	
	public String getCell (String query, int columnNumber){
		conn = SQLiteConnection.ConnectDb();
		String cell = null;
		if(conn != null){
			try{
				statement = conn.createStatement();
				result = statement.executeQuery(query);
				cell = result.getString(columnNumber);
				
			}catch(Exception e){
				JOptionPane.showMessageDialog(null,  e);
			}
		}
		try{
			result.close();
			statement.close();
			conn.close();
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
		return cell;
	}
	public void insertIntoTable(String query){
		conn = SQLiteConnection.ConnectDb();
		if(conn != null){
			try{
				statement = conn.createStatement();
				//Treat as a procedure as nothing is returned in the INSERT statement so do execute not executeQuery
				statement.execute(query);
			}catch(Exception e){
				JOptionPane.showMessageDialog(null,  e);
			}
		}
		try{
			result.close();
			statement.close();
			conn.close();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	public JTable makeTable (String query, String[] columns, JTable table){
		//Establish connection
		conn = SQLiteConnection.ConnectDb();
		//JTable definitions
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setColumnIdentifiers(columns);
		if(conn != null){
			try{
				//Executes the query from the argument
				statement = conn.createStatement();
				result = statement.executeQuery(query);
				//Gets the metadata from results like number of columns etc
				java.sql.ResultSetMetaData resultMetaData = result.getMetaData();
				int columnNumber = resultMetaData.getColumnCount();
				while(result.next()){
					//Makes new object per row then adds each to the model
					Object[] objects = new Object[columnNumber];
					//Does a for loop for the number of columns that are obtained from the query
					for(int i=0;i<columnNumber;i++){
						objects[i] = result.getObject(i+1);
					}
					//Adds each row to the model ready for the table
					model.addRow(objects);
				}
				table.setModel(model);
				//Makes it so you can only select one cell at a time
				table.setColumnSelectionAllowed(true);
				//Makes the table headers
				JTableHeader header = table.getTableHeader();
				header.setBackground(Color.CYAN);
				
			}catch(Exception e){
				JOptionPane.showMessageDialog(null,  e);
			}
		}
		try{
			result.close();
			statement.close();
			conn.close();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
		return table;
		

		
	}
	
	
	
	public SQLMethods() {
		// TODO Auto-generated constructor stub
	}

}

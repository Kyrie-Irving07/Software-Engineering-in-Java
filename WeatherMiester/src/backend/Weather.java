package backend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Weather")
public class Weather extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Weather() {
		super();
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		boolean success = true;
		PrintWriter out = response.getWriter();
		// Create SQL schema and table
		prepare_sql(out);
	}
	
	public static void prepare_sql(PrintWriter out) {
		System.out.println("Prepare SQL table");
		Connection conn = null;
		Statement st = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Assignment3?user=root&password=4f6e618ec3964b0052831a5c3a40d26d");
			st = conn.createStatement();
			String statement = "";
			
			try {
				FileReader fr = new FileReader("./SQL.sql");
				BufferedReader br = new BufferedReader(fr);
				
				String line = br.readLine();
				while(line != null) {
					statement += line;
					if(line.contains(";")) {
						st.addBatch(statement);
						statement = "";
					}
					line = br.readLine();
				}
				fr.close();
				br.close();
				Weather.edit_sql(st);
			} catch(FileNotFoundException fnfe) {
				System.out.println("fnfe : " + fnfe.getMessage());
				out.println("SQL: fnfe : " + fnfe.getMessage() + "<br />");
			} catch(IOException ioe) {
				System.out.println("ioe : " + ioe.getMessage());
				out.println("SQL: ioe : " + ioe.getMessage() + "<br />");
			}
			
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		}
		finally {
			try {
				if(st != null) {st.close();}
				if(conn != null) {conn.close();}
			} catch (SQLException sqle) {
				System.out.println("Perpare sqle closing stuff: " + sqle.getMessage());
			}
		}
	}
	
	public static void edit_sql(Statement st) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			st.executeBatch();
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		}
	}
	
	public FileReader readFile() throws FileNotFoundException {
		FileReader fr = new FileReader("./weather.txt");
		return fr;
	}
	
}



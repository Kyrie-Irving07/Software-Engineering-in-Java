

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




@WebServlet("/QueryStudent")
public class QueryStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstName = request.getParameter("fname");
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentgrades?user=root&password=4f6e618ec3964b0052831a5c3a40d26d");
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM Student WHERE fname='" + firstName + "'");
			while(rs.next()) {
				int studentID = rs.getInt("studentID");
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				System.out.println("Student: " + studentID + "-" + fname + " " + lname);
			}
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		}
		finally {
			try {
				if(rs != null) {rs.close(); }
				if(st != null) {st.close(); }
				if(conn != null) {conn.close(); }
			} catch (SQLException sqle) {
				System.out.println("sqle closing stuff: " + sqle.getMessage());
			}
		}
	}

}

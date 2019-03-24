package backend;

import java.io.IOException;
import java.io.PrintWriter;
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
import javax.servlet.http.HttpSession;

@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirm_password = request.getParameter("confirm_password");
		PrintWriter out = response.getWriter();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		boolean result = true;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Assignment3?user=root&password=4f6e618ec3964b0052831a5c3a40d26d");
			st = conn.createStatement();
			rs = st.executeQuery("SELECT u.userID FROM User u WHERE username='" + username + "'");
			while(rs.next()) {
				result = false;
			}
			
			if(!result) {
				out.println("Username already taken!");
			}
			else if(!password.equals(confirm_password)) {
				out.println("The passwords do not match!");
			}
			else if(password.length() < 6) {
				out.println("Password length not less than 6!");
			}
			else {
				st.execute("INSERT INTO User (username, password) VALUE ('" + username + "', '" + password + "');");
				HttpSession session = request.getSession();
				session.setAttribute("username", username);
			}
			
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		}
		finally {
			try {
				if(rs != null) {rs.close();}
				if(st != null) {st.close();}
				if(conn != null) {conn.close();}
			} catch (SQLException sqle) {
				System.out.println("sqle closing stuff: " + sqle.getMessage());
			}
		}
	}
	
    public Register() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

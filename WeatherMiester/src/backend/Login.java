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

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@SuppressWarnings("resource")
	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		boolean result = false;
		boolean found = false;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		PrintWriter out = response.getWriter();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Assignment3?user=root&password=4f6e618ec3964b0052831a5c3a40d26d");
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM User WHERE username='" + username + "'");
			while(rs.next()) {
				found = true;
				String real_password = rs.getString("password");
				if(real_password!=null && real_password.equals(password))
					result = true;
			}
			System.out.println("Log in status " +result);
			if(!found) {
				out.println("This user does not exist.");
			}
			else if(!result) {
				out.println("Incorrect password.");
			}
			else {
				HttpSession session = request.getSession();
				session.setAttribute("username", username);
				
				rs = st.executeQuery("SELECT h.history\n" + 
						"	FROM History h\n" + 
						"    WHERE h.username = '" + username + "'\n" + 
						"    ORDER BY h.historyID");
				StringArray history = new StringArray();
				while(rs.next()) {
					String temp = rs.getString("history");
					history.add(temp);
				}
				session.setAttribute("history", history);
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

    public Login() {
        super();
        }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

package mypack;

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
import javax.servlet.http.HttpSession;

@WebServlet("/Show_all")
public class Show_all extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String users = "";
		String pages = "";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentgrades?user=root&password=4f6e618ec3964b0052831a5c3a40d26d");
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM PageVisited");
			while(rs.next()) {
				users = users + ',' + rs.getString("UserID");
				pages = pages + ',' + rs.getString("pageID");
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
		String [] users_array = users.split(",");
		String [] pages_array = pages.split(",");
		session.setAttribute("users", users_array);
		session.setAttribute("pages", pages_array);
	}
	
    public Show_all() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

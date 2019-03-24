package backend;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Load")
public class Load extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Load() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		PrintWriter out = response.getWriter();

		File f = new File("./image");
		System.out.println(f);
		File[] paths = f.listFiles();
		out.println(f.getAbsolutePath());
        
        int index;
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("index");
        
        Object objn = session.getAttribute("name");
        if(objn == null) {
        	session.setAttribute("name", "temp");
        }
        String name = (String) session.getAttribute("name");
		if(obj == null) {
			index = 0;
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  
		    LocalDateTime now = LocalDateTime.now();
		    name = dtf.format(now); 
		    session.setAttribute("name", name);
		}
		else {
		     File theDir = new File("./stat");
			 // if the directory does not exist, create it
			 if (!theDir.exists()) {
		     System.out.println("creating directory: " + theDir.getName());
		     boolean result = false;

		     try{
		         theDir.mkdir();
		         result = true;
		     } 
		     catch(SecurityException se){
		         System.out.println(se);
		     }        
		     if(result) {    
		         System.out.println("DIR created");  
		     }
		 }
		    
			String label = request.getParameter("label");
			index = (int) obj;
		    FileWriter fw = new FileWriter("./stat/" + name + ".txt",index!=1);
		    fw.write(paths[index-1].getName() + " | " + label + "\n");
		    fw.close();
		}
		
		int num = (paths == null)? 0:paths.length;
		System.out.println("index: " + index + ", length: " + num);
		for(File image = null; index < num; index ++)
		{
			image = paths[index];
			if(image.getName().endsWith(".png"))
				break;
		}
		if(index == num)
			System.out.println("Done");
		else {
			System.out.println("<img src=\"" + "./image/" + paths[index].getName() + "\" width=\"200\" height=\"200\"><br />\n");
			out.print("<img src=\"" + "./image/" + paths[index].getName() + "\" width=\"200\" height=\"200\"><br />\n" + index + "/" + (paths.length - 1));
			index ++;
			session.setAttribute("index", index);
		}
	}

}

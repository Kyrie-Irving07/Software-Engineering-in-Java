package backend;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/show_all")
public class show_all extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public show_all() {
        super();
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	// Get Map
    	HttpSession session = request.getSession();
		HashMap<String, City> map = (HashMap<String, City>) session.getAttribute("map");
		// Get sorting type
		String sort = request.getParameter("sort");
		// Get name array
		String name_array[] = new String[map.size()];
		String value[] = name_array.clone();
		int j = 0;
		for(String key: map.keySet()) {
			name_array[j++] = key;
		}
		//Decide value array
		int direction = 1;
		if(sort.equals("name")) {
			value = name_array.clone();
			direction = 1;
		}
		else if(sort.equals("named")) {
			value = name_array.clone();
			direction = -1;
		}
		else if(sort.equals("templow")) {
			for(int i = 0;i < name_array.length;i ++)
				value[i] = map.get(name_array[i]).dayLow;
			direction = 1;
		}
		else if(sort.equals("templowd")) {
			for(int i = 0;i < name_array.length;i ++)
				value[i] = map.get(name_array[i]).dayLow;
			direction = -1;
		}
		else if(sort.equals("temphigh")) {
			for(int i = 0;i < name_array.length;i ++)
				value[i] = map.get(name_array[i]).dayHigh;
			direction = 1;
		}
		else if(sort.equals("temphighd")) {
			for(int i = 0;i < name_array.length;i ++)
				value[i] = map.get(name_array[i]).dayHigh;
			direction = -1;
		}
		
		name_array = string_sort(name_array, value, direction);
		String [] templow = new String[name_array.length];
		String [] temphigh = new String[name_array.length];
		for(int i = 0;i < name_array.length;i ++) {
			templow[i] = map.get(name_array[i]).dayLow;
			temphigh[i] = map.get(name_array[i]).dayHigh;
		}
		session.setAttribute("name_array", name_array);
		session.setAttribute("templow", templow);
		session.setAttribute("temphigh", temphigh);
    }
    
    static String [] string_sort(String [] name, String [] value, int direction) {
    	assert(name.length <= value.length);
    	String temp = null;
    	int count = name.length;
    	int sentry = count - 1;
    	while(sentry != 0) {
    		int i = 0;
    		while(i < sentry) {
    			if(value[i].compareTo(value[i+1]) * direction > 0) {
    				temp = value[i];
    				value[i] = value[i+1];
    				value[i+1] = temp;
    				
    				temp = name[i];
    				name[i] = name[i+1];
    				name[i+1] = temp;
    			}
    			i ++;
    		}
    		sentry --;
    	}
    	return name;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

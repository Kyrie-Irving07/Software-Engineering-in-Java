import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Exception;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONObject;


public class Test{
	public static void main(String []args) throws Exception 
	{
		
		
//		String a = "ANVD";
//		System.out.println(a.charAt(2) + a.charAt(1));
		
//		ArrayList<String> a = new ArrayList<String>();
//		a.add("Hello");
//		a.add("world");
//		System.out.println(a.size());
//		System.out.println(a.get(0));
//		System.out.println(a.get(0));
//		System.out.println(a.remove(0));
//		System.out.println(a.remove(0));
		
//		String s = "GET /test.html HTTP/1.1";
//		String [] array = s.split("/");
//		String [] dest = array[1].split(" ");
//		System.out.println(dest[0]);
//		for(int i = 0; i < 20; i ++) {
//			Random rand = new Random(); 
//			int rand_int = rand.nextInt(1000); 
//			System.out.println(rand_int);
//		}
		
//		Queue<String> queue = new LinkedList();
//		System.out.println("Content: " + queue);
//		queue.add("Hello World");
//		System.out.println("Content: " + queue.remove() + " Size: " + queue.size());
//		queue.add("123");
		
//		System.out.println("<img src=\"/home/ubuntu/yangjie/testimage/" + "file.png" + "\" width=\"200\" height=\"200\"><br />\n");
//		
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  
//	    LocalDateTime now = LocalDateTime.now();
//	    System.out.println(dtf.format(now)); 
		
//		File f = new File("./image");
//		File[] paths = f.listFiles();
//        
//        for(File path:paths) {
//           System.out.println(path.getName());
//        }
//		System.out.println(paths[1]);
//		String a[] = new String[2];
//		a[0] = "cbc";
//		a[1] = "cba";
//		Arrays.parallelSort(a);
//		System.out.println(a[0] + "||" + a[1]);
		
//		System.out.println(1.23+"asdf");
//		String cityname = "putian";
//		int id = 0;
//		try {
//			FileReader fr = new FileReader("./city.list.json");
//			BufferedReader br = new BufferedReader(fr);
//			String inputLine;
//			StringBuffer list = new StringBuffer();
//			while ((inputLine = br.readLine()) != null) {
//				list.append(inputLine);
//			} br.close();
//	    	JSONArray citylist = new JSONArray(list.toString());
//			fr.close();
//			br.close();
//			
//			for (int i = 0; i < citylist.length(); i ++)
//			{
//				JSONObject temp = citylist.getJSONObject(i);
//				if(temp.getString("name").toLowerCase().equals(cityname)) {
//					id = temp.getInt("id");
//					break;
//				}
//			}
//		} catch(FileNotFoundException fnfe) {
//			System.out.println("fnfe : " + fnfe.getMessage());
//		} catch(IOException ioe) {
//			System.out.println("ioe : " + ioe.getMessage());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println(id);
//		
//		try {
//	    	  String appid = "&appid=b710617ec75d20e7e78bc8644671453c";
//	    	  String url = "http://api.openweathermap.org/data/2.5/weather?id=";
//	    	  String t = url+id+appid;
//	    	  URL obj = new URL(t);
//	    	  HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//	    	  int responseCode = con.getResponseCode();
//	    	  System.out.println("\nSending 'GET' request to URL : " + t);
//	    	  System.out.println("Response Code : " + responseCode);
//	    	  BufferedReader in =new BufferedReader(
//	    	  new InputStreamReader(con.getInputStream()));
//	    	  String inputLine;
//	    	  StringBuffer response = new StringBuffer();
//	    	   while ((inputLine = in.readLine()) != null) {
//	    	     response.append(inputLine);
//	    	   } in .close();
//	    	   //print in String
//	    	   // System.out.println(response.toString());
//	    	   JSONObject myresponse = new JSONObject(response.toString());
//	    	   System.out.println(myresponse);
//	    	   System.out.println("name -" + myresponse.getString("name"));
//	    	   JSONObject coord = new JSONObject(myresponse.getJSONObject("coord").toString());
//	    	   System.out.println("coord -" + coord);
//	    	   System.out.println("longitude -" + coord.getDouble("lon"));
//	    	   System.out.println("latitude -" + coord.getDouble("lat"));
//	    	  } catch(Exception e) {
//	    	    System.out.println(e);
//	    	  }
//		
//		String a = "I love you;";
//		a += " end";
//		System.out.println(a);
		
//		T t0 = new T("Lebron");
//		T t1 = new T("Kevan");
//		T t2 = new T("Kyrie");
//		
//		ExecutorService executor = Executors.newFixedThreadPool(3);
//		executor.execute(t0);
//		executor.execute(t1);
//		executor.execute(t2);
//		executor.shutdown();
//		
//		while(!executor.isTerminated()) {
//			Thread.yield();
//		}
//		
//		System.out.println("Last Line");
		
//		String s1 = "CSCI 201";
//		String s2 = s1;
//		System.out.println(s1);
//		System.out.println(s2);
//		s1 = s1.replace('2', '1');
//		System.out.println(s1);
//		System.out.println(s2);
		
		
//		System.out.println("test sucess\n");
//		MyObject a = new MyObject(1);
//		MyObject b = new MyObject(2);
//		System.out.println(MyObject.const_value);
//		MyObject.const_value ++;
//		System.out.println(MyObject.const_value);
//		
//		System.out.println("Object Swap");
//		System.out.println("a = " + a.value + ", b = " + b.value);
//		swap(a, b);
//		System.out.println("a = " + a.value + ", b = " + b.value);
//		
//		System.out.println("\nInteger Swap");
//		int aa = 1;
//		int bb = 2;
//		System.out.println("aa = " + aa + ", bb = " + bb);
//		swap_int(aa, bb);
//		System.out.println("aa = " + aa + ", bb = " + bb);
		
		
//		String [] a = {"c", "d", "a", "b"};
//		String [] b = a.clone();
//		a[1] = "123";
//		System.out.print(b[1]);
//		int a = 0;
//		add_one(a);
//		System.out.println(a);
//		throw new Exception("I don't know what it is.\n");
		
		// Initialize String array by this way.
		// You can assign a String array to another String array
//		String []s = {"test ", "sucess !"};
//		String []ss = new String[2];
//		ss =s;
//		System.out.println(ss[0] + ss[1]);
//		
//		Scanner scan = new Scanner(System.in);
//		String command = scan.nextLine();
//		System.out.println(command.length());
//		if((int)command.charAt(0) > (int)'5')
//		{
//			System.out.println("Larger than 5.");
//		}
//		System.out.println(command);
//		// Print the number of char matches.
//		System.out.print(command.contentEquals("the"));
//		// Print a boolean number.
//		System.out.print(command.equals("the"));
//		scan.close();
//		
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("ABC", "abc");
//		String a = map.get("ab1c");
//		System.out.println(a);
	}
	
	public void count() {
		int i = 0;
		System.out.println(i++);
	}
	
	public void test(Boss b) {
		System.out.println(b.money);
	}
	
	public static void swap(MyObject a, MyObject b) {
		int temp = a.value;
		a.value = b.value;
		b.value = temp;
	}
	
	public static void swap_int(int a, int b) {
		int temp = a;
		a = b;
		b = temp;
	}
	
	public static void add_one(int input)
	{
		input = input + 1;
	}
}

class T extends Thread {
	private String name;
	public T (String name) {
		this.name = name;
	}
	public void run() {
		for(int i = 0; i < 20; i ++)
			System.out.println(name + i);
	}
}

class uncertain <T> {
	public void func(T t) {
		System.out.println(t);
	}
}


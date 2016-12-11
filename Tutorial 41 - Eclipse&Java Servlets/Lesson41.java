package helloservlets;

import java.io.IOException;
import java.io.PrintWriter; // contains methods that allow us to print out to the browser
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet; // declares a config to the servlet
import javax.servlet.http.HttpServlet; // abstract class used to create servlets
import javax.servlet.http.HttpServletRequest; // handles requests to get and post methods
import javax.servlet.http.HttpServletResponse; // handles the responses

/**
 * Servlet implementation class Lesson41
 */

@WebServlet("/Lesson41")
public class Lesson41 extends HttpServlet {
	private static final long serialVersionUID = 1L;
                  // any class that implements this serializable interface is required to define an ID number
                  // if we dont do it eclipse automatically gives us one

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */ 
                  // server calls this method to handle any type of get request issued by browser
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html"); // defines type of info passed
		
		PrintWriter output = response.getWriter(); // allows us to print on browser
		
		output.println("<html><body><h3>Hello Servlets</h3></body></html>");
                                    // type of info we are printing out to the browser
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

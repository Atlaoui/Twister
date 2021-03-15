package twetwe.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import twetwe.service.UserService;

public class Search extends HttpServlet {

	
	private static final long serialVersionUID = 1L;
	
	
	public Search() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String key = req.getParameter("key");
		String what = req.getParameter("query");
		String who = req.getParameter("who");
		PrintWriter out = resp.getWriter();
		JSONObject jo=null;
		try {	
			
			jo = UserService.searchUser(key,what,who);// je ne comprend pas le truck des amis 

			resp.setContentType("text/plain");
			out.print(jo.toString());

			} finally {
				out.close();
			}
	}
	
}

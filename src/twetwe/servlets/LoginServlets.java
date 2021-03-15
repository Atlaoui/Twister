package twetwe.servlets;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import twetwe.service.loginService;



public class  LoginServlets  extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public LoginServlets () {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		
		
		//a gerer le cas ou l'utilisateur est root
		String root = req.getParameter("root");
		
		PrintWriter out = resp.getWriter();

		JSONObject jo=null;

		try {	
			
		jo = loginService.login(login, password,Boolean.parseBoolean(root));
		
		resp.setContentType("text/plain");
		out.print(jo.toString());

		} finally {
			out.close();
		}

	}
}

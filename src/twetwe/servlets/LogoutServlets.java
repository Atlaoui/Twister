package twetwe.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import twetwe.service.loginService;

public class LogoutServlets extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public LogoutServlets() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String key = req.getParameter("key");
		PrintWriter out = resp.getWriter();
		JSONObject jo=null;
		try {	
			
			jo = loginService.logOut(key);

			resp.setContentType("text/plain");
			out.print(jo.toString());

			} finally {
				out.close();
			}
	}
}

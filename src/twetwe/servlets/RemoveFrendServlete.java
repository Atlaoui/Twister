package twetwe.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import twetwe.service.FrendService;

public class RemoveFrendServlete extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public RemoveFrendServlete () {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String key = req.getParameter("key");
		String frend = req.getParameter("id_frend");
		PrintWriter out = resp.getWriter();
		JSONObject jo=null;
		try {
			jo=FrendService.removefrend(key, frend);
			
			resp.setContentType("text/plain");
			out.print(jo.toString());			
		}finally {
			out.close();
		}
		
	}

	
}

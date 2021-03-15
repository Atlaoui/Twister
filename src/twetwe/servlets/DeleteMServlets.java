package twetwe.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import twetwe.service.MessagerieService;

public class DeleteMServlets extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5279367351254520019L;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		String key = req.getParameter("key");
		String ObjId = req.getParameter("ObjId");
		
		PrintWriter out = resp.getWriter();
		JSONObject jo=null;
		try {	
				
			jo=MessagerieService.removeMessage(key, ObjId);
			

			resp.setContentType("text/plain");
			out.print(jo.toString());

			} finally {
				out.close();
			}

	}
	

}

package twetwe.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import twetwe.service.PrivateMessageService;

public class AddPrivateMessageServlets extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String key = req.getParameter("key");
		String id_Util = req.getParameter("id2");
		String message = req.getParameter("content");
		
		PrintWriter out = resp.getWriter();
		JSONObject jo=null;
		try {	
			
			jo=PrivateMessageService.addPrivateMessage(key, id_Util, message);
			
			resp.setContentType("text/plain");
			out.print(jo.toString());

			} finally {
				out.close();
			}
	}
}

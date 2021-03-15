package twetwe.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import twetwe.service.FrendService;
import twetwe.service.MessagerieService;

public class GetProfilData extends HttpServlet{


	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String key = req.getParameter("key");
		String id_amis = req.getParameter("id_amis");
		PrintWriter out = resp.getWriter();
		JSONObject jo=null;
		
		try {	
			jo=MessagerieService.getMessage(key,id_amis);
			try {
				jo.put("Frend",FrendService.getFrends(key));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			resp.setContentType("text/plain");
			out.print(jo.toString());

			} finally {
				out.close();
			}

	}
	
	
	

}

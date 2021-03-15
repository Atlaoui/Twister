package twetwe.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import twetwe.service.LikeService;

public class AddLike extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public AddLike() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String key = req.getParameter("key");
		String ObjId = req.getParameter("ObjId");
		PrintWriter out = resp.getWriter();
		JSONObject jo=null;
		try {	
			
			jo = LikeService.addLikeService(key,ObjId);

			resp.setContentType("text/plain");
			out.print(jo.toString());

			} finally {
				out.close();
			}
	}
}

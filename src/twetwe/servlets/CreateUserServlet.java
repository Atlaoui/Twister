package twetwe.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import twetwe.service.UserService;

public class CreateUserServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;

	public CreateUserServlet() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String login = req.getParameter("login");
		String nom = req.getParameter("nom");
		String prenom = req.getParameter("prenom");
		String mail= req.getParameter("mail");
		String password=req.getParameter("password");
		String anniv = req.getParameter("anniv");
		String sexe = req.getParameter("sexe");
		PrintWriter out = resp.getWriter();
		JSONObject jo=null;
		try {
			
			jo = UserService.newUser(login, password, mail, nom, prenom, anniv, sexe);

			resp.setContentType("text/plain");
			out.print(jo.toString());

			} finally {
				out.close();
			}
	}
}

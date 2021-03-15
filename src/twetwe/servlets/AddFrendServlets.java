package twetwe.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import twetwe.service.FrendService;


public class AddFrendServlets extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String key = req.getParameter("key");
		String frend = req.getParameter("id_frend");
		
		PrintWriter out = resp.getWriter();
		JSONObject jo=null;
		try {	
			
			jo=FrendService.addfrend(key, frend);
			
			resp.setContentType("text/plain");
			out.print(jo.toString());

			} finally {
				out.close();
			}

	}

	
}

/*	public static JSONObject searchFriend(String pseudo, String user_key) throws JSONException {
		JSONObject retour = new JSONObject();
		
		//Test des champs
		if(pseudo==null || user_key == null) {
			return ErrorJSON.serviceRefused("Champs manquants", -1);
		}
		
		try {
			Connection conn = Database.getMySQLConnection();
			//Verif existance du futur ami
			if(!UserBDTools.checkUserExist(pseudo, conn)) {
				conn.close();
				return ErrorJSON.serviceRefused("Utilisateur "+pseudo+" inconnu", 1000);
			}
			//Verif de la cle
			String key = UserBDTools.checkKeyUpdate(user_key, conn);
			if (key == null) {
				conn.close();
				return ErrorJSON.serviceRefused("Erreur cle correspondance ou timestamp depasse", 1000);
			}
			//Verif que l'ami n'est pas le demandeur
			if (RelationBDTools.keyPseudoEquals(user_key, pseudo, conn)) {
				conn.close();
				return ErrorJSON.serviceRefused("On ne peut pas etre son propre ami ;) ", 1000);
			}
			
			//Recup id de l'ami et Recup id du demandeur
			int friendID = UserBDTools.getUserId(pseudo, conn);
			int iD = UserBDTools.getUserIdfromKey(key, conn);
			retour = RelationBDTools.searchFriend(friendID, iD,conn);
			//Insetion dans la BD de la relation
			if(retour == null) {
				conn.close();
				return ErrorJSON.serviceRefused("Je ne trouve pas "+pseudo, 1000);
			}
			
			//Great Succes !
			retour.put("new_key", key);
			retour.put("status", "OK");
			conn.close();
		}
		catch (SQLException e) {
			return ErrorJSON.serviceRefused("SQL probleme // "+e.getMessage(), 1000);
		}
		
		return retour;
	}*/

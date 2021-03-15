package twetwe.service;

import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import twetwe.BD.SessionDataBase;
import twetwe.BD.UserDataBase;
import twetwe.tools.Code;
import twetwe.tools.DBConecectors;
import twetwe.tools.ExceptionJason;
import twetwe.tools.Format;

import java.sql.Connection;


public class loginService {
	
	
	

	
	//OP
	public static JSONObject login(String login, String password , boolean root) {
		if(login==null || password==null)
			return ExceptionJason.serviceRefused("le login ou le password sont inconu", -1);
		Connection conn =null;
		String key="";
		try {
			conn = DBConecectors.getMySQLConnection();
			
			if(!UserDataBase.CheckUserByLogin(login, conn)) {
				conn.close();
				return  ExceptionJason.serviceRefused("Inconnu le gonin est inconu", 1);
			}
			if(!UserDataBase.checkPassword(login, password, conn)) {
				conn.close();
				return  ExceptionJason.serviceRefused("Inconnu password ou loging incorect", 1);
			}

			System.out.println("SessInset");
			key = SessionDataBase.sessInsertAndKeyG(login, root, conn);

			System.out.println("SessInsetddd");
		} catch (SQLException e) {
			e.printStackTrace();
			return ExceptionJason.serviceRefused( e.toString() , Code.ERREU_SQL );
		}
		
		JSONObject json = ExceptionJason.serviceAccepted();
		
		try {
			json.put("key", key);
			json.put("user_id", UserDataBase.getUserId(login, conn));
			json.put("login", login);
			json.put("isroot", root);
		} catch (JSONException e) {
			e.printStackTrace();
			return ExceptionJason.serviceRefused(e.toString(),Code.ERREUR_JSON);
		} catch (SQLException e) {
			e.printStackTrace();
			return ExceptionJason.serviceRefused(e.toString(), Code.ERREU_SQL);
		}finally {
			try {
				if(conn!=null && !conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return json;
	}
	
	

	//OP
	public static JSONObject logOut(String key) {
		if(!Format.checkKeyFormat(key))
			return ExceptionJason.serviceRefused("pas d'argument", -1);
		Connection conn = null;
		try {
			conn = DBConecectors.getMySQLConnection();
			SessionDataBase.deleteSession(key, conn);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return ExceptionJason.serviceRefused( "SQLQueryException" , 1000 );

		}finally {
			try {
				if(conn!=null && !conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return ExceptionJason.serviceAccepted();
	}


}









/*		if(loging==null || password == nul){
		return SericeTools.serviceRefused("pas d'argument",-1);
				try{
					if(!ServicesTools.checkUserExist) 
						return (secureTools.serviceRefused("UserName usess",1);
						if(!ServicesTools.checkUserPassword(login,ppassword)
								return ServiceTools.serviceRefused("Bad password",2)
										int id_user = BD.getUsId(login);
						Starting key = BD.insertConnexion(id_user , false)

								JSOObject retour = new JSONObject(),
								retour.put("Status","Ok");
						retour.put("keys",key)

				}Catch(JSONException e ){
					retourn ServicesTools.serviceRefusd(Json PB +getMessage());
					catch (SQLQuerryException e){ return ServciesTools.servicesRefused(" pb BD" 1000,-1} 	
				}
	} 
}





public static JSONObject loging( String loging, String password){
	if(loging==null || password == null){
		return SericeTools.serviceRefused("pas d'argument",-1);

	return ;

}
 */




/*Description permet la conextion d'un utilisateur enregistrer 
 * parametre loging password 
 * format de sortie 
 * Jason Ok et une clef de conextion ou erreur du loging 
 * Erreur possible si l'utilisateur n'existe pas parametre -1 
 * 
 * JSON bjzct loging(string loging , string password)
 * 1 verification des parametres (non null)
 * 2 checkUser(Check user) -> si existe pas 
 * 3 verification correspondance de mot de passe 
 * 4 key <- InsererConextion (loging, root,Date)
 * 5 redonner a l'utilisateur ca clé de conextion 
 * 5 retourner un Json Avec la clé { status "ok"
 * 									"key" "xxxx"}
 * Quesrion 3 les fonctions 
 * 
 * 
 * les fonction dont j'ai besoin pour faire fonctionner ce service 
 * checkuserExist(String loging) -> boolean
 * possible Erreur BD
 * public static String insertConnexion(int id , boolean not ; 
 * public static boolean checklogingPassword(String loging ,String password))
 * public  static int getUserId(String loging)
 * 
 * public class User {
 *  
 * 
 * public class loging extred Http Servlet {
 * 	prodected void doGet(HttpServletRequest ,HTTPServletRespponse reponse)
 * 
 * String Loging = request.getpparm...
 * 
 * JSONObject retour = Service.UserLoging(loging,pwd )
 * reponse.setContentType("fixet, plain)
 * printWarterant = response.getPrintWrite().out.prit(retour.toString));
 * 
 * }
 * 
 */
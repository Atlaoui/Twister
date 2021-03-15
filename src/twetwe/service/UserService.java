package twetwe.service;


import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;

import twetwe.BD.SessionDataBase;
import twetwe.BD.UserDataBase;
import twetwe.tools.Code;
import twetwe.tools.DBConecectors;
import twetwe.tools.ExceptionJason;
import twetwe.tools.Format;

public class UserService {




	//pour simplifier les teste j'ai enlver les regexp 
	public static JSONObject newUser(String login, String password, String mail, String nom, String prenom,String anniversaire,String sexe){

		
		
		if(!Format.checkPassword(password) || !Format.checkMail(mail) || !Format.checkNomPrenom(nom, prenom) || sexe==null || anniversaire==null)
		//if(password==null || login == null || mail ==null || nom ==null || prenom == null || sexe==null || anniversaire==null )
			return  ExceptionJason.serviceRefused("Paramètre(s) vide(s) ou mal remplis", Code.ERREUR_FORMAT);
		Connection conn=null;
		String teste="";
		try {
			conn = DBConecectors.getMySQLConnection();
			teste="apres";
			if(UserDataBase.CheckUserByLogin(login, conn)) {
				conn.close();
				return ExceptionJason.serviceRefused("Login existe deja", -2);
			}
			if(UserDataBase.mailExist(mail,conn)) {
				conn.close();
				return ExceptionJason.serviceRefused("le mail est deja lier a un autre compte", -3);

			}
			UserDataBase.insertUser(login, password, mail, nom, prenom, anniversaire, sexe, conn);

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();

			return ExceptionJason.serviceRefused(e.toString()+teste, 1000 );
		}finally {
			try {
				if(conn!=null && !conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		return ExceptionJason.serviceAccepted();
	}



	//OP
	public static JSONObject deleteUser(String login,String password) {
		if(login==null || password==null)
			return ExceptionJason.serviceRefused("argument null", Code.ERREUR_FORMAT);
		Connection conn=null;
		try {
			conn = DBConecectors.getMySQLConnection();
			if(!UserDataBase.checkPassword(login, password, conn)) {
				conn.close();
				return ExceptionJason.serviceRefused("Erreur SQL",Code.ERREU_SQL);
			}
			UserDataBase.deleteUser(login, conn);

		} catch (SQLException e) {
			e.printStackTrace();
			return ExceptionJason.serviceRefused("Erreur SQL",Code.ERREU_SQL);

		}finally {
			try {
				if(conn!=null && !conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return ExceptionJason.serviceAccepted();	
	}






	public static JSONObject searchUser(String key , String query ,String friends) {
		if(!Format.checkKeyFormat(key) || (query==null && friends == null))
			return ExceptionJason.serviceRefused("Paramètre(s) vide(s) ou mal remplis", -1);
		Connection conn=null;
		if(friends!=null) {
			try {
				conn = DBConecectors.getMySQLConnection();
				
				if(!SessionDataBase.connectionOneHour(key, conn))
					return ExceptionJason.serviceRefused("Erreur Refresh", Code.ERREUR_FORMAT);	
			
				
				if(!SessionDataBase.checkKey(key, conn))
					return ExceptionJason.serviceRefused("Tu n'est plus connecter",1);

				JSONObject jo= ExceptionJason.serviceAccepted();
	
				jo.put("Amis", UserDataBase.Search(friends,conn));
				return jo;

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				return ExceptionJason.serviceRefused(e.toString(),Code.ERREUR_JSON);
			} catch (SQLException e) {

				return ExceptionJason.serviceRefused(e.toString(),Code.ERREU_SQL);
			}finally {
				try {
					if(conn!=null && !conn.isClosed())
						conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}


		return ExceptionJason.serviceRefused("pas bon SearchUser",666);
	}

}




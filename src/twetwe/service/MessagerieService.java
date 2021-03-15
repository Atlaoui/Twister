package twetwe.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;



import twetwe.BD.LikesDB;
import twetwe.BD.MessagerieDB;
import twetwe.BD.SessionDataBase;
import twetwe.BD.UserDataBase;
import twetwe.BD.followDataBase;
import twetwe.tools.Code;
import twetwe.tools.DBConecectors;
import twetwe.tools.ExceptionJason;
import twetwe.tools.Format;

public class MessagerieService {
		
	//OP
	public static JSONObject removeMessage(String key, String ObjectId) {
		if(!Format.checkKeyFormat(key) || ObjectId==null)
			return ExceptionJason.serviceRefused("Erreur clée", Code.ERREUR_FORMAT);		
		Connection conn =null;
		int id ;
		try {
			
			conn=DBConecectors.getMySQLConnection();
			
			if(!SessionDataBase.connectionOneHour(key, conn))
				return ExceptionJason.serviceRefused("Erreur Refresh", Code.ERREUR_FORMAT);	
			
			id=SessionDataBase.getUserBykey(key, conn);
			System.out.println(id);
		if(!MessagerieDB.testIdPoster(id, ObjectId))
			return ExceptionJason.serviceRefused("Ce n'est pas ton message", Code.ERREUR_INCONU);
		
			MessagerieDB.supprimerMessage(id, ObjectId);
			//tester elle est OP
			LikesDB.DeleteAllLikes(ObjectId,conn);
		
		} catch (SQLException e) {
			return ExceptionJason.serviceRefused(e.getMessage(), Code.ERREU_SQL);
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
	
	//OP
	public static JSONObject addMessage(String key, String message) {
		if(!Format.checkKeyFormat(key))
			return ExceptionJason.serviceRefused("Erreur clée", Code.ERREUR_FORMAT);
		Connection conn = null;
		int id=-1;
		try {	
			 conn = DBConecectors.getMySQLConnection();
			 
			if(!SessionDataBase.connectionOneHour(key, conn))
				return ExceptionJason.serviceRefused("Erreur Refresh", Code.ERREUR_FORMAT);	
			
			
			 
			 id=SessionDataBase.getUserBykey(key,conn);
			 
			 
			 String Name=UserDataBase.getUserLogin(id, conn);
			MessagerieDB.ajouterMessage(id, Name, message);
				
		} catch (SQLException e) {
			return ExceptionJason.serviceRefused(e.getMessage(), Code.ERREU_SQL);
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


	//en renvois soit les message d'un utilisateur soit celui d'un compte qu'il voudrait visité
	//OP
	public static JSONObject getMessage(String key ,String id){
		Connection conn = null;
		try {
			conn = DBConecectors.getMySQLConnection();
			
			if(!SessionDataBase.connectionOneHour(key, conn))
				return ExceptionJason.serviceRefused("Erreur Refresh", Code.ERREUR_FORMAT);	
		
		
			
			
			if(!SessionDataBase.checkKey(key, conn))
				return ExceptionJason.serviceRefused("erreur de clé", -1);
			
			ArrayList<Integer> listAmis;
			if(id!=null) {
				listAmis = new ArrayList<>();
				listAmis.add(Integer.parseInt(id));
			}else {
			listAmis =followDataBase.getAllFrendID(key,conn);
			}
			
			System.out.println(listAmis);
			
			//si il n'a pas d'amis
			if(listAmis.isEmpty() && id ==null)
				listAmis.add(SessionDataBase.getUserBykey(key, conn));
				
			JSONObject jo = ExceptionJason.serviceAccepted();
			return jo.put("Message", MessagerieDB.ListMessage(listAmis,conn));
		
		} catch (SQLException | JSONException e) {
			return ExceptionJason.serviceRefused(e.getMessage(), Code.ERREU_SQL);
			
		}
		finally {
			try {
				if(conn!=null && !conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
}


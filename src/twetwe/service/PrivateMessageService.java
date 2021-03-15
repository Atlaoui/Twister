package twetwe.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import twetwe.BD.MessagePriveDB;
import twetwe.BD.SessionDataBase;
import twetwe.BD.UserDataBase;
import twetwe.tools.Code;
import twetwe.tools.DBConecectors;
import twetwe.tools.ExceptionJason;
import twetwe.tools.Format;

public class PrivateMessageService {

	//A tester
	public static JSONObject removePrivateMessage(String key, String ObjectId) {
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
		if(!MessagePriveDB.testIdPosterPrivate(id, ObjectId))
			return ExceptionJason.serviceRefused("Ce n'est pas ton message", Code.ERREUR_INCONU);
		
		MessagePriveDB.supprimerPrivateMessage(id, ObjectId);
	
		
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
	public static JSONObject addPrivateMessage(String key, String id_utili, String message) {
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
			 MessagePriveDB.ajouterPrivateMessage(id, Integer.parseInt(id_utili),Name,message);
				
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

	
	
	public static JSONObject getMessagePrivate(String key ,String id2){
		
		if(!Format.checkKeyFormat(key) || id2==null)
			return ExceptionJason.serviceRefused("Erreur clée", Code.ERREUR_FORMAT);
		
		Connection conn = null;
		try {
			conn = DBConecectors.getMySQLConnection();
			if(!SessionDataBase.checkKey(key, conn))
				return ExceptionJason.serviceRefused("erreur de clé", -1);
			
			if(!SessionDataBase.connectionOneHour(key, conn))
				return ExceptionJason.serviceRefused("Erreur Refresh", Code.ERREUR_FORMAT);	
		
			
			
			int id_util = SessionDataBase.getUserBykey(key, conn);
				
			JSONObject jo = ExceptionJason.serviceAccepted();
			return jo.put("MessagePrive", MessagePriveDB.ListMessagePrivate(id_util, Integer.parseInt(id2)));
		
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

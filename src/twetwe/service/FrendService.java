package twetwe.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import twetwe.BD.SessionDataBase;
import twetwe.BD.UserDataBase;
import twetwe.BD.followDataBase;
import twetwe.tools.Code;
import twetwe.tools.DBConecectors;
import twetwe.tools.ExceptionJason;
import twetwe.tools.Format;

public class FrendService {




	//OP
	public static JSONObject addfrend(String key, String id_frend) {

		/*!Format.checkKeyFormat(key))*/
		if(key==null || id_frend==null) 
			return ExceptionJason.serviceRefused("Paramètre(s) vide(s)", 104);
		Connection conn = null;
		try {

			conn =DBConecectors.getMySQLConnection();
			
			if(!SessionDataBase.connectionOneHour(key, conn))
				return ExceptionJason.serviceRefused("Erreur Refresh", Code.ERREUR_FORMAT);	




			if(!UserDataBase.checkUserById(id_frend,conn)) {
				conn.close();
				return ExceptionJason.serviceRefused("Pas d'amis", 104);
			}

			followDataBase.addlink(key, id_frend, conn);

			return ExceptionJason.serviceAccepted();

		} catch (SQLException e) {
			e.printStackTrace();
			return ExceptionJason.serviceRefused( "SQLQueryException" , 1000 );
		}finally {
			try {
				if(conn!=null && !conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				return ExceptionJason.serviceRefused(e.getMessage(), Code.ERREU_SQL);
			}
		}


	}

	//OP
	public static JSONObject removefrend(String key, String id_frend) {

		if((!Format.checkKeyFormat(key)) || id_frend==null) 
			return ExceptionJason.serviceRefused("Paramètre(s) vide(s)", 104);
		Connection conn=null;
		try {
			conn =DBConecectors.getMySQLConnection();
			
			if(!SessionDataBase.connectionOneHour(key, conn))
				return ExceptionJason.serviceRefused("Erreur Refresh", Code.ERREUR_FORMAT);	
		
		
			
			if(!UserDataBase.checkUserById(id_frend,conn)) {
				conn.close();
				return ExceptionJason.serviceRefused("Pas d'amis", 104);
			}

			followDataBase.removelink(key, id_frend, conn);
			System.out.println("j'ai suprimer");
			return ExceptionJason.serviceAccepted();

		} catch (SQLException e) {
			e.printStackTrace();
			return ExceptionJason.serviceRefused( "SQLQueryException" , 1000 );
		}finally {
			try {
				if(conn!=null && !conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				return ExceptionJason.serviceRefused(e.getMessage(), Code.ERREU_SQL);
			}
		}
	}

	//OP de chez OP
	public static JSONObject getFrends(String key) {
		JSONArray array = new JSONArray();

		if((!Format.checkKeyFormat(key))) 
			return ExceptionJason.serviceRefused("Paramètre(s) vide(s) mal remplis", 104);
		Connection conn=null;
		try {
			conn =DBConecectors.getMySQLConnection();
			if(!SessionDataBase.connectionOneHour(key, conn))
				return ExceptionJason.serviceRefused("Erreur Refresh", Code.ERREUR_FORMAT);	
		
		
			
			
			if(!SessionDataBase.checkKey(key,conn)) {
				conn.close();
				return ExceptionJason.serviceRefused("Pas d'amis", 104);
			}
			array = followDataBase.getAllFrends(key,conn);
			JSONObject jo = ExceptionJason.serviceAccepted();
			return jo.put("Amis",array);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ExceptionJason.serviceRefused( "SQLQueryException" , 1000 );
		} catch (JSONException e) {
			// TODO Auto-generated catch block

			return ExceptionJason.serviceRefused( e.toString() , 10 );
		}finally {
			try {
				if(conn!=null && !conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				return ExceptionJason.serviceRefused(e.getMessage(), Code.ERREU_SQL);
			}
		}
	}



}






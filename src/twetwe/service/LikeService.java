package twetwe.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.json.JSONObject;

import twetwe.BD.LikesDB;
import twetwe.BD.SessionDataBase;
import twetwe.tools.Code;
import twetwe.tools.DBConecectors;
import twetwe.tools.ExceptionJason;

public class LikeService {

	public static JSONObject addLikeService(String key ,String  _id) {

		if(key==null || _id==null)
			return ExceptionJason.serviceRefused("Param�tre(s) vide(s)", 104);
		Connection conn=null;
		try {
			conn =DBConecectors.getMySQLConnection();

			if(!SessionDataBase.connectionOneHour(key, conn))
				return ExceptionJason.serviceRefused("Erreur Refresh", Code.ERREUR_FORMAT);	



			int id=SessionDataBase.getUserBykey(key,conn); 
			if(id==-1) {
				conn.close();
				return ExceptionJason.serviceRefused("Pas d'amis", 104);
			}

			LikesDB.addLike(id, _id, conn);
			return ExceptionJason.serviceAccepted();

		} catch (SQLException e) {
			e.printStackTrace();
			return ExceptionJason.serviceRefused( "SQLQueryException" , 1000 );
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

	public static JSONObject removeLike(String key ,String  _id) {

		if(key==null || _id==null)
			return ExceptionJason.serviceRefused("Param�tre(s) vide(s)", 104);
		Connection conn=null;
		try {
			conn =DBConecectors.getMySQLConnection();

			if(!SessionDataBase.connectionOneHour(key, conn))
				return ExceptionJason.serviceRefused("Erreur Refresh", Code.ERREUR_FORMAT);	


			int id=SessionDataBase.getUserBykey(key,conn); 
			if(id==-1) {
				conn.close();
				return ExceptionJason.serviceRefused("Pas d'amis", 104);
			}

			LikesDB.DeleteLike(id, _id, conn);
			return ExceptionJason.serviceAccepted();

		} catch (SQLException e) {
			e.printStackTrace();
			return ExceptionJason.serviceRefused( "SQLQueryException" , 1000 );
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






}

package twetwe.BD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import  java.sql.Connection;

public class SessionDataBase {
	public static int SESSION_TIME=3600;
	
	//OP
	public static void deleteSession(String key , Connection conn) throws SQLException {
		Statement rmStatment=conn.createStatement();
		String query= "DELETE FROM sessions WHERE session_key='"+key+"'";
		rmStatment.executeUpdate(query);
		rmStatment.close();
	}
	
	//OP
	public static void deleteSessionById(int id , Connection conn) throws SQLException {
		Statement rmStatment=conn.createStatement();
		String query= "DELETE FROM sessions  WHERE id_user='"+id+"'";
		rmStatment.executeUpdate(query);
		rmStatment.close();
	}
	
	
	//OP
	public static String sessInsertAndKeyG(String login, boolean root, Connection conn) throws SQLException {
		int id= UserDataBase.getUserId(login, conn);
		
		deleteSessionById(id, conn);
		String key = UUID.randomUUID().toString();
		while(checkKey(key,conn))
			key= UUID.randomUUID().toString();
		
		Statement InsertStatment=conn.createStatement();
		String query= "INSERT INTO sessions (session_key,id_user,session_root,session_status) VALUES ('"+key+"','"+id+"','"+((root) ? 1: 0) +"',now())";	
		
		InsertStatment.executeUpdate(query);
		InsertStatment.close();
		return key;
	}
	
	
	public static boolean isRoot(String key,Connection conn) throws SQLException {
		boolean ret;
		Statement keyRootStatment=conn.createStatement();
		String query = "SELECT * session_root FROM sessions WHERE session_key='"+key+"'";
		ResultSet resultat = keyRootStatment.executeQuery(query);
		ret= resultat.getBoolean(3);
		
		keyRootStatment.close();
		resultat.close();
		return ret;
	}
	
	public static boolean checkKey(String key,Connection conn) throws SQLException {
		boolean ret;
		Statement keyStatment=conn.createStatement();
		String query = "SELECT * FROM sessions  WHERE session_key='"+key+"'";
		ResultSet resultat = keyStatment.executeQuery(query);
		ret=resultat.first();
		keyStatment.close();
		resultat.close();
		return ret;
	}
	
	
	public static int getUserBykey(String key,Connection conn) throws SQLException {
		int id=-1;
		Statement keyStatment=conn.createStatement();
		String query = "SELECT * FROM sessions  WHERE session_key='"+key+"'";
		ResultSet resultat = keyStatment.executeQuery(query);
		if(resultat.first())
			id = resultat.getInt(2);
		keyStatment.close();
		resultat.close();
		return id;
	}
	
	
	public static boolean connectionOneHour(String key, Connection conn) throws SQLException {
        //session(session_key(P), user_id*, session_root, session_start)
		String query = "SELECT * FROM sessions WHERE session_key='"+key+"' AND (TIMESTAMPDIFF(HOUR,session_status,NOW())<1 or session_root=1);";
	    Statement st = conn.createStatement();
	    ResultSet r = st.executeQuery(query);
	    
	    if(!r.next()) {
	    	deleteSession(key,conn);
	    	st.close();
	    	r.close();
	    	return false;
        }else {
	    	st.close();
	    	r.close();
        }
	    
		String update = "UPDATE sessions SET session_status=NOW() WHERE session_key='" + key + "';";
		st = conn.createStatement();
		int res = st.executeUpdate(update);
	    st.close();
	    return true;  
	}

}


package twetwe.BD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import twetwe.service.FrendService;


public class UserDataBase {

	//private String url = "http://localhost/phpmyadmin/db_structure.php?server=1&db=twetwe";
	/*(id_user,login,password,mail,nom,prenom,anniversaire,sexe)*/

	//OP
	/**
	 * @return -1 si il n'existe pas int sinon
	 * */
	public static int getUserId(String login, Connection conn) throws SQLException {
		int id=-1;
		Statement UserId=conn.createStatement();
		String query ="SELECT * FROM user  WHERE login='"+login+"'";
		ResultSet resultat = UserId.executeQuery(query);
		if(resultat.next())
			id=resultat.getInt(1);
		UserId.close();
		resultat.close();	
		return id;
	}
	
	public static String getUserLogin(int id ,Connection conn) throws SQLException {
		String name=null;
		Statement UserId=conn.createStatement();
		String query ="SELECT * FROM user  WHERE id_user='"+id+"'";
		ResultSet resultat = UserId.executeQuery(query);
		if(resultat.first())
			name=resultat.getString(2);
		resultat.close();
		UserId.close();
		return name;
	}
	

	//OP
	public static boolean CheckUserByLogin(String login, Connection conn) throws SQLException{
		boolean ret;
		Statement UserExist=conn.createStatement();
		String query = "SELECT * FROM user  WHERE login='"+login+"'";
		ResultSet resultat = UserExist.executeQuery(query);
		ret=resultat.first();

		UserExist.close();
		resultat.close();

		return ret;
	}

	//OP
	public static boolean mailExist(String mail, Connection conn) throws SQLException {
		boolean ret;
		Statement mailExist=conn.createStatement();
		String query = "SELECT * FROM user u WHERE u.mail='"+mail+"'";
		ResultSet resultat = mailExist.executeQuery(query);
		ret=resultat.first();

		mailExist.close();
		resultat.close();
		return ret;
	}



	/*(id_user,login,password,mail,nom,prenom,anniversaire,sexe)*/
	
	public static void insertUser(String login,String password,String mail,String nom, String prenom,String anniversaire,String sexe ,Connection conn) throws SQLException {
		String query = "INSERT INTO user (login, password, mail, nom, prenom,anniversaire,sexe) "
				+ "VALUES('"+login+"','"+password+"','"+mail+"','"+nom+"','"+prenom+"','"+anniversaire+"','"+sexe+"')";
		Statement insertUser = conn.createStatement();
		System.out.println("6");
		insertUser.executeUpdate(query);
		System.out.println("insert1");
		insertUser.close();
	}
	
	//OP
	public static void deleteUser(String login,Connection conn) throws SQLException {
		int id =getUserId(login,conn);
		followDataBase.removeAll(""+id, conn);
		SessionDataBase.deleteSessionById(id, conn);
		LikesDB.DeleteAllLikes(""+id, conn);
		MessagerieDB.supprimerAllMessage(login);
		String query ="DELETE FROM user WHERE login='"+login+"'";
		System.out.println("DELLLLETON");
		Statement deleteUser = conn.createStatement();
		deleteUser.executeUpdate(query);
		deleteUser.close();
	}

	
	//OP
	public static boolean checkPassword(String login, String password,Connection conn) throws SQLException {
		Statement chekpassword = conn.createStatement();
		String query = "SELECT * FROM user WHERE login='"+login+"' AND password='"+password+"'";
		ResultSet resultat = chekpassword.executeQuery(query);
		boolean ret = resultat.next();
		chekpassword.close();
		resultat.close();
		return ret;
	}


	//pas encoer chequer
	public static boolean checkUserById(String id_frend, Connection conn) throws SQLException {
		boolean ret;
		Statement UserExist=conn.createStatement();
		String query = "SELECT * FROM user  WHERE id_user='"+id_frend+"'";

		ResultSet resultat = UserExist.executeQuery(query);
		ret=resultat.first();

		UserExist.close();
		resultat.close();

		return ret;
	}
	/*(id_user,login,password,mail,nom,prenom,anniversaire,sexe)*/

	public static JSONArray Search(String friends, Connection conn) throws SQLException, JSONException {
		Statement userSearch = conn.createStatement();
		String query = "SELECT * FROM user  WHERE login LIKE '"+friends+"%'";
		ResultSet resultat = userSearch.executeQuery(query);
		JSONArray listeUser = new JSONArray();
		JSONObject jo;
		while(resultat.next()) {
			jo = new JSONObject();
			jo.put("id",resultat.getInt(1));
			jo.put("name",resultat.getString(2));
			listeUser.put(jo);
		}
		
		return listeUser;
		
	}



}

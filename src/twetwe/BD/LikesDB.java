package twetwe.BD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LikesDB {
	//OP
	public static void addLike(int id , String _id,Connection conn) throws SQLException {
		String query = "INSERT INTO likes (user_id, message_id) VALUES('"+id+"','"+_id+"')";
		Statement insertLike = conn.createStatement();
		insertLike.executeUpdate(query);
		insertLike.close();
		
	}
	
	//OP
	public static int getLikesByTweet(String Tweet,Connection conn) throws SQLException {
		int nbLigne = 0;
		String query = "SELECT * FROM likes WHERE message_id='"+Tweet+"' ";
		Statement getnbLike = conn.createStatement();
		ResultSet resultat =  getnbLike.executeQuery(query);
		while(resultat.next())
			nbLigne++;
		getnbLike.close();
		resultat.close();
		return nbLigne;
	}
	
	//OP
	public static void DeleteAllLikes(String _id ,Connection conn) throws SQLException {
		String query = "DELETE FROM likes WHERE message_id='"+_id+"' ";
		Statement DeleteLikes = conn.createStatement();
		DeleteLikes.executeUpdate(query);
		DeleteLikes.close();
	}

	//OP
	public static void DeleteAllLikesById(String id ,Connection conn) throws SQLException {
		String query = "DELETE FROM likes WHERE user_id='"+id+"' ";
		Statement DeleteLikes = conn.createStatement();
		DeleteLikes.executeUpdate(query);
		DeleteLikes.close();
	}

	//OP
	public static void DeleteLike(int id, String _id, Connection conn) throws SQLException {
		String query = "DELETE FROM likes WHERE user_id='"+id+"' AND   message_id='"+_id+"'";
		Statement DeleteLikes = conn.createStatement();
		DeleteLikes.executeUpdate(query);
		DeleteLikes.close();	
	}
}

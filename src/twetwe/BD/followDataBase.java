package twetwe.BD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//	(id_user_1;id_user_2;follow_date)
public class followDataBase {

	//OP
	public static void addlink(String key ,String id_frend , Connection conn) throws SQLException {
		//ptet tester si l id est bien 
		int id =SessionDataBase.getUserBykey(key, conn);

		String query = "INSERT INTO follow (id_user_1, id_user_2, follow_date) "
				+ "VALUES('"+id+"','"+id_frend+"',SYSDATE())";

		Statement addfre=conn.createStatement();
		addfre.executeUpdate(query);
		addfre.close();		
	}
	//OP
	public static void removeAll(String id_frend , Connection conn) throws SQLException {
		String query = "DELETE FROM follow WHERE id_user_1='"+id_frend+"' OR id_user_2='"+id_frend+"'";
		Statement removefre=conn.createStatement();
		removefre.executeUpdate(query);
		removefre.close();
	}

	//OP DE CHEZ OP
	public static JSONArray getAllFrends(String key, Connection conn) throws SQLException, JSONException {
		int id=SessionDataBase.getUserBykey(key, conn);
		JSONArray array = new JSONArray();
		JSONObject json;
		Set<Integer> linkedHashSet = new LinkedHashSet<>();
		if(id==-1) {
			json =new JSONObject();
			json.put("unkown", "Frends");
			array.put(json);
			return array;
		}
		String query = "SELECT * FROM follow WHERE id_user_1='"+id+"' OR id_user_2='"+id+"'";
		Statement Searchfrend=conn.createStatement();
		ResultSet resultat = Searchfrend.executeQuery(query);
		while(resultat.next()) {
			linkedHashSet.add(resultat.getInt(1));
			linkedHashSet.add(resultat.getInt(2));
		}
		linkedHashSet.remove(id);
		Iterator<Integer> iter = linkedHashSet.iterator();
		
		int index;
		System.out.println(linkedHashSet.toString());
		while(iter.hasNext()) {
			index=iter.next();
			json =new JSONObject();
			json.put("name",UserDataBase.getUserLogin(index, conn));
			json.put("id",index);
			array.put(json);
		}
		

		resultat.close();
		Searchfrend.close();
		return array;
	}
		
	//OP
	public static ArrayList<Integer> getAllFrendID(String key, Connection conn) throws SQLException, JSONException {
		int id=SessionDataBase.getUserBykey(key, conn);
		Set<Integer> linkedHashSet = new LinkedHashSet<>();
		if(id==-1) {
			return null;
		}
		String query = "SELECT * FROM follow WHERE id_user_1='"+id+"' OR id_user_2='"+id+"'";
		Statement Searchfrend=conn.createStatement();
		ResultSet resultat = Searchfrend.executeQuery(query);
		while(resultat.next()) {
			linkedHashSet.add(resultat.getInt(1));
			linkedHashSet.add(resultat.getInt(2));
		}
		
		Searchfrend.close();
		resultat.close();
		return new ArrayList<Integer>(linkedHashSet);
	}

	//OP
	public static void removelink(String key, String id_frend, Connection conn) throws SQLException {
		int id =SessionDataBase.getUserBykey(key, conn);
		String query = "DELETE FROM follow WHERE (id_user_1='"+id+"' and id_user_2='"+id_frend+"') OR (id_user_1='"+id_frend+"' and id_user_2='"+id+"')";
		Statement removefre=conn.createStatement();
		removefre.executeUpdate(query);
		removefre.close();
		
	}
	
	
}
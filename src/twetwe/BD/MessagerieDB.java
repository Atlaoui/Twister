package twetwe.BD;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.Date;
import java.util.GregorianCalendar;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import twetwe.tools.DBStatic;

public class MessagerieDB {
	
	//private static MongoClient mongo = (MongoClient) MongoClients.create("mongodb://" + DBStatic.mongo_host + ":" + DBStatic.mongo_port);
	private static MongoClient mongo = new MongoClient("localhost",27017);
	private static MongoDatabase mDB = mongo.getDatabase(DBStatic.mongo_db);
	
	public static void ajouterMessage(int user_id, String user_name, String content) {
		MongoCollection<Document> message_collection = mDB.getCollection("message");
		GregorianCalendar cal = new GregorianCalendar();
		Date now = cal.getTime();
		
		Document query = new Document();
	
		query.append("user_id", user_id);
		query.append("date", now);
		query.append("user_name", user_name);
		query.append("content", content);
		
		message_collection.insertOne(query);

	}
	
	//OP
	public static void supprimerMessage(int user_id, String ObjId) {
		
		MongoCollection<Document> message_collection = mDB.getCollection("message");
		Document query = new Document();
		query.append("_id", new ObjectId(ObjId));
		FindIterable<Document> d = message_collection.find(query);
		
		if(user_id==d.first().getInteger("user_id",-1));
			message_collection.findOneAndDelete(query);
		
	}
	
	
	public static void supprimerAllMessage(String login) {
		MongoCollection<Document> message_collection = mDB.getCollection("message");
		Document query = new Document();
		query.append("user_name", login);
		message_collection.deleteMany(query);
	}
	
	
	public static boolean testIdPoster(int id ,String id_Obj) {
		MongoCollection<Document> message_collection = mDB.getCollection("message");
		Document query = new Document ();
		Boolean retour =false;
		
		query.append("_id",new ObjectId(id_Obj));
		MongoCursor<Document> Document = message_collection.find(query).iterator();
		
		
		if(Document.hasNext() && Document.next().getInteger("user_id")==id)
				retour = true;
			
		return retour;
	}
	
	public static  JSONArray ListMessage(ArrayList<Integer> id ,Connection conn) throws JSONException, SQLException {
		MongoCollection<Document> message_collection = mDB.getCollection("message");
		Document current,Q = new Document();
		JSONArray msg = new JSONArray();
		FindIterable<Document> fi;
		ArrayList<Document> sortedList = new ArrayList<>();
		MongoCursor<Document> cursor = null;
		for(Integer person : id) {	
			Q.append("user_id", person.intValue());
			fi =message_collection.find(Q);
			
			cursor=fi.iterator();
			
			while(cursor.hasNext()) {
				current=cursor.next();
				current.append("likes", LikesDB.getLikesByTweet(current.getObjectId("_id").toString(),conn));
				sortedList.add(current);		
				//JSONObject retourJ = new JSONObject();
				//retourJ.put(""+cpt++,current.values());
			}
		
		}
		//Sort Op
		int cpt=0;
		//pour changer l'ordre juste changer h1 par h2
		sortedList.sort((Document h1, Document h2) -> h2.getDate("date").compareTo(h1.getDate("date")));
		for(Document d : sortedList)
			msg.put(cpt++,d.values());		
				
		if(cursor!=null)
			cursor.close();
		return msg;
	}
}

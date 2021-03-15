package twetwe.BD;


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

//privatemessage

/*l'idée est d'ajouter une nouvelle collection avec aulieu d'un seul id en met deux id celle des 
 * personne concerner*/


public class MessagePriveDB {
	private static MongoClient mongo = new MongoClient("localhost",27017);
	private static MongoDatabase mDB = mongo.getDatabase(DBStatic.mongo_db);
	
	//OP
	public static void ajouterPrivateMessage(int user_id1 ,int user_id2, String user_name_oftheposter, String content) {
		MongoCollection<Document> message_collection = mDB.getCollection("privatemessage");
		GregorianCalendar cal = new GregorianCalendar();
		Date now = cal.getTime();
		
		Document query = new Document();
	
		query.append("user_id1", user_id1);
		query.append("user_id2", user_id2);
		query.append("date", now);
		query.append("user_name", user_name_oftheposter);
		query.append("content", content);
		
		message_collection.insertOne(query);
	}
	
	//OP
	public static void supprimerPrivateMessage(int user_id, String ObjId) {
		
		MongoCollection<Document> message_collection = mDB.getCollection("privatemessage");
		Document query = new Document();
		query.append("_id", new ObjectId(ObjId));
		FindIterable<Document> d = message_collection.find(query);
		
		if(user_id==d.first().getInteger("user_id1"));
			message_collection.findOneAndDelete(query);
		
	}
	
	//OP
	public static  JSONArray ListMessagePrivate(int id1 ,int id2) throws JSONException {
		MongoCollection<Document> message_collection = mDB.getCollection("privatemessage");
		Document current,Q = new Document();
		Document Q2 = new Document();
		JSONArray msg = new JSONArray();
		FindIterable<Document> fi,fi2;
		ArrayList<Document> sortedList = new ArrayList<>();
		MongoCursor<Document> cursor = null;
		
			Q.append("user_id1",id1);
			Q.append("user_id2",id2);
			
			
			Q2.append("user_id2",id1);
			Q2.append("user_id1",id2);
			
			
			fi =message_collection.find(Q);
			
			fi2=message_collection.find(Q2);
			
			System.out.println(fi.first());
			cursor=fi.iterator();
				
			while(cursor.hasNext()) {
				current=cursor.next();
				sortedList.add(current);		
			}
			
			cursor=fi2.iterator();
			
			while(cursor.hasNext()) {
				current=cursor.next();
				if(!sortedList.contains(current))
					sortedList.add(current);		
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
	
	
	//tester si l'ider est celle du poster
	public static boolean testIdPosterPrivate(int id ,String id_Obj) {
		MongoCollection<Document> message_collection = mDB.getCollection("privatemessage");
		Document query = new Document ();
		Boolean retour =false;
		
		query.append("_id",new ObjectId(id_Obj));
		MongoCursor<Document> Document = message_collection.find(query).iterator();
		
		if(Document.hasNext() && Document.next().getInteger("user_id1")==id)
				retour = true;
			
		return retour;
	}
	
	
}

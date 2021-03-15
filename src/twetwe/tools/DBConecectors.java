package twetwe.tools;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import twetwe.BD.Database;


public class DBConecectors {
	private static Database database =null;


	public static Connection getMySQLConnection() throws SQLException {		
		if (DBStatic.mysql_pooling==false) {
			return(DriverManager.getConnection("jdbc:mysql://" + DBStatic.mysql_host + "/" +
					DBStatic.mysql_bd, DBStatic.mysql_username, DBStatic.mysql_password));
		}
		else {
			if (database==null) {
				database=new Database("jdbc/db");
			}
			return(database.getConnection());
		}
	}
	
	public static MongoCollection<Document> getMyMongoCollection() throws UnknownHostException {
		MongoClient m =  MongoClients.create();
		MongoDatabase db = m.getDatabase("Notwetwe");
		MongoCollection<Document> coll = db.getCollection("message");
			System.out.println(coll.getNamespace().toString());
		return coll;
	}
	
}

 
package twetwe.test;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import twetwe.BD.LikesDB;
import twetwe.BD.MessagePriveDB;
import twetwe.BD.MessagerieDB;
import twetwe.BD.SessionDataBase;
import twetwe.BD.UserDataBase;
import twetwe.BD.followDataBase;
import twetwe.service.FrendService;
import twetwe.service.LikeService;
import twetwe.service.MessagerieService;
import twetwe.service.UserService;
import twetwe.service.loginService;
import twetwe.tools.DBConecectors;
import twetwe.tools.ExceptionJason;

public class TesteServices {

	
	@Test
	public void test_Create_delete_User() {
		assertEquals(""+ExceptionJason.serviceAccepted(),""+UserService.newUser("ADA", "@Izanou893214568djfhbverdcvoeppp", "ADA@yahoo.fr", "ADADDA", "neil","1900-06-30","F"));

		assertEquals(""+ExceptionJason.serviceAccepted(),""+UserService.deleteUser("ADA", "@Izanou893214568djfhbverdcvoeppp"));

		System.out.println("Create delete user succï¿½s");
	}


	@Test
	public void test_logingService() {
		JSONObject json =	loginService.login("LoginBlabla", "@Izanou893214568djfhbverdcvoeppp",true);
		try {
			assertEquals(""+ExceptionJason.serviceAccepted(),""+loginService.logOut((String) json.get("key")));
		} catch (JSONException e) {

			System.out.println("loginServrvice rater");
		}

		System.out.println("loginService rï¿½ussi avec succï¿½ws");

	}


	@Test
	public void test_add_delete_follower() {
		try {
			JSONObject json =	loginService.login("LoginBlabla", "@Izanou893214568djfhbverdcvoeppp",true);
			assertEquals(""+ExceptionJason.serviceAccepted(),""+FrendService.addfrend((String)json.get("key"), "9"));
			assertEquals(""+ExceptionJason.serviceAccepted(),""+FrendService.removefrend((String)  json.get("key"),"9"));
			assertEquals(""+ExceptionJason.serviceAccepted(),""+loginService.logOut((String) json.get("key")));
		} catch (JSONException e) {
			e.printStackTrace();
			System.out.println("Pas bon ><");
		}

		System.out.println("Succï¿½s ajouter follower");
	}
	@Test
	public void test_add_remove_message() {
		String key ="b6176259-2c14-433c-87df-8a7e0a11cda7";
		
		assertEquals(""+ExceptionJason.serviceAccepted(),""+MessagerieService.addMessage(key, "oulalal"));
		
		//pour le remove j'utilise l'object id coter utilisateur pour le trouver du coups fo trouver le message
		//et appelert
		
	//assertEquals(""+ExceptionJason.serviceAccepted(),""+MessagerieService.removeMessage(key, "metre en string l'id de l'objet"));
		
		
		System.out.println("succer messagerie");

	}

	

	@Test
	public void test_listMessage() {

		//FrendService.getFrends(key);

		Connection conn = null;
		try {
			conn = DBConecectors.getMySQLConnection();
			ArrayList<Integer> l = followDataBase.getAllFrendID("453106fb-f8c1-4e4f-a486-0e5f918a205c",conn);
			System.out.println(l);
			MessagerieDB.ListMessage(l,conn);


			System.out.println(l.toString());
		} catch (SQLException e) {
			System.out.println("Pas bon ><");
			e.printStackTrace();
		} catch (JSONException e) {
			System.out.println("Pas bon ><");
			e.printStackTrace();
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

	@Test
	public void test_recupData() {
	
		Connection conn=null;
		try {
			conn = DBConecectors.getMySQLConnection();
	
			followDataBase.removelink("38cb99b0-5c1a-4a52-b8ea-57758d0b10f2","10",conn);
			LikesDB.DeleteAllLikes("5c76a0ac679fed21275475a3",conn);
		} catch (SQLException e) {
			System.out.println("Pas bon ><");
			e.printStackTrace();
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


	@Test
	public void Test_Ajout_retire_Amis() {
		String key ="b6176259-2c14-433c-87df-8a7e0a11cda7";

		assertEquals(""+FrendService.addfrend(key,"3"),""+ ExceptionJason.serviceAccepted());
		assertEquals(""+FrendService.addfrend(key,"10"),""+ ExceptionJason.serviceAccepted());
		assertEquals(""+FrendService.addfrend(key,"4"),""+ ExceptionJason.serviceAccepted());

		assertEquals(""+FrendService.removefrend(key,"3"),""+ ExceptionJason.serviceAccepted());
		assertEquals(""+FrendService.removefrend(key,"10"),""+ ExceptionJason.serviceAccepted());
		assertEquals(""+FrendService.removefrend(key,"4"),""+ ExceptionJason.serviceAccepted());

		System.out.println("Ajout et remove d'amis");

	}

	
	@Test
	public void Test_SearchFrends() {
		String key ="b6176259-2c14-433c-87df-8a7e0a11cda7";
		Connection conn=null;
		try {
			conn = DBConecectors.getMySQLConnection();
		
		System.out.println(UserDataBase.Search("ne", conn));;
		
		System.out.println(UserService.searchUser(key, null, "ne"));
		
		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn!=null && !conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}
	
	@Test
	public void TesteLikeService() {
		String objectId="5c768cef74a07a62807a132d";
		String key ="b6176259-2c14-433c-87df-8a7e0a11cda7";
		
		assertEquals(""+LikeService.addLikeService(key, objectId),""+ ExceptionJason.serviceAccepted());
		assertEquals(""+LikeService.removeLike(key, objectId),""+ ExceptionJason.serviceAccepted());
	
		System.out.println("Succés Likes Services");
	}
	
	
	@Test
	public void TesteMyMessages() {
		String key ="b6176259-2c14-433c-87df-8a7e0a11cda7";
		System.out.println(MessagerieService.getMessage(key,null));
		
		System.out.println(MessagerieService.getMessage(key,""+3));
		
		System.out.println("Teste messages");
		
		key="453106fb-f8c1-4e4f-a486-0e5f918a205c";
		
		MessagerieService.getMessage("453106fb-f8c1-4e4f-a486-0e5f918a205c","3");
		
		
		
	}
	
	@Test
	public void TestePrivateMessage() {
		/*MessagePriveDB.ajouterPrivateMessage(1,2,"Blabla","^spofkqspofk");
		MessagePriveDB.ajouterPrivateMessage(1,2,"Blabla","^qsdlqsdqsdqsdsqdqs");
		MessagePriveDB.ajouterPrivateMessage(1,2,"Blabla","^qsdqsdmù");
		MessagePriveDB.ajouterPrivateMessage(1,2,"Blabla","^qsdqsdsq");
		MessagePriveDB.ajouterPrivateMessage(1,2,"Blabla","^spofqslmdqsdkqspofk");
		
		MessagePriveDB.ajouterPrivateMessage(2,1,"Corneille","et moi");
		*/
		
		try {
			System.out.println(MessagePriveDB.ListMessagePrivate(2,1));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		//MessagePriveDB.supprimerPrivateMessage(1, "5cb73628dcce1177d3481284");
	}
	
	
	@Test
	public void TesteSessionTempsDefini() {
		String key ="eb5badab-200b-4615-a974-382a0ec962d1";
		
		Connection conn = null;
		try {
			conn = DBConecectors.getMySQLConnection();
			assertEquals(SessionDataBase.connectionOneHour(key, conn),true);
			
			System.out.println("Teste session refresh réussi");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

package twetwe.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import twetwe.tools.Format;

public class TestRegexp {

	@Test
	public void test_format_nom() {
		//Valide
		assertTrue(Format.checkNomPrenom("Blabla","D3"));
		
		//non Valide
		assertTrue(!Format.checkNomPrenom(null,"blabla"));
		assertTrue(!Format.checkNomPrenom("blabla",null));
		assertTrue(!Format.checkNomPrenom("8585","D3"));
		assertTrue(!Format.checkNomPrenom("     ","dddddd"));
		System.out.println("Succ�s tests Format nom");
	}
	
	
	
	
	@Test
	public void test_format_mail() {
		//email valide
		assertTrue(Format.checkMail("mkyong@yahoo.com"));
		assertTrue(Format.checkMail("mkyong-100@yahoo.com"));
		assertTrue(Format.checkMail("mkyong.100@yahoo.com"));
		assertTrue(Format.checkMail("mkyong111@mkyong.com"));
		assertTrue(Format.checkMail("mkyong-100@mkyong.net"));
		assertTrue(Format.checkMail("mkyong@1.com"));
		assertTrue(Format.checkMail("mkyong+100@gmail.com"));
		assertTrue(Format.checkMail("mkyong-100@yahoo-test.com"));
		assertTrue(Format.checkMail("mkyong@gmail.com"));
		
		
		//email non valide
		assertTrue(!Format.checkMail(null));
		assertTrue(!Format.checkMail("mkyong"));
		assertTrue(!Format.checkMail("mkyong@.com.my"));
		assertTrue(!Format.checkMail("mkyong123@.com"));
		assertTrue(!Format.checkMail("mkyong123@.com.com"));
		assertTrue(!Format.checkMail(".mkyong@mkyong.com"));
		assertTrue(!Format.checkMail("mkyong()*@gmail.com"));
		assertTrue(!Format.checkMail("mkyong@%*.com"));
		assertTrue(!Format.checkMail("mkyong..2002@gmail.com"));
		assertTrue(!Format.checkMail("mkyong@mkyong@gmail.com"));
		assertTrue(!Format.checkMail("mkyong@gmail.com.1a"));	
		assertTrue(!Format.checkMail("mkyong.100@mkyong.com.au"));
		assertTrue(!Format.checkMail("mkyong@gmail.com.com")); 
		
		System.out.println("Succ�s tests Format email");
	}
	
	
	
	
	@Test
	public void test_format_mdp() {
		//Valide
		assertTrue(Format.checkPassword("@Blablag9"));
		assertTrue(Format.checkPassword("@Izanaoug9"));
		assertTrue(Format.checkPassword("@Izanou893214568djfhbverdcvoeppp"));
		
		//non Valide
		assertTrue(!Format.checkPassword(null));
		assertTrue(!Format.checkPassword("Blabla"));
		assertTrue(!Format.checkPassword("@Blabla"));
		assertTrue(!Format.checkPassword("@Izanou893214568djfhbverdcvoeppp5"));
		assertTrue(!Format.checkPassword("@Izandqsdqsazzag9qsd545sssdsdsds454554545qskq"));
		
		
		System.out.println("Succ�s tests Format mdp");
	}
	

}

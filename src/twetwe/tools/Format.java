package twetwe.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Format {
	/**
	 * Définition des regexp pour check de la validiter des information saisie par le client
	 * */
	
	private static final Pattern pattern_nomPrenom =  Pattern.compile("^[a-zA-Z]+[0-9]*$");
	private static final Pattern pattern_mail =  Pattern.compile("(^[a-zA-Z]+[0-9\\-\\+]*|^[a-zA-Z]+[\\.]?[0-9\\-\\+]*|^[a-zA-Z]+[0-9]+)[@][A-Za-z0-9\\-]+.[a-z]+$");
	private static final Pattern pattern_dateNaiss = Pattern.compile("^[0-3]?[0-9].[0-3]?[0-9].(?:[0-9]{2})?[0-9]{2}$"); 
	private static final Pattern pattern_age=Pattern.compile("^[0-9][0-9][0-9]$");
	private static final Pattern pattern_mdp=Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,32}$");
	
	/**
	 * Check le nom et le prenom
	 * */
	
	public static boolean checkNomPrenom(String nom , String prenom) {
		if(nom == null)
			return false;
		if(prenom == null)
			return false;
		Matcher matcher =pattern_nomPrenom.matcher(nom);
		if(!matcher.find()) {
			return false;
		}
		matcher =pattern_nomPrenom.matcher(prenom);
		if(!matcher.find()) {
			return false;
		}
		return true;
	}
	
	/**
	 * check une date de type jj.mm.aaaa
	 * */
	public static boolean checkDate(String date) {
		if(date == null)
			return false;
		return pattern_dateNaiss.matcher(date).find();
	}
	
	/**
	 * check le type de mail 
	 * */
	public static boolean checkMail(String mail) {
		if(mail == null)
			return false;
		return pattern_mail.matcher(mail).find();
	}
	
	/**
	 *check un age de type string avans de le transformet en Integer pour la suite des
	 * traitement 
	 **/
	public static boolean checkAge(String age) {
		if(age==null)
			return false;	
		return pattern_age.matcher(age).find();
	}
	
	/** 
	 *  au moin un caracter spécial (?=.*?[#?!@$%^&*-])
		au moins une minuscule et une majuscule (?=.*?[a-z]) (?=.*?[A-Z])
		au moin un nombre (?=.*?[0-9])
		minimum  8caracter max 32 {8,32}
 		*/
	public static boolean checkPassword(String mdp) {
		if(mdp==null)
			return false;
		return pattern_mdp.matcher(mdp).find();
	}
	
	//Ajouter des traitement suplémentaire
	public static boolean checkKeyFormat(String key) {
		if(key==null || key=="")
			return false;
		return true;
	}
	
	
		
}

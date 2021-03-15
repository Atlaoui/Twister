
package twetwe.tools;

import org.json.JSONException;
import org.json.JSONObject;

public class ExceptionJason {
	public static JSONObject serviceRefused(String message, int codeErreur) {
		JSONObject j = new JSONObject();
		
			try {
				j.put("Message", message);
				j.put("ErrorNum",codeErreur);
				j.put("status", "KO");
			} catch (JSONException e) {
				e.printStackTrace();
				return serviceRefused("JSONException", 100);
			}
			return j;
	}
	
	public static JSONObject serviceAccepted() {

		JSONObject j = new JSONObject();
		try {
			j.put("status", "OK");
		} catch (JSONException e) {
			e.printStackTrace();
			return serviceRefused("JSONException", 100);
		}
		return j;
	}
	
}

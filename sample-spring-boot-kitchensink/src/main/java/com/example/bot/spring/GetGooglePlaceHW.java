package JsonTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;

public class GoogleMap {
	private static final String GOOGLE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
			+ "location=24.95375,121.22575&" + "radius=500&" + "types=food&" + "name=吃到飽&" + "language=zh-TW&"
			+ "key=AIzaSyABpniy5Vpk35tOBKaracShvNOsPWyt-cI";

	public static void main(String[] args) throws IOException {
		URL url = new URL(GOOGLE_URL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setDoInput(true);

		InputStream is = con.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = br.read()) != -1) {
			sb.append((char) cp);
		}
		
		JSONObject jsonObj1 = new JSONObject(sb.toString());
		JSONArray jsonArray = jsonObj1.getJSONArray("results");

		JSONObject jsonObj2 = new JSONObject(jsonArray.get(0).toString());
		JSONObject jsonObj3 = new JSONObject(jsonObj2.get("geometry").toString());
		JSONObject jsonObj4 = new JSONObject(jsonObj3.get("location").toString());
		
		for (int i = 0; i < jsonArray.length(); i++) {
			System.out.println("第"+i+"家:");
			
			jsonObj2 = new JSONObject(jsonArray.get(i).toString());
			System.out.println("name:"+jsonObj2.get("name"));
			System.out.println("rating:"+jsonObj2.get("rating"));
			System.out.println("vicinity:"+jsonObj2.get("vicinity"));
			
			jsonObj3 = new JSONObject(jsonObj2.get("opening_hours").toString());
			System.out.println("open_now:"+jsonObj3.get("open_now"));
			
			jsonObj3 = new JSONObject(jsonObj2.get("geometry").toString());			
			jsonObj4 = new JSONObject(jsonObj3.get("location").toString());
			System.out.println("lng:"+jsonObj4.get("lng"));
			System.out.println("lat:"+jsonObj4.get("lat"));
			
			System.out.println();
		}
		
		br.close();
		isr.close();
		is.close();

		con.disconnect();

	}

}

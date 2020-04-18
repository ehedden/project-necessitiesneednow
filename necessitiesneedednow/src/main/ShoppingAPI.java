import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class ShoppingAPI {

	private static HttpURLConnection connection;
	private static HttpURLConnection connectionItem;
	private static HashMap<String, Integer> searchResults = new HashMap<String, Integer>();

	public static boolean searchItems(String searchEntry){
		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();
		boolean result = false;

		try {
			URL url = new URL("https://api.spoonacular.com/food/ingredients/autocomplete?query=" + searchEntry +  "&metaInformation=true&apiKey=704d3da3d2f84275b3ebcf55e3cece44");
			connection = (HttpURLConnection) url.openConnection();

			// Request Setup
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);;

			int status = connection.getResponseCode();

			if(status > 299) { // Connection status was NOT successful
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			} else { // Connection WAS successful
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}

			result = parseItemList(responseContent.toString());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
		return result;
	}

	public static void getSpecificItem(int id, int amount){
		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();

		try {
			URL url = new URL("https://api.spoonacular.com/food/ingredients/" + id +"/information?amount=1&apiKey=704d3da3d2f84275b3ebcf55e3cece44");
			connectionItem = (HttpURLConnection) url.openConnection();

			// Request Setup
			connectionItem.setRequestMethod("GET");
			connectionItem.setConnectTimeout(5000);
			connectionItem.setReadTimeout(5000);;

			int status = connectionItem.getResponseCode();

			if(status > 299) { // Connection status was NOT successful
				reader = new BufferedReader(new InputStreamReader(connectionItem.getErrorStream()));
				while((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			} else { // Connection WAS successful
				reader = new BufferedReader(new InputStreamReader(connectionItem.getInputStream()));
				while((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}

			System.out.println(responseContent.toString());
//			parseItem(responseContent.toString());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			connectionItem.disconnect();
		}
	}

	public static int getItemId(String name){
		if(searchResults.get(name) != null){
			return searchResults.get(name);
		}
		else{
			return 0;
		}
	}

	public static boolean parseItemList(String responseBody) {
		JSONArray albums = new JSONArray(responseBody);

		if(albums.length() <= 0) return false;

		for(int i = 0; i < albums.length(); i++) {
			JSONObject album = albums.getJSONObject(i);
			String name = album.getString("name");
			int id = album.getInt("id");
			searchResults.put(name, id);
			System.out.println("- " + name);
		}
		return true;
	// list our ingredients --> have them choose from given list or do a different search -->
	// once chosen get price of item
	}

	public static String parseItem(String responseBody) {
		JSONObject obj = new JSONObject(responseBody);
		int price = obj.getInt("estimatedCost");
		System.out.println("PRICE: " + price);
		return null;
	}
}
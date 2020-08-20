import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class JsonSimpleTest {

	public static void printJsonObject(Object obj) {
		JSONObject jsonObj = (JSONObject)obj; 
		jsonObj.forEach((key, value) -> System.out.println(key + " " + value));
	}

	public static void printJsonArray(Object arr) {
		JSONArray jsonArr = (JSONArray) arr;
		for (int i = 0; i < jsonArr.size(); i++) {
			printJsonObject((JSONObject) jsonArr.get(i));
			System.out.println();
		}
	}

	public static void main(String[] args) throws IOException, ParseException {
		Document doc = Jsoup.connect("https://www.strava.com/athletes/51315032").get();
//		Document doc = Jsoup.connect("https://www.strava.com/athletes/7013156").get();
		Elements contents = doc.select("div");
		String json = contents.attr("data-react-props");
//		System.out.println(json);
//		System.out.println();

		JSONParser parser = new JSONParser();

		JSONObject obj = null;
		obj = (JSONObject) parser.parse(json);
//		System.out.println(obj);

//		obj.forEach((key, value) -> System.out.println(key + " " + value));
//		printJsonObject(obj);
		/*
		 * athlete - 스트라바 선수 정보 
		 * stats - 선수 스텟 
		 * recentActivities - 최근 활동 내역
		 */

//		System.out.println(obj.get("athlete"));
//		((JSONObject) obj.get("athlete")).forEach((key, value) -> System.out.println(key + " " + value));
//		printJsonObject(obj.get("athlete"));
		
		/*
		 * avatarUrl 
		 * name 
		 * primarySport 
		 * location 
		 * followersCount 
		 * followingCount
		 */
		
//		String str = ((JSONObject)obj.get("athlete")).get("followingCount").toString();
//		System.out.println(str);

//		System.out.println(obj.get("stats"));
//		printJsonObject(obj.get("stats"));
		/*
		 * chartData - JSONArray, 달별 기록
		 * monthlyTime
		 * monthlyDistance
		 */
		
//		printJsonArray((JSONArray)((JSONObject)obj.get("stats")).get("chartData"));
		/*
		 * hours
		 * month
		 * elev_gain
		 * miles
		 */

//		System.out.println(obj.get("recentActivities")); // JSONArray
		printJsonArray(obj.get("recentActivities"));
		/*
		 * elevation
		 * images - JSONArray 
		 * distance
		 * hasGps
		 * startDateLocal
		 * isManual
		 * name
		 * detailedType
		 * movingTime
		 * id
		 * type 
		 */
		
//		printJsonArray(((JSONObject)((JSONArray)obj.get("recentActivities")).get(0)).get("images"));
		/*
		 * defaultSrc 
		 * squareSrc
		 * caption
		 * type
		 * primary
		 */
		
		JSONArray arr = null;
		arr = (JSONArray) obj.get("recentActivities");

		for (int i = 0; i < arr.size(); i++) {
			obj = (JSONObject) arr.get(i);
//			System.out.println(obj);
		}

//		arr = (JSONArray)parser.parse(json);
//		System.out.println(arr);

	}
}

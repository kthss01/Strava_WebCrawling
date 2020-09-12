import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class JsoupTest {

	public static void main(String[] args) {
		try {
			// 웹에서 내용을 가져온다.
//			Document doc = Jsoup.connect("https://jobc.tistory.com/").get();
			Document doc = Jsoup.connect("https://www.strava.com/athletes/51315032").get();
//			System.out.println(doc);
			
			// 내용 중에서 원하는 부분을 가져온다.
//			Elements contents = doc.select("h1");
			Elements contents = doc.select("div");
//			System.out.println(contents);
			System.out.println(contents.attr("data-react-props"));
			
			String str = contents.attr("data-react-props");			
			
			// 원하는 부분은 Elements 형태로 되어 있으므로 이를 String 형태로 바꾸어 준다.
			String text = contents.text();
			System.out.println(text);

		} catch (IOException e) {
			// Jsoup의 connect 부분에서 IOException 오류가 날 수 있으므로 사용
			e.printStackTrace();
		}
	}

}

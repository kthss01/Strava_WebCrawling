import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.json.simple.parser.ParseException;

import webCrawler.WebCrawler;

public class MultiThreadTest {
	public static void main(String[] args) {
		List<WebCrawler> webCrawlers = new ArrayList<>();

		int cnt = 10;

		for (int j = 0; j < 100; j++) {

			Thread[] threads = new Thread[cnt];
			CountDownLatch latch = new CountDownLatch(cnt);

			for (int i = 0; i < cnt; i++) {
				WebCrawlerThread task = new WebCrawlerThread(i + 51315032, webCrawlers, latch);
				threads[i] = new Thread(task);
				threads[i].start();
			}

			try {
				latch.await();
				System.out.println(webCrawlers.size());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

//		for (int i = 0; i < cnt; i++) {
//			try {
//				threads[i].join();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

	}
}

class WebCrawlerThread implements Runnable {
	List<WebCrawler> webCrawlers;
	CountDownLatch latch;

	List<WebCrawler> list;
	int user;
	String url;

	WebCrawlerThread(int user, List<WebCrawler> webCrawlers, CountDownLatch latch) {
		this.webCrawlers = webCrawlers;
		this.latch = latch;
		list = new ArrayList<>();
		url = "https://www.strava.com/athletes/";
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			for (int i = 0; i < 10; i++) {
				list.add(new WebCrawler(url + String.valueOf(user + i)));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		add();

		latch.countDown();

//		System.out.println("crawling done");
	}

	public void add() {
		webCrawlers.addAll(list);
	}
}

public class SingleThreadEx extends Thread {
	private int[] temp;

	public SingleThreadEx(String threadName) {
		super(threadName);
		temp = new int[10];

		for (int start = 0; start < temp.length; start++) {
			temp[start] = start;
		}
	}

	public void run() {
		for (int start : temp) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			System.out.println("스레드 이름 : " + currentThread().getName());
			System.out.println("temp value : " + start);

		}
	}
	
	public static void main(String[] args) {
		SingleThreadEx st = new SingleThreadEx("첫번째");
		st.start();
	}
}

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//public class AddPenny implements Runnable {
//	private static PiggyBank piggy = new PiggyBank();
//	
//	public void run() {
//		piggy.deposit(1);
//	}
//	
//	public static void main(String [] args) {
//		for(int i = 0; i < 100; i ++) {
//			Thread t = new Thread(new AddPenny());
//			t.start();
//		}
//		System.out.println("Balance = " + piggy.getBalance());
//	}
//}

public class AddPenny implements Runnable {
	private static PiggyBank piggy = new PiggyBank();
	
	public void run() {
		piggy.deposit(1);
	}
	
	public static void main(String [] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		for(int i = 0; i < 100; i ++) {
			executor.execute(new AddPenny());
		}
		executor.shutdown();
		System.out.println("Balance = " + piggy.getBalance());
	}
}

class PiggyBank {
	private int balance = 0;
	public int getBalance() {
		return balance;
	}
	public void deposit(int amount) {
		int newBalance = balance + amount;
		Thread.yield();
		balance = newBalance;
	}
}
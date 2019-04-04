import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AddPenny implements Runnable {
	private static PiggyBank piggy = new PiggyBank();
	boolean is_withdraw;
	
	public AddPenny(boolean p) {
		this.is_withdraw = p;
	}
	
	public void run() {
		if(this.is_withdraw)
			piggy.withdraw(1);
		else
			piggy.deposit(1);
	}
	
	public static void main(String [] args) {
		for(int i = 0; i < 50; i ++) {
			Thread t = new Thread(new AddPenny(true));
			t.start();
		}
		for(int i = 0; i < 50; i ++) {
			Thread t = new Thread(new AddPenny(false));
			t.start();
		}
		System.out.println("Balance = " + piggy.getBalance());
	}
}

//public class AddPenny implements Runnable {
//	private static PiggyBank piggy = new PiggyBank();
//	
//	public void run() {
//		piggy.deposit(1);
//	}
//	
//	public static void main(String [] args) {
//		ExecutorService executor = Executors.newCachedThreadPool();
//		for(int i = 0; i < 100; i ++) {
//			executor.execute(new AddPenny());
//		}
//		executor.shutdown();
//		System.out.println("Balance = " + piggy.getBalance());
//	}
//}




class PiggyBank {
	private int balance = 0;
	private Lock lock = new ReentrantLock();
	Condition depositMade = lock.newCondition();
	public int getBalance() {
		return balance;
	}
	public void withdraw(int amount) {
		lock.lock();
		try {
			while(balance < amount) {
				System.out.println("Waiting for a deposit");
				depositMade.await();
			}
			balance -= amount;
			System.out.println("Withdrew " + amount + " leaving balance=" + balance);
		} catch (InterruptedException ie) {
			System.out.println("id: " + ie.getMessage());
		} finally {
			lock.unlock();
		}
	}
	public /*synchronized*/ void deposit(int amount) {
		lock.lock();
		try {
			int newBalance = balance + amount;
			balance = newBalance;
			System.out.println("deposit made leaving balance=" + balance);
			depositMade.signalAll();
		} finally {
			lock.unlock();
		}
	}
}
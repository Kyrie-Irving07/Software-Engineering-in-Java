import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SleepingBarber extends Thread {
	private String name;
	public boolean sleeping;
	static private int maxSeats;
	static private int totalCustomers;
	static private ArrayList<Customer> customersWaiting;
	private Lock barberLock;
	private Condition sleepingCondition;
	static private boolean moreCustomers;
	public SleepingBarber(String name) {
		this.name = name;
		this.sleeping = true;
		maxSeats = 3;
		totalCustomers = 10;
		moreCustomers = true;
		customersWaiting = new ArrayList<Customer>();
		barberLock = new ReentrantLock();
		sleepingCondition = barberLock.newCondition();
		this.start();
	}
	static public synchronized boolean addCustomerToWaiting(Customer customer) {
		if (customersWaiting.size() == maxSeats) {
			return false;
		}
		Util.printMessage("Customer " + customer.getCustomerName() + " is waiting");
		customersWaiting.add(customer);
		String customersString = "";
		for (int i=0; i < customersWaiting.size(); i++) {
			customersString += customersWaiting.get(i).getCustomerName();
			if (i < customersWaiting.size() - 1) {
				customersString += ",";
			}
		}
		Util.printMessage("Customers currently waiting: " + customersString);
		return true;
	}
	public void wakeUpBarber() {
		try {
			barberLock.lock();
			sleepingCondition.signal();
			this.sleeping = false;
		} finally {
			barberLock.unlock();
		}
	}
	public void run() {
		while(moreCustomers) {
			while(!customersWaiting.isEmpty()) {
				Customer customer = null;
				synchronized(this) {
					customer = customersWaiting.remove(0);
				}
				customer.startingHaircut(this.name);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ie) {
					System.out.println(this.name + "ie cutting customer's hair" + ie.getMessage());
				}
				customer.finishingHaircut();
				Util.printMessage(this.name + "Checking for more customers...");		
			}
			try {
				barberLock.lock();
				Util.printMessage(this.name + "No customers, so time to sleep...");
				this.sleeping = true;
				sleepingCondition.await();
				Util.printMessage(this.name + "Someone woke me up!");
			} catch (InterruptedException ie) {
				System.out.println(this.name + "ie while sleeping: " + ie.getMessage());
			} finally {
				barberLock.unlock();
			}
		}
		Util.printMessage(this.name + "All done for today!  Time to go home!");
		
	}
	public static void main(String [] args) {
		SleepingBarber sb1 = new SleepingBarber("Barber #1 ");
		SleepingBarber sb2 = new SleepingBarber("Barber #2 ");
		ExecutorService executors = Executors.newCachedThreadPool();
		for (int i=0; i < SleepingBarber.totalCustomers; i++) {
			Customer customer = new Customer(i, sb1, sb2);
			executors.execute(customer);
			try {
				Random rand = new Random();
				int timeBetweenCustomers = rand.nextInt(2000);
				Thread.sleep(timeBetweenCustomers);
			} catch (InterruptedException ie) {
				System.out.println("ie in customers entering: " + ie.getMessage());
			}
		}
		executors.shutdown();
		while(!executors.isTerminated()) {
			Thread.yield();
		}
		Util.printMessage("No more customers coming today...");
		SleepingBarber.moreCustomers = false;
		sb1.wakeUpBarber();
		sb2.wakeUpBarber();
	}
}
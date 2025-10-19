package sprint_0;

public class BankAccount {
	private String owner;
	private double balance;
	
	public BankAccount(String owner, double initialBalance) {
		this.owner = owner;
		this.balance = Math.max(0, initialBalance);
	}
	
	public String getOwner() {
		return owner;
	}
	
	public double getBalance() {
		return balance;
	}
	
	// checks if deposit amount is valid 
	public boolean deposit(double amount) {
		if (amount > 0) {
			balance += amount;
			return true;
		}
		return false; 
	}
	
	// checks if withdraw is valid
	public boolean withdraw(double amount) {
		if (amount > 0 && amount <= balance) {
			balance -= amount;
			return true;
		}
		return false;
	}
}

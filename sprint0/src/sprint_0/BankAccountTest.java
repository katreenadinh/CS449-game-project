package sprint_0;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankAccountTest {

	private BankAccount account;
	
	@BeforeEach
	public void setUp() {
		account = new BankAccount("Jason", 3000.0);
	}
	
	@Test
	public void testDeposit() {
		assertTrue(account.deposit(20.0));
		assertEquals(3020.0, account.getBalance(), 0.001);
		
	}
	
	@Test
	public void testDepositFailsForNegativeAmount() {
		assertFalse(account.deposit(-50.0));
		assertEquals(3000.0, account.getBalance(), 0.001);
	}
	
	@Test
    public void testWithdraw() {
        assertTrue(account.withdraw(50.0));
        assertEquals(2950.0, account.getBalance(), 0.001);
    }

    @Test
    public void testWithdrawFailsForTooMuch() {
        assertFalse(account.withdraw(4000.0));
        assertEquals(3000.0, account.getBalance(), 0.001); // unchanged
    }
	

}

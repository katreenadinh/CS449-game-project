package sprint4.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.After;
import org.junit.jupiter.api.*;
import sprint2.product.*;

class SetGameModeTest {

	 private GameModel model;

	    @BeforeEach
	    public void setUp() {
	        model = new GameModel(3); // default size
	    }
	    
	    @After
		public void tearDown() throws Exception {
		}
	    
	    // acceptance criterion 2.2
	    @Test
	    public void testDefaultGameMode() {
	        assertEquals(GameMode.SIMPLE, model.getGameMode(), "Default game mode should be SIMPLE");
	    }
	    
	    // acceptance criterion 2.1
	    @Test
	    public void testSetGameMode() {
	        model.setGameMode(GameMode.GENERAL);
	        assertEquals(GameMode.GENERAL, model.getGameMode(), "Game mode should be GENERAL after setting it");
	    }

}

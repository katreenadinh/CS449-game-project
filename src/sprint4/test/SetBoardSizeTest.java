package sprint4.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.After;
import org.junit.jupiter.api.*;
import sprint2.product.GameModel;

class SetBoardSizeTest {

    private GameModel model;

    @BeforeEach
    public void setUp() {
        model = new GameModel(3); // default 3x3 board
    }
    
    @After
	public void tearDown() throws Exception {
	}
    
    // acceptance criterion 1.1
    @Test
    @DisplayName("Set a valid board size (>=3) should succeed")
    public void testSetBoardSize_Valid() {
        boolean result = model.setBoardSize(5); // valid size
        assertTrue(result, "Board size 5 should be accepted");
        assertEquals(5, model.getSize(), "Board size should update to 5");
        assertEquals(5, model.getBoard().length, "Board rows should match new size");
    }
    
    // acceptance criterion 1.2
    @Test
    @DisplayName("Set an invalid board size (<3) should fail")
    public void testSetBoardSize_Invalid() {
        boolean result = model.setBoardSize(2); // invalid size
        assertFalse(result, "Board size 2 should be rejected");
        assertEquals(3, model.getSize(), "Board size should remain unchanged");
        assertEquals(3, model.getBoard().length, "Board rows should remain unchanged");
    }
}

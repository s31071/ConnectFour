import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectFourTest {
    private ConnectFour game;


    @BeforeAll
    static void setUp() {
        System.loadLibrary("ConnectFour");

    }

    @BeforeEach
    void initialize() {
        game = new ConnectFour();
    }

    //Board Initialization
    @Test
    public void testInitialization() {

        assertEquals(4, ConnectFour.ROWS, "The board should have 4 rows.");
        assertEquals(5, ConnectFour.COLS, "The board should have 5 columns.");

        assertEquals(new Point(125, ConnectFour.ROWS * ConnectFour.CELL_SIZE + 20), game.circle1Position,
                "Circle 1 position should be set to its initial parking position.");
        assertEquals(new Point(225, ConnectFour.ROWS * ConnectFour.CELL_SIZE + 20), game.circle2Position,
                "Circle 2 position should be set to its initial parking position.");

        assertNotNull(game.turnLabel, "Turn label should be initialized.");
        assertEquals("Turn: Red", game.turnLabel.getText(), "The initial turn should be set to Red.");

    }

    ///Valid move Test
    @Test
    public void testMovePlacement() {
        game.setBoardCell(ConnectFour.ROWS - 1, 2, 1);
        int[][] board = game.getBoardAs2D();
        assertEquals(1, board[ConnectFour.ROWS - 1][2], "The piece should be in the lowest row of column 2.");

        game.setBoardCell(ConnectFour.ROWS - 2, 2, 2);
        board = game.getBoardAs2D();
        assertEquals(2, board[ConnectFour.ROWS - 2][2], "The piece should be in the row above the last piece in column 2.");

        for (int row = ConnectFour.ROWS - 3; row >= 0; row--) {
            game.setBoardCell(row, 2, 1);
        }
        board = game.getBoardAs2D();
        assertEquals(1, board[0][2], "The top row should now contain a piece as the column is full.");
    }

    @Test
    public void testInvalidMove() {
        for (int row = ConnectFour.ROWS - 1; row >= 0; row--) {
            game.setBoardCell(row, 1, 1);
        }

        int[][] board = game.getBoardAs2D();
        for (int row = 0; row < ConnectFour.ROWS; row++) {
            assertEquals(1, board[row][1], "All rows in the column should be filled with RED pieces.");
        }

    }

    ///Victory Condition Tests:
    @Test
    public void testRedHorizontalWinRow0_0To3() {
        game.setBoardCell(0, 0, 1);
        game.setBoardCell(0, 1, 1);
        game.setBoardCell(0, 2, 1);
        game.setBoardCell(0, 3, 1);
        assertTrue(game.checkWin(1), "Red should win horizontally in row 0 (columns 0-3)");
    }

    @Test
    public void testRedHorizontalWinRow0_1To4() {
        game.setBoardCell(0, 1, 1);
        game.setBoardCell(0, 2, 1);
        game.setBoardCell(0, 3, 1);
        game.setBoardCell(0, 4, 1);
        assertTrue(game.checkWin(1), "Red should win horizontally in row 0 (columns 1-4)");
    }

    @Test
    public void testRedHorizontalWinRow1_0To3() {
        game.setBoardCell(1, 0, 1);
        game.setBoardCell(1, 1, 1);
        game.setBoardCell(1, 2, 1);
        game.setBoardCell(1, 3, 1);
        assertTrue(game.checkWin(1), "Red should win horizontally in row 1 (columns 0-3)");
    }

    @Test
    public void testRedHorizontalWinRow1_1To4() {
        game.setBoardCell(1, 1, 1);
        game.setBoardCell(1, 2, 1);
        game.setBoardCell(1, 3, 1);
        game.setBoardCell(1, 4, 1);
        assertTrue(game.checkWin(1), "Red should win horizontally in row 1 (columns 1-4)");
    }

    @Test
    public void testRedHorizontalWinRow2_0To3() {
        game.setBoardCell(2, 0, 1);
        game.setBoardCell(2, 1, 1);
        game.setBoardCell(2, 2, 1);
        game.setBoardCell(2, 3, 1);
        assertTrue(game.checkWin(1), "Red should win horizontally in row 2 (columns 0-3)");
    }

    @Test
    public void testRedHorizontalWinRow2_1To4() {
        game.setBoardCell(2, 1, 1);
        game.setBoardCell(2, 2, 1);
        game.setBoardCell(2, 3, 1);
        game.setBoardCell(2, 4, 1);
        assertTrue(game.checkWin(1), "Red should win horizontally in row 2 (columns 1-4)");
    }

    @Test
    public void testRedHorizontalWinRow3_0To3() {
        game.setBoardCell(3, 0, 1);
        game.setBoardCell(3, 1, 1);
        game.setBoardCell(3, 2, 1);
        game.setBoardCell(3, 3, 1);
        assertTrue(game.checkWin(1), "Red should win horizontally in row 3 (columns 0-3)");
    }

    @Test
    public void testRedHorizontalWinRow3_1To4() {
        game.setBoardCell(3, 1, 1);
        game.setBoardCell(3, 2, 1);
        game.setBoardCell(3, 3, 1);
        game.setBoardCell(3, 4, 1);
        assertTrue(game.checkWin(1), "Red should win horizontally in row 3 (columns 1-4)");
    }

    @Test
    public void testBlueHorizontalWinRow0_0To3() {
        game.setBoardCell(0, 0, 2);
        game.setBoardCell(0, 1, 2);
        game.setBoardCell(0, 2, 2);
        game.setBoardCell(0, 3, 2);
        assertTrue(game.checkWin(2), "Blue should win horizontally in row 0 (columns 0-3)");
    }

    @Test
    public void testBlueHorizontalWinRow0_1To4() {
        game.setBoardCell(0, 1, 2);
        game.setBoardCell(0, 2, 2);
        game.setBoardCell(0, 3, 2);
        game.setBoardCell(0, 4, 2);
        assertTrue(game.checkWin(2), "Blue should win horizontally in row 0 (columns 1-4)");
    }

    @Test
    public void testBlueHorizontalWinRow1_0To3() {
        game.setBoardCell(1, 0, 2);
        game.setBoardCell(1, 1, 2);
        game.setBoardCell(1, 2, 2);
        game.setBoardCell(1, 3, 2);
        assertTrue(game.checkWin(2), "Blue should win horizontally in row 1 (columns 0-3)");
    }

    @Test
    public void testBlueHorizontalWinRow1_1To4() {
        game.setBoardCell(1, 1, 2);
        game.setBoardCell(1, 2, 2);
        game.setBoardCell(1, 3, 2);
        game.setBoardCell(1, 4, 2);
        assertTrue(game.checkWin(2), "Blue should win horizontally in row 1 (columns 1-4)");
    }

    @Test
    public void testBlueHorizontalWinRow2_0To3() {
        game.setBoardCell(2, 0, 2);
        game.setBoardCell(2, 1, 2);
        game.setBoardCell(2, 2, 2);
        game.setBoardCell(2, 3, 2);
        assertTrue(game.checkWin(2), "Blue should win horizontally in row 2 (columns 0-3)");
    }

    @Test
    public void testBlueHorizontalWinRow2_1To4() {
        game.setBoardCell(2, 1, 2);
        game.setBoardCell(2, 2, 2);
        game.setBoardCell(2, 3, 2);
        game.setBoardCell(2, 4, 2);
        assertTrue(game.checkWin(2), "Blue should win horizontally in row 2 (columns 1-4)");
    }

    @Test
    public void testBlueHorizontalWinRow3_0To3() {
        game.setBoardCell(3, 0, 2);
        game.setBoardCell(3, 1, 2);
        game.setBoardCell(3, 2, 2);
        game.setBoardCell(3, 3, 2);
        assertTrue(game.checkWin(2), "Blue should win horizontally in row 3 (columns 0-3)");
    }

    @Test
    public void testBlueHorizontalWinRow3_1To4() {
        game.setBoardCell(3, 1, 2);
        game.setBoardCell(3, 2, 2);
        game.setBoardCell(3, 3, 2);
        game.setBoardCell(3, 4, 2);
        assertTrue(game.checkWin(2), "Blue should win horizontally in row 3 (columns 1-4)");
    }

    @Test
    public void testRedVerticalWinColumn0() {
        game.setBoardCell(0, 0, 1);
        game.setBoardCell(1, 0, 1);
        game.setBoardCell(2, 0, 1);
        game.setBoardCell(3, 0, 1);
        assertTrue(game.checkWin(1), "Red should win vertically in column 0");
    }

    @Test
    public void testRedVerticalWinColumn1() {
        game.setBoardCell(0, 1, 1);
        game.setBoardCell(1, 1, 1);
        game.setBoardCell(2, 1, 1);
        game.setBoardCell(3, 1, 1);
        assertTrue(game.checkWin(1), "Red should win vertically in column 1");
    }

    @Test
    public void testRedVerticalWinColumn2() {
        game.setBoardCell(0, 2, 1);
        game.setBoardCell(1, 2, 1);
        game.setBoardCell(2, 2, 1);
        game.setBoardCell(3, 2, 1);
        assertTrue(game.checkWin(1), "Red should win vertically in column 2");
    }

    @Test
    public void testRedVerticalWinColumn3() {
        game.setBoardCell(0, 3, 1);
        game.setBoardCell(1, 3, 1);
        game.setBoardCell(2, 3, 1);
        game.setBoardCell(3, 3, 1);
        assertTrue(game.checkWin(1), "Red should win vertically in column 3");
    }

    @Test
    public void testRedVerticalWinColumn4() {
        game.setBoardCell(0, 4, 1);
        game.setBoardCell(1, 4, 1);
        game.setBoardCell(2, 4, 1);
        game.setBoardCell(3, 4, 1);
        assertTrue(game.checkWin(1), "Red should win vertically in column 4");
    }

    @Test
    public void testBlueVerticalWinColumn0() {
        game.setBoardCell(0, 0, 2);
        game.setBoardCell(1, 0, 2);
        game.setBoardCell(2, 0, 2);
        game.setBoardCell(3, 0, 2);
        assertTrue(game.checkWin(2), "Blue should win vertically in column 0");
    }

    @Test
    public void testBlueVerticalWinColumn1() {
        game.setBoardCell(0, 1, 2);
        game.setBoardCell(1, 1, 2);
        game.setBoardCell(2, 1, 2);
        game.setBoardCell(3, 1, 2);
        assertTrue(game.checkWin(2), "Blue should win vertically in column 1");
    }

    @Test
    public void testBlueVerticalWinColumn2() {
        game.setBoardCell(0, 2, 2);
        game.setBoardCell(1, 2, 2);
        game.setBoardCell(2, 2, 2);
        game.setBoardCell(3, 2, 2);
        assertTrue(game.checkWin(2), "Blue should win vertically in column 2");
    }

    @Test
    public void testBlueVerticalWinColumn3() {
        game.setBoardCell(0, 3, 2);
        game.setBoardCell(1, 3, 2);
        game.setBoardCell(2, 3, 2);
        game.setBoardCell(3, 3, 2);
        assertTrue(game.checkWin(2), "Blue should win vertically in column 3");
    }

    @Test
    public void testBlueVerticalWinColumn4() {
        game.setBoardCell(0, 4, 2);
        game.setBoardCell(1, 4, 2);
        game.setBoardCell(2, 4, 2);
        game.setBoardCell(3, 4, 2);
        assertTrue(game.checkWin(2), "Blue should win vertically in column 4");
    }

    @Test
    public void testRedDiagonalBLTRWinFirstConfiguration() {
        game.setBoardCell(3, 0, 1);
        game.setBoardCell(2, 1, 1);
        game.setBoardCell(1, 2, 1);
        game.setBoardCell(0, 3, 1);
        assertTrue(game.checkWin(1), "Red should win diagonally (BL to TR) in the first configuration");
    }

    @Test
    public void testRedDiagonalBLTRWinSecondConfiguration() {
        game.setBoardCell(3, 1, 1);
        game.setBoardCell(2, 2, 1);
        game.setBoardCell(1, 3, 1);
        game.setBoardCell(0, 4, 1);
        assertTrue(game.checkWin(1), "Red should win diagonally (BL to TR) in the second configuration");
    }

    @Test
    public void testBlueDiagonalBLTRWinFirstConfiguration() {
        game.setBoardCell(3, 0, 2);
        game.setBoardCell(2, 1, 2);
        game.setBoardCell(1, 2, 2);
        game.setBoardCell(0, 3, 2);
        assertTrue(game.checkWin(2), "Blue should win diagonally (BL to TR) in the first configuration");
    }

    @Test
    public void testBlueDiagonalBLTRWinSecondConfiguration() {
        game.setBoardCell(3, 1, 2);
        game.setBoardCell(2, 2, 2);
        game.setBoardCell(1, 3, 2);
        game.setBoardCell(0, 4, 2);
        assertTrue(game.checkWin(2), "Blue should win diagonally (BL to TR) in the second configuration");
    }

    @Test
    public void testRedDiagonalTLBRWinFirstConfiguration() {
        game.setBoardCell(0, 0, 1);
        game.setBoardCell(1, 1, 1);
        game.setBoardCell(2, 2, 1);
        game.setBoardCell(3, 3, 1);
        assertTrue(game.checkWin(1), "Red should win diagonally (TL to BR) in the first configuration");
    }

    @Test
    public void testRedDiagonalTLBRWinSecondConfiguration() {
        game.setBoardCell(0, 1, 1);
        game.setBoardCell(1, 2, 1);
        game.setBoardCell(2, 3, 1);
        game.setBoardCell(3, 4, 1);
        assertTrue(game.checkWin(1), "Red should win diagonally (TL to BR) in the second configuration");
    }

    @Test
    public void testBlueDiagonalTLBRWinFirstConfiguration() {
        game.setBoardCell(0, 0, 2);
        game.setBoardCell(1, 1, 2);
        game.setBoardCell(2, 2, 2);
        game.setBoardCell(3, 3, 2);
        assertTrue(game.checkWin(2), "Blue should win diagonally (TL to BR) in the first configuration");
    }

    @Test
    public void testBlueDiagonalTLBRWinSecondConfiguration() {
        game.setBoardCell(0, 1, 2);
        game.setBoardCell(1, 2, 2);
        game.setBoardCell(2, 3, 2);
        game.setBoardCell(3, 4, 2);
        assertTrue(game.checkWin(2), "Blue should win diagonally (TL to BR) in the second configuration");
    }
    ///Edge Case Tests:
    @Test
    public void testMovingOutsideBoard() {
        int[][] initialBoardState = game.getBoardAs2D();

        game.setBoardCell(-1, 2, 1);
        assertArrayEquals(initialBoardState, game.getBoardAs2D(),
                "The board should not change when placing a piece in a negative row.");

        game.setBoardCell(ConnectFour.ROWS, 2, 1);
        assertArrayEquals(initialBoardState, game.getBoardAs2D(),
                "The board should not change when placing a piece in a row greater than ROWS-1.");

        game.setBoardCell(2, -1, 1);
        assertArrayEquals(initialBoardState, game.getBoardAs2D(),
                "The board should not change when placing a piece in a negative column.");

        game.setBoardCell(2, ConnectFour.COLS, 1);
        assertArrayEquals(initialBoardState, game.getBoardAs2D(),
                "The board should not change when placing a piece in a column greater than COLUMNS-1.");

    }

    @Test
    public void testTurns() {

        assertTrue(game.isRedTurn(), "Initial turn should be Red.");

        game.setBoardCell(ConnectFour.ROWS - 1, 2, 1);
        game.changeTurn();
        assertFalse(game.isRedTurn(), "After Red's move, it should be Blue's turn.");

        game.setBoardCell(ConnectFour.ROWS - 1, 3, 2);
        game.changeTurn();
        assertTrue(game.isRedTurn(), "After Blue's move, it should be Red's turn.");

        for (int row = 0; row < ConnectFour.ROWS; row++) {
            game.setBoardCell(row, 4, 1);
        }

        game.setBoardCell(0, 4, 1);
        assertTrue(game.isRedTurn(), "After an invalid move, it should still be Red's turn.");

        game.setBoardCell(ConnectFour.ROWS - 2, 2, 1);
        game.changeTurn();
        assertFalse(game.isRedTurn(), "After a valid move by Red, it should be Blue's turn.");

    }
}

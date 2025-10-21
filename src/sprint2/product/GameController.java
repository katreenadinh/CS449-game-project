package sprint2.product;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class GameController {

    private GameModel model;
    private GameView view;
    private GameView.Square[][] squares;

    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
        this.squares = view.getSquares();

        setupEventHandlers();
    }

    private void setupEventHandlers() {
        modeSelectorListeners();
        boardSizeSelectorListeners();
        squareListeners();
    }

    private void modeSelectorListeners() {
        ToggleGroup modeGroup = view.getGameModeGroup();
        if (modeGroup != null) {
            modeGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
                if (newToggle != null) {
                    RadioButton selected = (RadioButton) newToggle;
                    if (selected.getText().equalsIgnoreCase("Simple")) {
                        model.setGameMode(GameMode.SIMPLE);
                        System.out.println("Game mode set to SIMPLE");
                    } else {
                        model.setGameMode(GameMode.GENERAL);
                        System.out.println("Game mode set to GENERAL");
                    }
                }
            });
        }
    }

    private void boardSizeSelectorListeners() {
        ComboBox<Integer> sizeBox = view.getBoardSizeBox();
        if (sizeBox != null) {
            sizeBox.setOnAction(e -> {
                int newSize = sizeBox.getValue();
                model.setBoardSize(newSize);
                System.out.println("Board resized to " + newSize + "x" + newSize);
                rebuildBoard(newSize);
                view.setCurrentTurnLabel("Current Turn: Blue Player");
            });
        }
    }

    private void squareListeners() {
        if (squares == null) return;

        for (int r = 0; r < squares.length; r++) {
            for (int c = 0; c < squares[r].length; c++) {
                int row = r;
                int col = c;
                squares[r][c].setOnMouseClicked(e -> handleSquareClick(row, col));
            }
        }
    }

    public void rebuildBoard(int newSize) {
        GridPane newBoard = new GridPane();
        newBoard.setAlignment(Pos.CENTER);
        squares = new GameView.Square[newSize][newSize];

        for (int r = 0; r < newSize; r++) {
            for (int c = 0; c < newSize; c++) {
                squares[r][c] = view.new Square(r, c);
                int row = r, col = c;
                squares[r][c].setOnMouseClicked(e -> handleSquareClick(row, col));
                newBoard.add(squares[r][c], c, r);
            }
        }

        BorderPane mainPanel = view.getMainPanel();
        mainPanel.setCenter(newBoard);

        view.setBoardPane(newBoard);
        view.setSquares(squares);

        System.out.println("Board rebuilt to " + newSize + "x" + newSize);
    }

    public void updateSquare(int row, int col, char letter) {
        if (squares != null && row < squares.length && col < squares[row].length) {
            squares[row][col].setLetter(letter);
        }
    }

    public void handleSquareClick(int row, int col) {
        char letter = getSelectedLetterForCurrentPlayer();
        boolean moveMade = model.makeMove(row, col, letter);

        if (moveMade) {
            System.out.println("Move made at (" + row + ", " + col + ") with " + letter);
            updateSquare(row, col, letter);
            view.setCurrentTurnLabel("Current Turn: " + 
                (model.getCurrentPlayer() == 1 ? "Blue Player" : "Red Player"));
        } else {
            System.out.println("Invalid move at (" + row + ", " + col + ")");
        }
    }

    private char getSelectedLetterForCurrentPlayer() {
        ToggleGroup group = (model.getCurrentPlayer() == 1)
                ? view.getBlueGroup()
                : view.getRedGroup();

        RadioButton selected = (RadioButton) group.getSelectedToggle();
        return selected.getText().charAt(0);
    }
}

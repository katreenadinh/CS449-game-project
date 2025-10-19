package sprint2.product;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameView extends Application {

    private GameModel model;
    private GameController controller;
    private Square[][] squares; // fixed typo

    @Override
    public void start(Stage primaryStage) {
        // Initialize model if not already set
        if (model == null) {
            model = new GameModel(3); // 3x3 default board
        }

        GridPane pane = new GridPane();
        int size = model.getSize();

        squares = new Square[size][size];

        // Create the grid of squares
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                squares[row][col] = new Square(row, col);
                pane.add(squares[row][col], col, row);
            }
        }

        // Scene setup
        Scene scene = new Scene(pane, 400, 400);
        primaryStage.setTitle("SOS Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Inner class for the board cells
    public class Square extends Pane {
        private int row;
        private int col;

        public Square(int row, int col) {
            this.row = row;
            this.col = col;
            setStyle("-fx-border-color: black; -fx-background-color: white;");
            setPrefSize(2000, 2000);
            setOnMouseClicked(e -> handleMouseClick());
        }

        private void handleMouseClick() {
            System.out.println("Square clicked at (" + row + ", " + col + ")");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

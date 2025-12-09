package sprint5.product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameRecorder {

    private List<PlayerModel.Move> moves;
    private boolean recording;
    private int boardSize;
    private GameMode gameMode;
    private PlayerType blueType;
    private PlayerType redType;

    public GameRecorder() {
        moves = new ArrayList<>();
        recording = false;
    }

    public void startRecording(int boardSize, GameMode gameMode, PlayerType blueType, PlayerType redType) {
        this.boardSize = boardSize;
        this.gameMode = gameMode;
        this.blueType = blueType;
        this.redType = redType;
        moves.clear();
        recording = true;
    }

    public void stopRecording() {
        recording = false;
    }

    public boolean isRecording() {
        return recording;
    }

    public void recordMove(PlayerModel.Move move) {
        if (recording) {
            moves.add(move);
        }
    }

    public List<PlayerModel.Move> getMoves() {
        return moves;
    }


    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {

        	writer.write(boardSize + "," + gameMode + "," + blueType + "," + redType + "\n");
            
            for (PlayerModel.Move move : moves) {
                writer.write(move.row + "," + move.col + "," + move.letter + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<PlayerModel.Move> loadFromFile(String filename) {
        List<PlayerModel.Move> loadedMoves = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String metaLine = reader.readLine();
            if (metaLine != null) {
                String[] metaParts = metaLine.split(",");
                boardSize = Integer.parseInt(metaParts[0]);
                gameMode = GameMode.valueOf(metaParts[1]);
                blueType = PlayerType.valueOf(metaParts[2]);
                redType = PlayerType.valueOf(metaParts[3]);
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);
                char letter = parts[2].charAt(0);
                loadedMoves.add(new PlayerModel.Move(row, col, letter));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedMoves;
    }
    
    public int getBoardSize() { return boardSize; }
    public GameMode getGameMode() { return gameMode; }
    public PlayerType getBlueType() { return blueType; }
    public PlayerType getRedType() { return redType; }
}

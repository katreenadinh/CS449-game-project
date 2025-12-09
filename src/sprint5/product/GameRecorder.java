package sprint5.product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameRecorder {

    private List<PlayerModel.Move> moves;
    private boolean recording;

    public GameRecorder() {
        moves = new ArrayList<>();
        recording = false;
    }

    public void startRecording() {
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
}

